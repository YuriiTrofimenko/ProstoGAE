package org.tyaa.prostogae.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.tyaa.prostogae.dao.*;
import org.tyaa.prostogae.entity.PageData;

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
						req.getParameter("title")
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
	        	PageData pageData = mOFY.get(PageData.class, id);
	        	if(pageData != null){
	        		
	        		pageData.setTitle(req.getParameter("title"));
	        		pageData.setContent(req.getParameter("content"));
	        		mOFY.put(pageData);
		        	out.print("ok");
	        	} else {
	        		
	        		out.print("error");
	        	}
				/*//TODO validator
	        	
	        	//out.print(req.getParameter("name"));
	        	
				Customer newCustomer =
					new Customer(
						req.getParameter("name")
						, req.getParameter("phone")
						, req.getParameter("email")
						, req.getParameter("passwd"));
				
				mOFY.put(newCustomer);
				
				Query<Service> query = mOFY.query(Service.class);
    			//query.filter("name", "name1");
    			List<Service> results = (List<Service>) query.list();
    			
    			
    			
    			if(!results.isEmpty()){
    				
    				Gson gson = new Gson();
    			    String json = gson.toJson(results);
    			    out.print(json.toString());
    			}
	        	
				Gson gson = new Gson();
			    String json = gson.toJson(newCustomer);
			    out.print(json.toString());*/
			} else if(actionString.equals("signup")) {
				/*//TODO validator
	        	
	        	//out.print(req.getParameter("name"));
	        	
				Customer newCustomer =
					new Customer(
						req.getParameter("name")
						, req.getParameter("phone")
						, req.getParameter("email")
						, req.getParameter("passwd"));
				
				mOFY.put(newCustomer);
				
				Query<Service> query = mOFY.query(Service.class);
    			//query.filter("name", "name1");
    			List<Service> results = (List<Service>) query.list();
    			
    			
    			
    			if(!results.isEmpty()){
    				
    				Gson gson = new Gson();
    			    String json = gson.toJson(results);
    			    out.print(json.toString());
    			}
	        	
				Gson gson = new Gson();
			    String json = gson.toJson(newCustomer);
			    out.print(json.toString());*/
			} else if(actionString.equals("signin")) {
				/*//TODO validator
	        	
	        	//out.print(req.getParameter("name"));
				
				String id = req.getParameter("id");
				String email = req.getParameter("email");
				String password = req.getParameter("passwd");
				
				//Customer fromDBCustomer = mOFY.get(Customer.class, id);
				
				Query<Customer> query = mOFY.query(Customer.class);
    			query.filter("email", email).filter("password", password);
    			
    			List<Customer> fromDBCustomer = (List<Customer>) query.list();
    			
    			List<Service> results = (List<Service>) query.list();
    			
    			if(!results.isEmpty()){
    				
    				Gson gson = new Gson();
    			    String json = gson.toJson(results);
    			    out.print(json.toString());
    			}
    			
				Gson gson = new Gson();
			    String json =
		    		(fromDBCustomer != null && fromDBCustomer.size() != 0)
		    			? gson.toJson(fromDBCustomer.get(0))
    					: gson.toJson(new Customer());
			    out.print(json);*/
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
