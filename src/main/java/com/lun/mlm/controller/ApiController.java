package com.lun.mlm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.OrderDao;
import com.lun.mlm.dao.SailDao;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.Category;
import com.lun.mlm.model.Dish;
import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.Order;
import com.lun.mlm.model.OrderDetail;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.service.OrderService;
import com.lun.mlm.service.WxPayService;
import com.lun.mlm.utils.Context;
import com.lun.mlm.utils.StringUtil;
import com.lun.mlm.web.annotations.TcResponseBody;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.TokenManager;
import com.townmc.mp.json.JSONObject;

@Controller
public class ApiController extends BaseController{
	@Autowired SailDao sailDao;
	@Autowired MemberService memberService;
	@Autowired MemberDao memberDao;
	@Autowired OrderService orderService;
	@Autowired TokenManager tokenManager;
	@Autowired OrderDao orderDao;
	@Autowired WxPayService wxPayService;
	@Autowired WechatDao wechatDao;

	/**
	 * 首页接口
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/h5/index/json")
	@TcResponseBody
	public Map<String, Object> index(
			@RequestParam(required = true) String openid) {
		List<Category> categoryList = sailDao.listCategory(null, null, null);
		if(categoryList!=null && categoryList.size()>0) {
			for(Category c:categoryList){
				List<Dish> dishList = sailDao.listDishByCategoryId(c.getId(), Dish.STATUS_NORMAL);
				if(dishList!=null){
					for (Dish dish : dishList) {
						if(dish.getStock() == 0){
							dish.setName(dish.getName()+"<span  style='color:red'>(今日售罄)</span>");
						}
					}
				}
				c.setList(dishList);
			}
		}
		Member member = memberService.getMember(openid, false);
		if(member==null) throw new MlmException("300", "请从微信客户端打开");
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("trueName", member.getTrueName());
		re.put("headImg", member.getHeadImg());
		re.put("prepay", member.getPrepay());
		
		re.put("grade", member.getGrade().getName());
		re.put("rate", member.getGrade().getRate());
		re.put("categoryList", categoryList);
		re.put("phone", member.getPhone());
		re.put("addr", member.getAddr());
		return re;
	}
	
	/**
	 * 根据类别获取菜品
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/h5/listdish/json")
	@TcResponseBody
	public Map<String, Object> listdish(
			@RequestParam(required = true) String categoryId) {
		List<Dish> dishList = sailDao.listDishByCategoryId(categoryId, Dish.STATUS_NORMAL);
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("dishList", dishList);
		return re;
	}
	
	/**
	 * 提交订单
	 * @param openid
	 * @param takeType
	 * @param payType
	 * @param sendTime
	 * @param mark
	 * @param orderjson
	 * @return
	 */
	@RequestMapping(value = "/h5/saveorder/json")
	@TcResponseBody
	public Map<String, Object> saveorder(
			@RequestParam(required = true) String openid,
			@RequestParam(required = true) String takeType,
			@RequestParam(required = true) String payType,
			@RequestParam(required = true) String phone,
			@RequestParam(required = true) String addr,
			@RequestParam(required = true) String sendTime,
			@RequestParam(required = false) String mark,
			@RequestParam(required = false) String orderjson
			) {
		 Integer take =Order.TAKE_TYPE_OUT;
		if(StringUtil.isNotBlank(takeType)) take = Integer.parseInt(takeType);
		String orderId = orderService.createOrder(openid, take, sendTime, phone, addr, mark, orderjson);
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("orderId", orderId);
		return re;
	}
	
	@RequestMapping(value = "h5/paydetailUi")
	public ModelAndView paydetailUi() {
		String  orderId = this.getParaMeter("orderId");
		Order order = orderDao.getOrder(orderId);
		if(order==null) throw new MlmException("300", "订单不存在");
		ModelAndView mav = new ModelAndView("/wx/payDetail");
		mav.addObject("orderId", orderId);
		mav.addObject("total", order.getPay());
		mav.addObject("wxpay", order.getWxpay());
		mav.addObject("prepay", order.getPrepay());
		return mav;
	}
	
	@RequestMapping(value = "/h5/payconfig/json")
	@TcResponseBody
	public Map<String, Object> payconfig(
			@RequestParam(required = true) String openid,
			@RequestParam(required = true) String orderId,
			@RequestParam(required = true) String type){
		String pageUrl = "http://"+request.getServerName();
		if("payDetail".equals(type)){
			pageUrl = pageUrl + "/h5/paydetailUi?orderId="+orderId;
		}else if("order".equals(type)){
			pageUrl = pageUrl +"/h5/myorderUi";
		}
		Map<String, Object> re = orderService.payConfig(openid, orderId, pageUrl);
		return re;
	}
	
	@RequestMapping(value = "/h5/prepayconfig/json")
	@TcResponseBody
	public Map<String, Object> prepayconfig(
			@RequestParam(required = true) String openid,
			@RequestParam(required = true) String gradeId){
		String pageUrl = "http://"+request.getServerName()+"/h5/myaccountUi";
		Map<String, Object> re = orderService.prepayConfig(openid, gradeId, pageUrl);
		return re;
	}
	
	/**
	 * 订单列表
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/h5/orderList/json")
	@TcResponseBody
	public Map<String, Object> orderList(
			@RequestParam(required = true) String openid,
			@RequestParam(required = false) String page) {
		
		if(StringUtil.isBlank(page)) page="1";
		Integer start = (Integer.parseInt(page)-1)*10;
		Integer end = start + 10;
		List<Order>list=orderService.listOrderAndDetails(openid, null, null, null, start, end, null);
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("list", list);
		return re;
	}
	
	@RequestMapping(value = "/h5/confirmOrder/json")
	@TcResponseBody
	public Map<String, Object> confirmOrder(
			@RequestParam(required = true) String orderId){
		//TODO
		Order order = orderDao.getOrder(orderId);
		order.setStatus(Order.STATUS_PAY);
		orderDao.updateOrder(order);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}
	
	/**
	 * 我的页面
	 * @param openid
	 * @return
	 */
	@RequestMapping(value = "/h5/core/json")
	@TcResponseBody
	public Map<String, Object> core(
			@RequestParam(required = true) String openid) {
		List<Grade> list =memberService.listGradeByCharge();
		Member member = memberDao.getMember(openid);
		if(member==null) throw new MlmException("300", "请从公众号菜单进入");
		Grade grade = memberDao.getGrade(member.getGradeId());
		//只能充值高于或等于当前会员的等级
//		if(list!=null&&list.size()>0){
//			for(int i=list.size()-1;i>=0;i--){
//				if(list.get(i).getRate()> grade.getRate()){
//					list.remove(list.get(i));
//				}
//			}
//		}
		String trueName = member.getTrueName();
		if(trueName.length()>3){
			trueName = trueName.substring(0,3)+"..";
		}
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("ruleList", list);
		re.put("rate", grade.getRate());
		re.put("trueName", trueName);
		re.put("headImg", member.getHeadImg());
		re.put("prepay", member.getPrepay());
		re.put("phone", member.getPhone());
		re.put("addr", member.getAddr());
		return re;
	}
	
	@RequestMapping(value = "/h5/charge/json")
	@TcResponseBody
	public Map<String, Object> charge(
			@RequestParam(required = true) String openid,
			@RequestParam(required = true) String gradeId) {
		
		Grade grade = memberDao.getGrade(gradeId);
		Member member = memberDao.getMember(openid);
		BigDecimal prepay = member.getPrepay().add(grade.getPrepay());
		member.setPrepay(prepay);
		memberDao.updateMember(member);
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("prepay", prepay);
		return re;
	}
	
	/**
	 * 保存收货信息
	 * @param openid
	 * @param trueName
	 * @param phone
	 * @param addr
	 * @return
	 */
	@RequestMapping(value = "/h5/saveAddr/json")
	@TcResponseBody
	public Map<String, Object> saveAddr(
			@RequestParam(required = true) String openid,
			@RequestParam(required = true) String trueName,
			@RequestParam(required = true) String phone,
			@RequestParam(required = true) String addr) {
		if(StringUtil.isBlank(openid)) throw new MlmException("300", "保存失败");
		Member member = new Member();
		member.setId(openid);
		member.setTrueName(trueName);
		member.setPhone(phone);
		member.setAddr(addr);
		memberDao.updateMember(member);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}
	
}
