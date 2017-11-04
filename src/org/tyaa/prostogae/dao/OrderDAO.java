package org.tyaa.prostogae.dao;

import java.util.List;

import org.tyaa.prostogae.entity.OrderData;
import org.tyaa.prostogae.entity.PageData;
import org.tyaa.prostogae.entity.OrdersData;

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
	
	public OrderData getOrder(String _id) {
		
		return mOFY.get(OrderData.class, Long.parseLong(_id));
	}
	
	public OrdersData getAllOrders() {
			
		Query<OrderData> query = mOFY.query(OrderData.class);
		return new OrdersData((List<OrderData>) query.list());
	}
	
	public void saveOrder(OrderData _order) {
		
		mOFY.put(_order);
	}
}
