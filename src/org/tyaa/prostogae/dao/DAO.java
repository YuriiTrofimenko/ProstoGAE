package org.tyaa.prostogae.dao;

import org.tyaa.prostogae.entity.*;

import com.googlecode.objectify.util.DAOBase;
import com.googlecode.objectify.*;

public class DAO extends DAOBase {

	static {
		
		ObjectifyService.register(PageData.class);
		ObjectifyService.register(OrderData.class);
	}
}
