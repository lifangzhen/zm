package com.lun.mlm.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.lun.mlm.dao.OrderDao;
import com.lun.mlm.model.Order;
import com.lun.mlm.model.OrderDetail;
import com.lun.mlm.model.PrepayLog;

@Repository("orderDao")
public class OrderDaoImpl extends SqlMapClientDaoSupport implements OrderDao {

	@Override
	public void addOrder(Order order) {
		this.getSqlMapClientTemplate().insert("Order_SqlMap.addOrder", order);
	}
	
	@Override
	public void updateOrder(Order order) {
		this.getSqlMapClientTemplate().update("Order_SqlMap.updateOrder", order);
	}
	
	@Override
	public void addOrderDtail(List<OrderDetail> list) {
		this.getSqlMapClientTemplate().insert("Order_SqlMap.addOrderDtail", list);
	}
	
	@Override
	public Order getOrder(String id) {
		return (Order) this.getSqlMapClientTemplate().queryForObject("Order_SqlMap.getOrder", id);
	}
	
	@Override
	public void changeOrderTakeStatus(String id) {
		this.getSqlMapClientTemplate().update("Order_SqlMap.changeOrderTakeStatus", id);
	}

	
	@Override
	public PrepayLog getPrepayLog(String id) {
		return (PrepayLog) this.getSqlMapClientTemplate().queryForObject("Order_SqlMap.getPrepayLog", id);
	}
	
	@Override
	public void addPrepayLog(PrepayLog pl) {
		this.getSqlMapClientTemplate().insert("Order_SqlMap.addPrepayLog", pl);
	}
    
	@Override
	public void updatePrepayLog(PrepayLog pl) {
		this.getSqlMapClientTemplate().update("Order_SqlMap.updatePrepayLog", pl);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PrepayLog> listPrepayLog(String openid, String trueName,
			String day, Integer start, Integer end) {
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("openid", openid);
		map.put("trueName", trueName);
		map.put("day", day);
		map.put("start", start);
		map.put("end", end);
		return this.getSqlMapClientTemplate().queryForList("Order_SqlMap.listPrepayLog", map);
	}

	@Override
	public Integer countPrepayLog(String openid, String trueName, String day) {
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("openid", openid);
		map.put("trueName", trueName);
		map.put("day", day);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Order_SqlMap.countPrepayLog", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> listOrder(String openid, String trueName, String phone, String gradeId, Date fromDate,
			Date toDate, Date sendfromDate, Date sendtoDate, Integer start, Integer end, Integer status, Integer takeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		map.put("trueName", trueName);
		map.put("phone", phone);
		map.put("gradeId", gradeId);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		map.put("sendfromDate", sendfromDate);
		map.put("sendtoDate", sendtoDate);
		map.put("start", start);
		map.put("end", end);
		map.put("status", status);
		map.put("takeType", takeType);
		return this.getSqlMapClientTemplate().queryForList("Order_SqlMap.listOrder", map);
	}

	@Override
	public Integer countOrder(String openid, String trueName, String phone, String gradeId, Date fromDate,
			Date toDate, Date sendfromDate, Date sendtoDate, Integer takeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", openid);
		map.put("trueName", trueName);
		map.put("phone", phone);
		map.put("gradeId", gradeId);
		map.put("fromDate", fromDate);
		map.put("toDate", toDate);
		map.put("sendfromDate", sendfromDate);
		map.put("sendtoDate", sendtoDate);
		map.put("takeType", takeType);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Order_SqlMap.countOrder", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetail> listOrderDetail(String orderId) {
		return this.getSqlMapClientTemplate().queryForList("Order_SqlMap.listOrderDetail", orderId);
	}

}
