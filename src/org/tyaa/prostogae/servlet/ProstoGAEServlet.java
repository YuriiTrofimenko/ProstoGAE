package org.tyaa.prostogae.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.tyaa.prostogae.dao.DAO;
import org.tyaa.prostogae.entity.PageData;

import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@SuppressWarnings("serial")
public class ProstoGAEServlet extends HttpServlet {
	
	private DAO mDAO;
	private Objectify mOFY;
	
	@Override
	public void init() throws ServletException {
		//
		super.init();
		mDAO = new DAO();
		mOFY = mDAO.ofy();
	}
	
	public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		
		// TODO handle exceptions
		if (req.getParameterMap().containsKey("action")) {
			
			String actionString = req.getParameter("action");
	        if(actionString.equals("send-order")){
	        		        	
	        	try {

    				//проверяем является ли полученный запрос multipart/form-data
					boolean isMultipart = ServletFileUpload.isMultipartContent(req);
					if (isMultipart) {
						
						//TODO Send an email with attachment(s)

						// Создаём класс фабрику 
						DiskFileItemFactory factory = new DiskFileItemFactory();
				 
						// Максимальный буфера данных в байтах,
						// при его привышении данные начнут записываться на диск во временную директорию
						// устанавливаем один мегабайт
						factory.setSizeThreshold(1024*1024);
						
						// устанавливаем временную директорию
						File tempDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
						factory.setRepository(tempDir);
				 
						//Создаём сам загрузчик
						ServletFileUpload upload = new ServletFileUpload(factory);
						
						//максимальный размер данных который разрешено загружать в байтах
						//по умолчанию -1, без ограничений. Устанавливаем 10 мегабайт. 
						//upload.setSizeMax(1024 * 1024 * 10);

						//Помещаем полученные данные в список объектов
						List<FileItem> items = upload.parseRequest(req);
						Iterator<FileItem> iter = items.iterator();

						String copyrightTask = null;
						String copyrightDate = null;
						List<FileItem> attachments = new ArrayList();
						
						//Перебираем список объектов с полученными данными
						while (iter.hasNext()) {

						    FileItem item = (FileItem) iter.next();
			 
						    if (item.isFormField()) {
						    	//если принимаемая часть данных является полем формы			    	
						    	if(item.getFieldName().equals("copyright-task")){
						    		
						    		copyrightTask = URLDecoder.decode(item.getString(), "UTF-8");
						    	} else if(item.getFieldName().equals("copyright-date")){
						    		
						    		copyrightDate = item.getString();
						    	}
						    } else {
						    	//в противном случае рассматриваем как файл
						    	attachments.add(item);
						    }
						}
						
						//
						Multipart mp = new MimeMultipart();
						
						MimeBodyPart textPart = new MimeBodyPart();
						textPart.setText(copyrightTask + " Выполнить до: " + copyrightDate);
						mp.addBodyPart(textPart);
						
						for(FileItem item : attachments){
							
							MimeBodyPart attachment = new MimeBodyPart();
							InputStream attachmentDataStream =
									new ByteArrayInputStream(item.get());
							attachment.setFileName(item.getName());
							attachment.setContent(attachmentDataStream, "image/jpeg");
							mp.addBodyPart(attachment);
						}

						// Отправляем сообщение
						Properties props = new Properties();
						props.setProperty("mail.mime.charset", "UTF-8");
						Session session = Session.getDefaultInstance(props, null);

						Message msg = new MimeMessage(session);
						msg.setFrom(new InternetAddress("karakal2586@gmail.com", "Prosto Studio site"));
						msg.addRecipient(Message.RecipientType.TO,
						               new InternetAddress("karakal2586@gmail.com", "Admin"));
						msg.setSubject("New order");
						//msg.setText(copyrightTask + " Выполнить до: " + copyrightDate);
						msg.setContent(mp);
						Transport.send(msg);


					} else {

						//TODO Send a simple email
					}
					//out.print("ok");
					Gson gson = new Gson();
    			    String json = gson.toJson("ok");
	        		out.print(json);
	        	} catch(Exception ex) {
	        		
	        		/*String errorString = "";
	        		
	        		StackTraceElement[] elements = ex.getStackTrace();  
	                for (int iterator=1; iterator<=elements.length; iterator++)  
	                	errorString += "Class Name:"+elements[iterator-1].getClassName()+" Method Name:"+elements[iterator-1].getMethodName()+" Line Number:"+elements[iterator-1].getLineNumber();
*/
	        		Gson gson = new Gson();
    			    //String json = gson.toJson(errorString);
	        		String json = gson.toJson(ex.getLocalizedMessage());
	        		out.print(json);
	        	}
	        	
	        	/*Gson gson = new Gson();
			    String json = gson.toJson("send - ok");
        		out.print(json);*/
	        	
	        } else if(actionString.equals("get-data-by-section")) {
				
				//TODO validator
				String section = req.getParameter("section");
								
				Query<PageData> query = mOFY.query(PageData.class);
    			query.filter("section", section);
    			
    			List<PageData> pageData = (List<PageData>) query.list();
    			    			
    			if(pageData != null && !pageData.isEmpty()){
    				
    				Gson gson = new Gson();
    			    String json = gson.toJson(pageData.get(0));
    			    out.print(json);
    			} else {
	        		
    				Gson gson = new Gson();
    			    String json = gson.toJson("error");
	        		out.print(json);
	        	}
			} else {

				Gson gson = new Gson();
			    String json = gson.toJson("Incorrect URL - params are not exist");
			    out.print(json);
			}
		} else {

			Gson gson = new Gson();
		    String json = gson.toJson("Incorrect URL - action is not exists");
		    out.print(json);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//
		doRequest(req, resp);
	}
}
