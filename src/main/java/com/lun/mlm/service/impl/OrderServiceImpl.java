package com.lun.mlm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.OrderDao;
import com.lun.mlm.dao.SailDao;
import com.lun.mlm.model.Dish;
import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.Order;
import com.lun.mlm.model.OrderDetail;
import com.lun.mlm.model.PrepayDeduct;
import com.lun.mlm.model.PrepayLog;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.service.OrderService;
import com.lun.mlm.service.WxPayService;
import com.lun.mlm.utils.Context;
import com.lun.mlm.utils.DateUtil;
import com.lun.mlm.utils.StringUtil;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.TokenManager;
import com.townmc.mp.json.JSONArray;
import com.townmc.mp.json.JSONObject;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	private static final Log log = LogFactory.getLog(OrderServiceImpl.class);
	@Autowired OrderDao orderDao;
	@Autowired MemberDao memberDao;
	@Autowired SailDao sailDao;
	@Autowired TokenManager tokenManager;
	@Autowired WxPayService wxPayService;
	@Autowired MemberService memberService;

	@Override
	public List<PrepayLog> listPrepayLog(String openid, String trueName,
			String day, Integer start, Integer end) {
		List<PrepayLog> list = orderDao.listPrepayLog(openid, trueName, day, start, end);
		if(list!=null&&list.size()>0){
			for(PrepayLog p:list){
				p.setUpdateTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, p.getUpdateTime()));
			}
		}
		return list;
	}

	@Override
	public List<Order> listOrder(String openid, String trueName, String phone, String gradeId, String from,
			String to, String sendfrom, String sendto, Integer start, Integer end, Integer takeType) {
		Date fromDate = null;
		Date toDate = null;
		Date sendfromDate = null;
		Date sendtoDate = null;
		if(StringUtil.isNotBlank(from)) fromDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, from);
		if(StringUtil.isNotBlank(to)) toDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, to);
		if(StringUtil.isNotBlank(sendfrom)) sendfromDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, sendfrom);
		if(StringUtil.isNotBlank(sendto)) sendtoDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, sendto);
		List<Order> list = orderDao.listOrder(openid, trueName, phone, gradeId, fromDate, toDate, sendfromDate, sendtoDate, start, end, Order.STATUS_PAY, takeType);
		if(list!=null && list.size()>0){
			for(Order order:list){
				order.setUpdateTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getUpdateTime()));
				order.setSendTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime()));
			}
		}
		return list;
	}

	@Override
	public Integer countOrder(String openid, String trueName, String phone, String gradeId, String from,
			String to, String sendfrom, String sendto, Integer takeType) {
		Date fromDate = null;
		Date toDate = null;
		Date sendfromDate = null;
		Date sendtoDate = null;
		if(StringUtil.isNotBlank(from)) fromDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, from);
		if(StringUtil.isNotBlank(to)) toDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, to);
		if(StringUtil.isNotBlank(sendfrom)) sendfromDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, sendfrom);
		if(StringUtil.isNotBlank(sendto)) sendtoDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, sendto);
		Integer count = orderDao.countOrder(openid, trueName, phone, gradeId, fromDate, toDate, sendfromDate, sendtoDate, takeType);
		return count;
	}

	@Override
	public List<Order> listOrderAndDetails(String openid, String trueName,
			String from, String to, Integer start, Integer end, Integer status) {
		Date fromDate = null;
		Date toDate = null;
		if(StringUtil.isNotBlank(from)) fromDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, from);
		if(StringUtil.isNotBlank(to)) toDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, to);
		List<Order> list = orderDao.listOrder(openid, trueName, null, null, fromDate, toDate,null, null, start, end, status, null);
		if(list!=null && list.size()>0){
			for(Order order:list){
				order.setUpdateTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getUpdateTime()));
				order.setSendTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime()));
				List<OrderDetail> details = orderDao.listOrderDetail(order.getId());
				order.setList(details);
			}
		}
		return list;
	}

	@Override
	public Order getOrderAndDetails(String orderid) {
		Order order = orderDao.getOrder(orderid);
		if(order!=null){
			order.setUpdateTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getUpdateTime()));
			order.setSendTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime()));
			List<OrderDetail> details = orderDao.listOrderDetail(order.getId());
			order.setList(details);
		}
		return order;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public String createOrder(String openid, Integer takeType, String sendTime, String phone,
			String addr, String mark, String orderjson) {
		if(StringUtil.isBlank(openid)) throw new MlmException("300", "获取不到openid");
		if(StringUtil.isBlank(orderjson)) throw new MlmException("300", "订单参数为空");
		Member member = memberDao.getMember(openid);
		if(member==null) throw new MlmException("300", "会员不存在");
		if(StringUtil.isBlank(member.getPhone())||StringUtil.isBlank(member.getAddr())){
			Member record = new Member();
			record.setId(member.getId());
			record.setPhone(phone);
			record.setAddr(addr);
			memberDao.updateMember(record);
		}
		Date sendDate = new Date();
		if(StringUtil.isNotBlank(sendTime)) sendDate = DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, sendTime+":00");
		
		//生成订单ID
		String orderId = StringUtil.getUUID();
		String day = DateUtil.currenDateToString(DateUtil.DATE_PATTERN);
		
		//解析订单json
		JSONObject jo = new JSONObject(orderjson);
		BigDecimal total = new BigDecimal(jo.getString("total"));
		JSONArray ja = jo.getJSONArray("list");
		
		Grade grade = memberDao.getGrade(member.getGradeId());
		Grade defalut = memberService.getDefalutGrade();
		if(grade.getPrepay().compareTo(defalut.getPrepay())!=0 && member.getPrepay().compareTo(total)<0) throw new MlmException("300", "尊敬的"+grade.getName()+",您的预存已不足，请先进行充值");
		
		Integer dishNum = 0;
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<ja.length();i++){
			JSONObject joo = (JSONObject) ja.get(i);
			String dishId = joo.getString("id");
			Integer num = joo.getInt("num");
			String categoryId = joo.getString("categoryId");
			dishNum +=num;
			Dish dish = sailDao.getDish(dishId);
			OrderDetail od = new OrderDetail();
			od.setId(StringUtil.getUUID());
			od.setOrderId(orderId);
			od.setCategoryId(categoryId);
			od.setDishId(dishId);
			od.setDishName(dish.getName());
			od.setUnit(dish.getUnit());
			od.setDishNum(num);
			od.setPrice(dish.getPrice());
			od.setDay(day);
			details.add(od);
			if(dish.getStock()==0){
				sb.append(dish.getName()).append(" ");
			}
		}
		if(details==null || details.size()==0) throw new MlmException("300", "订单为空");
		if(sendDate.before(DateUtil.getTodayEnd())){
			if(StringUtil.isNotBlank(sb.toString())){
				sb.append("已经没有库存，可预订明日");
				throw new MlmException("300", sb.toString());
			}
		}
		BigDecimal wxpay = new BigDecimal(0);
		if(member.getPrepay().compareTo(total)<0){
			wxpay = total.subtract(member.getPrepay());
		}
		BigDecimal prepay = total.subtract(wxpay);
		Integer payType = member.getPrepay().compareTo(total)>=0?Order.PAY_TYPE_PREPAY:(member.getPrepay().compareTo(new BigDecimal(0))==0?Order.PAY_TYPE_WECHAT:Order.PAY_TYPE_BOTH);
		
		//生成订单
		Order order = new Order();
		order.setId(orderId);
		order.setOpenid(openid);
		order.setTrueName(member.getTrueName());
		order.setPhone(phone);
		order.setAddr(addr);
		order.setPay(total);
		order.setWxpay(wxpay);
		order.setPrepay(prepay);
		order.setDishNum(dishNum);
		order.setTakeType(takeType);
		order.setPayType(payType);
		order.setSendTime(sendDate);
		order.setDay(day);
		order.setStatus(Order.STATUS_NOT_PAY);
		order.setMark(mark);
		orderDao.addOrder(order);
		
		//生成订单详情
		orderDao.addOrderDtail(details);
		return orderId;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void orderFeedback(String appid, String mch_id, String orderid,
			String cashFee) {
		if(StringUtil.isBlank(orderid)) throw new MlmException("300", "回调的orderid为空");
		Order order = orderDao.getOrder(orderid);
		//订单存在且未支付状态，更改状态、扣款、更改产品数量、通知
		if(order!=null && Order.STATUS_NOT_PAY.equals(order.getStatus())){
			//更改状态
			order.setStatus(Order.STATUS_PAY);
			orderDao.updateOrder(order);
			
			//扣减预存
			if(order.getPrepay().compareTo(new BigDecimal(0))>0){
				this.deductPrepay(order.getOpenid(), orderid, order.getPrepay());
			}
			List<OrderDetail> list = orderDao.listOrderDetail(orderid);
			Member member = memberDao.getMember(order.getOpenid());
			String text = "";
			if(Order.TAKE_TYPE_OUT.equals(order.getTakeType())){
				text = member.getTrueName()+"您好。\n"
						+ "您"+order.getDay()+"的外卖订单已经预定成功。\n"
						+ "您购买的产品有：\n";
				int count=0;
				if(list!=null){
					for (OrderDetail od : list) {
						text = text+ od.getDishName()+" X  "+od.getDishNum()+"\n";
						count+=od.getDishNum();
					}
				}
				text = text + "共计："+count+"件\n";
				text = text + "送餐地址:"+order.getAddr()+"\n"
						+ "联系电话:"+order.getPhone()+"\n"
						+ "送餐时间为:"+DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime())+"\n"
						+"本订单无食品质量问题不可退换。\n"
						+"如有疑问请拨打电话:"+Context.WX_TAKE_PHONE+"，Mr.Lunch。";
			}else {
				text =member.getTrueName()+"您好。\n" 
						+"您"+order.getDay()+"的自取订单已经预定成功。\n"
						+ "您购买的产品有：";
				int count=0;
				if(list!=null){
					for (OrderDetail od : list) {
						text = text+ od.getDishName()+" X  "+od.getDishNum()+"\n";
						count+=od.getDishNum();
					}
				}
				text = text + "共计："+count+"件\n"
						+ "取餐地址:"+Context.WX_TAKE_ADDR+"\n"
						+ "联系电话:"+order.getPhone()+"\n"
						+ "取餐时间为:"+DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime())+"\n"
						+ "本订单无食品质量问题不可退换。\n"
						+ "如有疑问请拨打电话:"+Context.WX_TAKE_PHONE+"，Mr.Lunch。"
						+ "请保留此微信做为取餐凭证。\n";
			}
			
			//异步更新产品数量并通知
			Map<String, Object> mes = new HashMap<String, Object>();
			mes.put("orderId", orderid);
			mes.put("openid", order.getOpenid());
			mes.put("text", text);
		}else if(order==null){
			PrepayLog pl = orderDao.getPrepayLog(orderid);
			if(pl==null) log.info("微信支付通知找不到订单或PrepayLog  orderid:"+orderid);
			//更改状态，并充值
			if(PrepayLog.STATUS_NOT_PAY.equals(pl.getStatus())){
				pl.setStatus(PrepayLog.STATUS_PAY);
				orderDao.updatePrepayLog(pl);
				
				Member member = memberDao.getMember(pl.getOpenid());
				Grade oldgrade = memberDao.getGrade(member.getGradeId());
				Grade newgrade = memberDao.getGrade(pl.getGradeId());
				BigDecimal prepay = member.getPrepay().add(pl.getPrepay());
				member.setPrepay(prepay);
				String  text= "";
				if(oldgrade.getRate()>newgrade.getRate()){
					member.setGradeId(pl.getGradeId());
					text= "您已经成为Mr.Lunch的"+newgrade.getName()+"。\n您以后将享受美食全场"+newgrade.getRate()+"%折优惠。\n"
							+ "提前预定和自取更有折上折，最低6折。\n"
							+ "会员是我们继续前进的财富，有任何需求和问题请随时联系我们。\n"
							+ "非常感谢您的信任，希望我们让您满意。\n"
							+ "祝您永远吃不胖，升职加薪:)";
				}else{
					text = "感谢您继续对我们的信任。\n"+
							   "会员是我们继续前进的财富，有任何需求和问题请随时联系我们。";
				}
				memberDao.updateMember(member);
				
				Map<String, Object> mes = new HashMap<String, Object>();
				mes.put("openid", pl.getOpenid());
				mes.put("text", text);
			}
		}
		
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void deductPrepay(String openid, String orderid, BigDecimal deductNum) {
		Order order = orderDao.getOrder(orderid);
		if(order==null) throw new MlmException("300", "订单不存在");
		Member member = memberDao.getMember(openid);
		if(member==null) throw new MlmException("300", "账户不存在");
		if(member.getPrepay().compareTo(deductNum)<0) throw new MlmException("300", "预存不足");
		BigDecimal pre = member.getPrepay();
		BigDecimal later = member.getPrepay().subtract(deductNum);
		member.setPrepay(later);
		memberDao.updateMember(member);
		
		PrepayDeduct pd = new PrepayDeduct();
		pd.setId(StringUtil.getUUID());
		pd.setOpenid(openid);
		pd.setTrueName(order.getTrueName());
		pd.setPhone(order.getPhone());
		pd.setOrderid(orderid);
		pd.setDeduct(deductNum);
		pd.setPre(pre);
		pd.setLater(later);
		memberDao.addPrepayDeduct(pd);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public Map<String, Object> payConfig(String openid, String orderid, String pageUrl) {
		Order order = orderDao.getOrder(orderid);
		Member member = memberDao.getMember(openid);
		if(order == null) throw new MlmException("300", "订单不存在");
		if(member == null) throw new MlmException("300", "会员不存在");
		if(member.getPrepay().compareTo(order.getPrepay())<0) throw new MlmException("300", "预存不足");
		Map<String, Object> re = new HashMap<String, Object>();
		if(!order.getPayType().equals(Order.PAY_TYPE_PREPAY)){
			DefaultWechat wechat = new DefaultWechat();
			wechat.setAppid(Context.WX_APPID);
			wechat.setSecret(Context.WX_SECRET);
			wechat.setTokenManager(tokenManager);
			Map<String, Object> jsconfig = wechat.getJsConfig(Context.WX_APPID, pageUrl);
			Integer wxpay = (order.getWxpay().multiply(new BigDecimal(100))).intValue();
			re = wxPayService.unifiedPay(openid, orderid, "会员:"+openid, "Mr.Lunch外卖", "WEB", wxpay, "JSAPI", StringUtil.getLocalIpAddress());
			re.put("jsconfig", jsconfig);
		}else{
			if(Order.STATUS_NOT_PAY.equals(order.getStatus())){
				Order record = new Order();
				record.setId(orderid);
				record.setStatus(Order.STATUS_PAY);
				orderDao.updateOrder(record);
				
				if(order.getPrepay().compareTo(new BigDecimal(0))>0){
					this.deductPrepay(order.getOpenid(), orderid, order.getPrepay());
				}
				
				List<OrderDetail> list = orderDao.listOrderDetail(orderid);
				
				String text = "";
				if(Order.TAKE_TYPE_OUT.equals(order.getTakeType())){
					text = member.getTrueName()+"您好。\n"
							+ "您"+order.getDay()+"的外卖订单已经预定成功。\n"
							+ "您购买的产品有：\n";
					int count=0;
					if(list!=null){
						for (OrderDetail od : list) {
							text = text+ od.getDishName()+" X  "+od.getDishNum()+"\n";
							count+=od.getDishNum();
						}
					}
					text = text + "共计："+count+"件\n";
					text = text + "送餐地址:"+order.getAddr()+"\n"
							+ "联系电话:"+order.getPhone()+"\n"
							+ "送餐时间为:"+DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime())+"\n"
							+"本订单无食品质量问题不可退换。\n"
							+"如有疑问请拨打电话:"+Context.WX_TAKE_PHONE+"，Mr.Lunch。";
				}else {
					text =member.getTrueName()+"您好。\n" 
							+"您"+order.getDay()+"的自取订单已经预定成功。\n"
							+ "您购买的产品有：";
					int count=0;
					if(list!=null){
						for (OrderDetail od : list) {
							text = text+ od.getDishName()+" X  "+od.getDishNum()+"\n";
							count+=od.getDishNum();
						}
					}
					text = text + "共计："+count+"件\n"
							+ "取餐地址:"+Context.WX_TAKE_ADDR+"\n"
							+ "联系电话:"+order.getPhone()+"\n"
							+ "取餐时间为:"+DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime())+"\n"
							+ "本订单无食品质量问题不可退换。\n"
							+ "如有疑问请拨打电话:"+Context.WX_TAKE_PHONE+"，Mr.Lunch。"
							+ "请保留此微信做为取餐凭证。\n";
				}
				
				Map<String, Object> mes = new HashMap<String, Object>();
				mes.put("orderId", orderid);
				mes.put("openid", order.getOpenid());
				mes.put("text", text);
				re.put("payType", 0);
			}
		}
		return re;
	}

	@Override
	public Map<String, Object> prepayConfig(String openid, String gradeId,
			String pageUrl) {
		Map<String, Object> re = new HashMap<String, Object>();
		Member member = memberDao.getMember(openid);
		if(member==null) throw new MlmException("300", "会员不存在");
		Grade grade = memberDao.getGrade(gradeId);
		DefaultWechat wechat = new DefaultWechat();
		wechat.setAppid(Context.WX_APPID);
		wechat.setSecret(Context.WX_SECRET);
		wechat.setTokenManager(tokenManager);
		Map<String, Object> jsconfig = wechat.getJsConfig(Context.WX_APPID, pageUrl);
		Integer wxpay = (grade.getPrepay().multiply(new BigDecimal(100))).intValue();
		String orderid = StringUtil.getUUID();
		re = wxPayService.unifiedPay(openid, orderid, "会员:"+openid, "Mr.Lunch预存充值", "WEB", wxpay, "JSAPI", StringUtil.getLocalIpAddress());
		re.put("jsconfig", jsconfig);
		PrepayLog pl = new PrepayLog();
		pl.setId(orderid);
		pl.setOpenid(openid);
		pl.setTrueName(member.getTrueName());
		pl.setGradeId(gradeId);
		pl.setPrepayId(re.get("prepay_id").toString());
		pl.setPrepay(grade.getPrepay());
		pl.setDay(DateUtil.dateToString(DateUtil.DATE_PATTERN, new Date()));
		pl.setStatus(PrepayLog.STATUS_NOT_PAY);
		orderDao.addPrepayLog(pl);
		re.put("prepay", member.getPrepay().add(grade.getPrepay()));
		return re;
	}

}
