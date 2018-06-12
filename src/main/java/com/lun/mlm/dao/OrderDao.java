package com.lun.mlm.dao;

import java.util.Date;
import java.util.List;

import com.lun.mlm.model.Order;
import com.lun.mlm.model.OrderDetail;
import com.lun.mlm.model.PrepayLog;

public interface OrderDao {
	
	public void addOrder(Order order);
	
	public void updateOrder(Order order);
	
	public void addOrderDtail(List<OrderDetail> list);
	
	public Order getOrder(String id);
	
	public void changeOrderTakeStatus(String id);
	
	public PrepayLog getPrepayLog(String id);
	
	public void addPrepayLog(PrepayLog pl);
	
	public void updatePrepayLog(PrepayLog pl);

	public List<PrepayLog> listPrepayLog(String openid, String trueName, String day, Integer start, Integer end);
	
	public Integer countPrepayLog(String openid, String trueName, String day);
	
	public List<Order> listOrder(String openid, String trueName, String phone, String gradeId, Date fromDate, Date toDate,Date sendfromDate, Date sendtoDate, Integer start, Integer end, Integer status, Integer takeType);
	
	public Integer countOrder(String openid, String trueName, String phone, String gradeId, Date fromDate, Date toDate,Date sendfromDate, Date sendtoDate, Integer takeType);
	
	public List<OrderDetail> listOrderDetail(String orderId);
}
