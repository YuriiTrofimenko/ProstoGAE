package org.tyaa.prostogae.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tyaa.prostogae.dao.DAO;
import org.tyaa.prostogae.entity.PageData;
import org.tyaa.prostogae.servlet.publicparts.OrderController;

import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

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
		
		// проверяем, содержит ли запрос параметр с именем "действие"
		if (req.getParameterMap().containsKey("action")) {
			// если да - проверяем, чему он равен
			String actionString = req.getParameter("action");
			// отправка заказа
	        if(actionString.equals("send-order")){
	        	// вызываем метод отправки заказа администратору по почте
	        	// и сохранения в нереляционную БД
	        	String createOrderResultJsonString =
	        			(new OrderController()).createOrder(getServletContext(), req);
	        	// отправляем ответ клиенту - успешно ли произошло создание заказа
	        	out.print(createOrderResultJsonString);
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
