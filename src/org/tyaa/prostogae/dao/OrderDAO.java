package org.tyaa.prostogae.dao;

import java.util.List;

import org.tyaa.prostogae.entity.Order;
import org.tyaa.prostogae.entity.PageData;
import org.tyaa.prostogae.entity.Orders;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

/**
 * Контроллер DataStore для работы с объектами сущности Order
 */
public class OrderDAO {

	private DAO mDAO;
	private Objectify mOFY;
	
	public OrderDAO() {
		
		mDAO = new DAO();
		mOFY = mDAO.ofy();
	}
	
	public Order getOrder(String _id) {
		
		return mOFY.get(Order.class, Long.parseLong(_id));
	}
	
	public Orders getAllOrders() {
			
		Query<Order> query = mOFY.query(Order.class);
		return new Orders((List<Order>) query.list());
	}
	
	public void saveOrder(Order _order) {
		
		mOFY.put(_order);
	}
}
