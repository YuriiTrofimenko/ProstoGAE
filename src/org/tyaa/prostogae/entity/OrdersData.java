package org.tyaa.prostogae.entity;

import java.util.List;

public class OrdersData {
	
	public List<OrderData> ordersData;
	
	public OrdersData() { }
	
	public OrdersData(List<OrderData> _orders) {
		
		ordersData = _orders;
	}
	
	public int size() {return ordersData.size();}
}
