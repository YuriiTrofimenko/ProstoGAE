package org.tyaa.prostogae.servlet.commonparts;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Отправка сообщений на email
 * */
public class Mailer {
	
	public void prepareMulipartData(
			ServletContext _context
			, HttpServletRequest _req
			, Iterator<FileItem> _iter)
			throws FileUploadException {
		
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
		_iter = items.iterator();
	}
	
	public void sendMulipartMsg(Multipart _mp, String _subjectString)
			throws FileUploadException, Exception, MessagingException {
		
		Properties props = new Properties();
		props.setProperty("mail.mime.charset", "UTF-8");
		Session session = Session.getDefaultInstance(props, null);

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("karakal2586@gmail.com", "Prosto Studio site"));
		msg.addRecipient(Message.RecipientType.TO,
		               new InternetAddress("karakal2586@gmail.com", "Admin"));
		msg.setSubject(_subjectString);
		msg.setContent(_mp);
		Transport.send(msg);
	}
	
	public void sendPlainMsg(
			String _messageString
			, String _subjectString
			, String _fromAddressString
			, String _fromNameString
			, String _toAddressString
			, String _toNameString)
			throws FileUploadException, Exception, MessagingException {
		
		Properties props = new Properties();
		props.setProperty("mail.mime.charset", "UTF-8");
		Session session = Session.getDefaultInstance(props, null);

		Message msg = new MimeMessage(session);
		msg.setFrom(
			new InternetAddress(_fromAddressString, _fromNameString)
		);
		msg.addRecipient(
			Message.RecipientType.TO,
			new InternetAddress(_toAddressString, _toNameString)
       );
		msg.setSubject(_subjectString);
		msg.setText(_messageString);
		Transport.send(msg);
	}
}
