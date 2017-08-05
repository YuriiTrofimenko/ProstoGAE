package org.tyaa.prostogae.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.tyaa.prostogae.dao.DAO;
import org.tyaa.prostogae.entity.PageData;

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
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		
		// TODO handle exceptions
		if (req.getParameterMap().containsKey("action")) {
			
			String actionString = req.getParameter("action");
	        if(actionString.equals("999")){
	        	
	        	/*//TODO
	        	PageData pageData =
	    			new PageData(
    					req.getParameter("section")
						, req.getParameter("title")
						, req.getParameter("content")
					);
	        	
	        	mOFY.put(pageData);
	        	
	        	out.print("ok");*/
	        }/* else if(actionString.equals("888")) {
	        	
	        	String id = req.getParameter("id");
	        	PageData pageData = mOFY.get(PageData.class, Long.parseLong(id));
	        	if(pageData != null){
	        		
	        		pageData.setTitle(req.getParameter("title"));
	        		pageData.setContent(req.getParameter("content"));
	        		mOFY.put(pageData);
		        	out.print("ok");
	        	} else {
	        		
	        		out.print("error");
	        	}
			} else if(actionString.equals("111")) {
	        	
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
		        		
		        		out.print("error" + " " + id);
		        	}
				}catch(Exception ex){
					
					out.print("ex " + ex + id);
				}
			} else if(actionString.equals("777")) {
				
				Query<PageData> query = mOFY.query(PageData.class);
    			List<PageData> results = (List<PageData>) query.list();
    			
    			if(results != null && !results.isEmpty()){
    				
    				Gson gson = new Gson();
    			    String json = gson.toJson(results);
    			    out.print(json.toString());
    			} else {
	        		
	        		out.print("error");
	        	}
			}*/ else if(actionString.equals("get-data-by-section")) {
				
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
	        		
	        		out.print("error");
	        	}
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//
		doGet(req, resp);
	}
}
