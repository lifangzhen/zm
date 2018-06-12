package com.lun.mlm.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lun.mlm.model.Order;
import com.lun.mlm.model.PrepayLog;

public interface OrderService {

	public List<PrepayLog> listPrepayLog(String openid, String trueName, String day, Integer start, Integer end);
	
	public List<Order> listOrder(String openid, String trueName, String phone, String gradeId, String from, String to, String sendfrom, String sendto, Integer start, Integer end, Integer takeType);
	
	public Integer countOrder(String openid, String trueName, String phone, String gradeId, String from, String to, String sendfrom, String sendto, Integer takeType);
	
	public List<Order>listOrderAndDetails(String openid, String trueName, String from, String to, Integer start, Integer end, Integer status);
	
	public Order getOrderAndDetails(String orderid);
	/**
	 * 生成订单
	 * @param openid
	 * @param takeType
	 * @param sendTime
	 * @param mark
	 * @param orderjson
	 */
	public String createOrder(String openid, Integer takeType,  String sendTime, String phone, String addr,  String mark, String orderjson);
	
	/**
	 * 微信回调
	 * @param appid
	 * @param mch_id
	 * @param orderid
	 * @param cashFee
	 */
	public void orderFeedback(String appid, String mch_id, String orderid, String cashFee);
	
	/**
	 * 支付设置：若预存全款，则直接扣款；否则返回微信支付配置
	 * @param openid
	 * @param orderid
	 * @param pageUrl
	 * @return
	 */
	public Map<String, Object> payConfig(String openid, String orderid, String pageUrl);
	
	/**
	 * 预存充值设置
	 * @param openid
	 * @param gradeId
	 * @param pageUrl
	 * @return
	 */
	public Map<String, Object> prepayConfig(String openid, String gradeId, String pageUrl);
	
	/**
	 * 预存扣减
	 * @param openid
	 * @param orderid
	 * @param deductNum
	 */
	public void deductPrepay(String openid, String orderid, BigDecimal deductNum);
}
