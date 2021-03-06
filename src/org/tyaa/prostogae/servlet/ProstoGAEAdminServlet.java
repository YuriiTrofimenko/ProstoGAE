package org.tyaa.prostogae.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tyaa.prostogae.dao.DAO;
import org.tyaa.prostogae.entity.OrderData;
import org.tyaa.prostogae.entity.PageData;
import org.tyaa.prostogae.entity.PagesData;
import org.tyaa.prostogae.servlet.adminparts.AdminOrderController;
import org.tyaa.prostogae.servlet.publicparts.OrderController;

import com.google.gson.Gson;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class ProstoGAEAdminServlet extends HttpServlet {
	
	private DAO mDAO;
	private Objectify mOFY;
	
	@Override
	public void init() throws ServletException {
		//
		super.init();
		mDAO = new DAO();
		mOFY = mDAO.ofy();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		
		// TODO handle exceptions
		if (req.getParameterMap().containsKey("action")) {
			
			String actionString = req.getParameter("action");
			//out.print(req.getParameter("action") + " " + req.getParameter("name") + " " + req.getParameter("descr"));
	        if(actionString.equals("create-page-data")){
	        	//TODO
	        	PageData pageData =
	    			new PageData(
    					req.getParameter("section")
						, req.getParameter("title")
						, req.getParameter("content")
					);
	        	//Key<Service> v = mOFY.put(service);
	        	
	        	mOFY.put(pageData);
	        	
	        	//out.print(v);
	        	out.print("ok");
	        	
	        	//Gson gson = new Gson();
			    //String json = gson.toJson(service);
			    //out.print(json.toString());
	        	
	        } else if(actionString.equals("edit-page-data")) {
	        	
	        	String id = req.getParameter("id");
	        	PageData pageData = mOFY.get(PageData.class, Long.parseLong(id));
	        	if(pageData != null){
	        		
	        		pageData.setTitle(req.getParameter("title"));
	        		pageData.setContent(req.getParameter("content"));
	        		mOFY.put(pageData);
	        		Gson gson = new Gson();
    			    String json = gson.toJson("ok");
		        	out.print(json);
	        	} else {
	        		
	        		Gson gson = new Gson();
    			    String json = gson.toJson("error");
	        		out.print(json);
	        	}
			} else if(actionString.equals("get-page-data")) {
	        	
				String id = null;
	        	PageData pageData = null;
				try{
					
					id = req.getParameter("id");
		        	pageData = mOFY.get(PageData.class, Long.parseLong(id));
		        	if(pageData != null){
		        		
		        		Gson gson = new Gson();
	    			    String json = gson.toJson(pageData);
	    			    out.print(json);
		        	} else {
		        		
		        		out.print("error");
		        	}
				}catch(Exception ex){
					
					out.print("error");
				}
	        	
	        	
	        	/*Map<Key<PageData>, PageData> map =
        			mOFY.get(PageData.class, Key.create(PageData.class, id));
	        	if(map != null){
	        		
	        		PageData pageData = map.values().iterator().next();
	        		Gson gson = new Gson();
    			    String json = gson.toJson(pageData);
    			    out.print(json);*/
	        	
	        	
			} else if(actionString.equals("get-pages-data-lazy")) {
				/*//TODO validator*/
				
				Query<PageData> query = mOFY.query(PageData.class);
    			List<PageData> results = (List<PageData>) query.list();
    			
    			if(results != null && !results.isEmpty()){
    				
    				for(int i = 0; i < results.size(); i++){
    					
    					results.get(i).setSection("");
    					results.get(i).setContent("");
    				}
    				Gson gson = new Gson();
    			    String json = gson.toJson(new PagesData(results));
    			    out.print(json.toString());
    			} else {
	        		
	        		out.print("error");
	        	}
			} else if(actionString.equals("get-pages-data")) {
				/*//TODO validator
	        	
	        	//out.print(req.getParameter("name"));
	        	
				Customer newCustomer =
					new Customer(
						req.getParameter("name")
						, req.getParameter("phone")
						, req.getParameter("email")
						, req.getParameter("passwd"));
				
				mOFY.put(newCustomer);*/
				
				Query<PageData> query = mOFY.query(PageData.class);
    			//query.filter("name", "name1");
    			List<PageData> results = (List<PageData>) query.list();
    			
    			if(results != null && !results.isEmpty()){
    				
    				Gson gson = new Gson();
    			    String json = gson.toJson(new PagesData(results));
    			    out.print(json.toString());
    			} else {
	        		
	        		out.print("error");
	        	}
			} else if(actionString.equals("signin")) {
				//TODO
			} else if(actionString.equals("get-orders-data-lazy")) {
				//Получение кратких данных о заказах
				String getAllOrdersLazyResultJsonString =
	        			(new AdminOrderController()).getAllOrdersLazy();
	        	out.print(getAllOrdersLazyResultJsonString);
			} else if(actionString.equals("get-orders-data")) {
				//Получение полных данных о заказах
				String getAllOrdersResultJsonString =
	        			(new AdminOrderController()).getAllOrders();
	        	out.print(getAllOrdersResultJsonString);
			} else if(actionString.equals("get-order-data")) {
				//Получение полных данных об одном заказе
				String getOrderResultJsonString =
	        			(new AdminOrderController()).getOrderById(req);
	        	out.print(getOrderResultJsonString);
			} else if(actionString.equals("edit-order-data")) {
	        	
				//Получение полных данных об одном заказе
				String editOrderResultJsonString =
	        			(new AdminOrderController()).editOrder(req);
	        	out.print(editOrderResultJsonString);
			}
		} else if(req.getParameterMap().isEmpty()){
			
			resp.sendRedirect("/admin/index.html");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		doGet(req, resp);
	}
}
