package org.tyaa.prostogae.entity;

import java.util.List;

public class Orders {
	
	public List<Order> orders;
	
	public Orders() { }
	
	public Orders(List<Order> _orders) {
		
		orders = _orders;
	}
	
	public int size() {return orders.size();}
}
