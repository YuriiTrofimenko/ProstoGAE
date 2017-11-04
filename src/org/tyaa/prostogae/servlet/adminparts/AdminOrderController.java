package org.tyaa.prostogae.servlet.adminparts;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.tyaa.prostogae.dao.OrderDAO;
import org.tyaa.prostogae.entity.OrderData;
import org.tyaa.prostogae.entity.OrdersData;
import org.tyaa.prostogae.entity.PageData;

import com.google.gson.Gson;

public class AdminOrderController {
	
	/**
	 * Преобразователь объектов Java в строки JSON
	 * */
	Gson mGson;
	
	public AdminOrderController() {
		
		mGson = new Gson();
	}

	/**
	 * Получение строки json-массива кратких данных о заказах
	 * */
	public String getAllOrdersLazy() {
		
		//Строка ответа клиенту (браузеру)
		String responseJsonString = "start";
		
		try {
			
			OrdersData ordersData = (new OrderDAO()).getAllOrders();
			
			if(ordersData != null){
				
				for(int i = 0; i < ordersData.size(); i++){
					//Зануляем все ненужные при данном запросе поля
					OrderData currentOrder = ordersData.ordersData.get(i);
					currentOrder.setTask("");
					currentOrder.setDeadlineDate(null);
					currentOrder.setDesiredDeadlineDate(null);
					currentOrder.setEmail(null);
					currentOrder.setFulfilledDate(null);
					currentOrder.setLaunchStartDate(null);
					currentOrder.setPaidDate(null);
					currentOrder.setPlacedDate(null);
				}
				responseJsonString = mGson.toJson(ordersData);
			} else {
        		
				responseJsonString = mGson.toJson("error");
        	}
    	} catch(Exception ex) {
    		
    		responseJsonString = mGson.toJson("error");
    	}
		
		return responseJsonString;
	}
	
	/**
	 * Получение строки json-массива полных данных о заказах
	 * */
	public String getAllOrders() {
		
		//Строка ответа клиенту (браузеру)
		String responseJsonString = "start";
		
		try {
			
			OrdersData ordersData = (new OrderDAO()).getAllOrders();
			
			if(ordersData != null){
				
				responseJsonString = mGson.toJson(ordersData);
			} else {
        		
				responseJsonString = mGson.toJson("error");
        	}
    	} catch(Exception ex) {
    		
    		responseJsonString = mGson.toJson("error");
    	}
		
		return responseJsonString;
	}
	
	/**
	 * Получение строки json-объекта полных данных об одном заказе
	 * по заданному id
	 * */
	public String getOrderById(HttpServletRequest _req) {
		
		//Строка ответа клиенту (браузеру)
		String responseJsonString = "start";
		String id = null;
		
		try {
			
			id = _req.getParameter("id");
			OrderData order = (new OrderDAO()).getOrder(id);
			
			if(order != null){
				
				responseJsonString = mGson.toJson(order);
			} else {
        		
				responseJsonString = mGson.toJson("error");
        	}
    	} catch(Exception ex) {
    		
    		responseJsonString = mGson.toJson("error");
    	}
		
		return responseJsonString;
	}
}
