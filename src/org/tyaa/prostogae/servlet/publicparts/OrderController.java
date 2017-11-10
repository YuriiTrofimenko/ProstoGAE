package org.tyaa.prostogae.servlet.publicparts;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.tyaa.prostogae.dao.OrderDAO;
import org.tyaa.prostogae.entity.OrderData;
import org.tyaa.prostogae.entity.OrderStatus;
import org.tyaa.prostogae.entity.OrderType;
import org.tyaa.prostogae.servlet.commonparts.Mailer;

import com.google.gson.Gson;

public class OrderController {
	
	/**
	 * Преобразователь объектов Java в строки JSON
	 * */
	Gson mGson;
	
	public OrderController() {
		
		mGson = new Gson();
	}

	/**
	 * Добавление записи о заказе в БД и отправка письма администратору
	 * */
	@SuppressWarnings("null")
	public String createOrder(ServletContext _context, HttpServletRequest _req) {
		
		//Строка ответа клиенту (браузеру)
		String responseJsonString = "start";
		
		try {
			
			String copyrightTask = null;
			String copyrightDate = null;
			String copyrightName = null;
			String copyrightEmail = null;
			
			//Помощник по отправке сообщений на электронную почту
			Mailer mailer = new Mailer();

			//Проверяем является ли полученный запрос multipart/form-data
			boolean isMultipart = ServletFileUpload.isMultipartContent(_req);
			
			//Если получен запрос, содержащий прикрепленные файлы
			if (isMultipart) {
				
				Iterator<FileItem> iter = null;
				List<FileItem> attachments = new ArrayList<>();
				
				//mailer.prepareMulipartData(_context, _req, iter);
				
				// Создаём класс-фабрику для работы с файлами на сервере
				DiskFileItemFactory factory = new DiskFileItemFactory();
		 
				// Максимальный буфера данных в байтах,
				// при его привышении данные начнут записываться на диск во временную директорию
				// устанавливаем один мегабайт
				factory.setSizeThreshold(1024*1024);
				
				// устанавливаем временную директорию
				File tempDir = (File)_context.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(tempDir);
		 
				//Создаём загрузчик
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				//максимальный размер данных который разрешено загружать в байтах
				//по умолчанию -1, без ограничений. Устанавливаем 10 мегабайт. 
				//upload.setSizeMax(1024 * 1024 * 10);

				//Помещаем полученные данные в список объектов
				List<FileItem> items = upload.parseRequest(_req);
				iter = items.iterator();
				
				/*String copyrightTask = null;
				String copyrightDate = null;
				String copyrightName = null;
				String copyrightEmail = null;*/
				
				//Перебираем список объектов с полученными данными
				while (iter.hasNext()) {

				    FileItem item = (FileItem) iter.next();
	 
				    if (item.isFormField()) {
				    	//если принимаемая часть данных является полем формы			    	
				    	if(item.getFieldName().equals("copyright-task")){
				    		
				    		copyrightTask = URLDecoder.decode(item.getString(), "UTF-8");
				    	} else if(item.getFieldName().equals("copyright-date")){
				    		
				    		copyrightDate = item.getString();
				    	} else if(item.getFieldName().equals("copyright-name")){
				    		
				    		copyrightName = item.getString();
				    	} else if(item.getFieldName().equals("copyright-email")){
				    		
				    		copyrightEmail = item.getString();
				    	}
				    } else {
				    	//в противном случае рассматриваем как файл
				    	attachments.add(item);
				    }
				}
				
				//
				Multipart mp = new MimeMultipart();
				
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText(
						copyrightTask
						+ " Выполнить до: "
						+ copyrightDate
						+ " ( "
						+ ((copyrightName != null) ? copyrightName : "NoName")
						+ " , "
						+ ((copyrightEmail != null) ? copyrightEmail : "NoEmail")
						+ " )."
					);
				mp.addBodyPart(textPart);
				
				for(FileItem item : attachments){
					
					try {
						
						MimeBodyPart attachment = new MimeBodyPart();
						InputStream attachmentDataStream =
								new ByteArrayInputStream(item.get());
						attachment.setFileName(item.getName());
						//attachment.setContent(attachmentDataStream, "image/jpeg");
						attachment.setContent(attachmentDataStream, item.getContentType());
						mp.addBodyPart(attachment);
					} catch (Exception e) {
						//
					}
				}

				// Отправляем сообщение с вложениями
				mailer.sendMulipartMsg(mp, "New order");
			} else {
				
				copyrightTask = _req.getParameter("copyright-task");
				copyrightDate = _req.getParameter("copyright-date");
				copyrightName = _req.getParameter("copyright-name");
				copyrightEmail = _req.getParameter("copyright-email");
				
				String messageString =
						copyrightTask
						+ " Выполнить до: "
						+ copyrightDate
						+ " ( "
						+ ((copyrightName != null) ? copyrightName : "NoName")
						+ " , "
						+ ((copyrightEmail != null) ? copyrightEmail : "NoEmail")
						+ " ).";

				// Отправляем сообщение
				mailer.sendPlainMsg(
						messageString
						, "New order"
						, "karakal2586@gmail.com"
						, "Prosto Studio site"
						, "karakal2586@gmail.com"
						, "Admin");
			}
			//Добавляем в БД запись о новом заказе
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			OrderData newOrder = new OrderData(
					OrderType.photoprocessing
					, copyrightName
					, copyrightEmail
					, copyrightTask
					, OrderStatus.placed
					, sdf.format(new Date())
					, copyrightDate
					, ""
					, ""
					, ""
					, ""
				);
			(new OrderDAO()).saveOrder(newOrder);
			//Записываем в результат Json-строку "ok"
			responseJsonString = mGson.toJson("ok");
    	} catch(Exception ex) {
    		//TODO error reporting to tyaa10@gmail.com
    		//Записываем в результат Json-строку, содержащую сообщение об ошибке 
    		//responseJsonString = mGson.toJson(ex.getLocalizedMessage());
    		//responseJsonString = mGson.toJson(ex.getStackTrace());
    		if(ex.getLocalizedMessage() != null) {
    			responseJsonString = mGson.toJson(ex.getLocalizedMessage());
    		} else if(ex.getMessage() != null) {
    			responseJsonString = mGson.toJson(ex.getMessage());
    		} else if(ex.getStackTrace() != null) {
    			responseJsonString = mGson.toJson(ex.getStackTrace());
    		}
    	}
		
		return responseJsonString;
	}
}
