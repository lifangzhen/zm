package com.lun.mlm.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.OrderDao;
import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Order;
import com.lun.mlm.model.OrderDetail;
import com.lun.mlm.model.PageParam;
import com.lun.mlm.service.OrderService;
import com.lun.mlm.utils.NumberUtil;
import com.lun.mlm.utils.export.DataField;
import com.lun.mlm.utils.export.ExportDataSource;
import com.lun.mlm.utils.export.excel.ExcelDataExportor;
import com.lun.mlm.utils.export.excel.MODE;
import com.wxopen.util.DateUtil;
import com.wxopen.util.StringUtil;

@Controller
public class OrderController extends MlmController {
	private static final Log log = LogFactory.getLog(OrderController.class);
	@Autowired OrderService orderService;
	@Autowired OrderDao orderDao;
	@Autowired MemberDao memberDao;

	@RequestMapping(value="/mlm/orderUi")
	public ModelAndView orderUi() {
		String openid = this.getParaMeter("openid");
		String trueName = this.getParaMeter("trueName");
		String phone = this.getParaMeter("phone");
		String gradeId = this.getParaMeter("gradeId");
		String from = this.getParaMeter("from");
		String to = this.getParaMeter("to");
		String sendfrom = this.getParaMeter("sendfrom");
		String sendto = this.getParaMeter("sendto");
		String takeType = this.getParaMeter("takeType");
		PageParam pp = this.getPageParam();
		Integer tt = null;
		if(StringUtil.isNotBlank(takeType)) tt = Integer.parseInt(takeType);
		List<Order> list = orderService.listOrder(openid, trueName, phone, gradeId,from, to, sendfrom, sendto, pp.getStart(), pp.getEnd(), tt);
		Integer count = orderService.countOrder(openid, trueName, phone,gradeId, from, to, sendfrom, sendto, tt);
		List<Grade> gradeList = memberDao.listGrade();
		ModelAndView mav = new ModelAndView("/mlm/order/list");
		mav.addObject("openid", openid);
		mav.addObject("trueName", trueName);
		mav.addObject("phone", phone);
		mav.addObject("gradeId", gradeId);
		mav.addObject("from", from);
		mav.addObject("to", to);
		mav.addObject("sendfrom", sendfrom);
		mav.addObject("sendto", sendto);
		mav.addObject("takeType", takeType);
		mav.addObject("list", list);
		mav.addObject("gradeList", gradeList);
		mav.addObject("numPerPage",pp.getNumPerPage()); 
		mav.addObject("totalCount",count);
		mav.addObject("currentPage",pp.getPageNum());
		mav.addObject("pageCount",PageParam.pageCount(count, pp.getNumPerPage()));
		return mav;
	}
	
	@RequestMapping(value="/mlm/orderDetailUi")
	public ModelAndView orderDetailUi() {
		String orderId = this.getParaMeter("orderId");
		List<OrderDetail> list = orderDao.listOrderDetail(orderId);
		ModelAndView mav = new ModelAndView("/mlm/order/detail");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value="/mlm/exportOrder/json")
	@ResponseBody
	public void  exportOrder() throws IOException {
		final String openid = this.getParaMeter("openid");
		String tn = this.getParaMeter("trueName");
		final String trueName = new String(tn.getBytes("iso8859-1"),"utf-8");
		final String phone = this.getParaMeter("phone");
		final String gradeId = this.getParaMeter("gradeId");
		final String from = this.getParaMeter("from");
		final String to = this.getParaMeter("to");
		final String sendfrom = this.getParaMeter("sendfrom");
		final String sendto = this.getParaMeter("sendto");
		String takeType = this.getParaMeter("takeType");
		final Integer tt = NumberUtil.parseInt(takeType);
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filename = "订单列表_" + sdf.format(new Date());
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = response.getOutputStream();
			DataField[] dataFields = new DataField[13];
			dataFields[0] = new DataField("订单号", "id",5);
			dataFields[1] = new DataField("下单时间", "updateTime",25);
			dataFields[2] = new DataField("收货时间", "sendTime",25);
			dataFields[3] = new DataField("会员姓名", "name",25);
			dataFields[4] = new DataField("会员等级", "gradeName",25);
			dataFields[5] = new DataField("手机号", "phone",25);
			dataFields[6] = new DataField("地址", "addr",25);
			dataFields[7] = new DataField("支付总金额", "pay",25);
			dataFields[8] = new DataField("微信支付", "wxpay",25);
			dataFields[9] = new DataField("预存支付", "prepay",25);
			dataFields[10] = new DataField("购买数量", "num",25);
			dataFields[11] = new DataField("订单类型", "takeType",25);
			dataFields[12] = new DataField("备注", "mark",25);


			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				public List getData() {

					List<Order> list = orderService.listOrder(openid, trueName, phone, gradeId,from, to, sendfrom, sendto, null, null, tt);
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (Order o : list) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							mapParam.put("id", o.getId());
							mapParam.put("updateTime", o.getUpdateTimeStr());
							mapParam.put("sendTime", o.getSendTimeStr());
							mapParam.put("name", o.getTrueName());
							mapParam.put("gradeName", o.getGradeName());
							mapParam.put("phone", o.getPhone());
							mapParam.put("addr", o.getAddr());
							mapParam.put("pay", o.getPay());
							mapParam.put("wxpay", o.getWxpay());
							mapParam.put("prepay", o.getPrepay());
							mapParam.put("num", o.getDishNum());
							if(o.getTakeType()==1){
								mapParam.put("takeType", "外卖");
							}else{
								mapParam.put("takeType", "自取");
							}
							mapParam.put("mark", o.getMark());
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
		} catch (UnsupportedEncodingException e) {
			log.error("下载报表", e);
		} catch (IOException e) {
			log.error("IO", e);
		}
//		ExportExcel excel=new ExportExcel();  
//	    List<Object> li=new ArrayList<>();
//	    String[] Title={"机构ID","会员编号","类别","名称","省ID","省名称","城市ID","城市名称","详细地址","联系人","性别","联系手机","联系电话","传真","邮箱","QQ","生日","积分","客户等级","现金账户余额","结算方式","客户类型","购买次数","购买支数","创建人ID","创建人姓名","create_time","del","STS","备注","负责人ID","负责人姓名","审核标识","审核人ID ","审核人姓名","审核日期","分配人ID","分配人姓名","分配日期","修改人ID","修改人姓名  ","修改时间"};  
//	    excel.exportExcel(this.response,"客户资料信息.xls",Title, li);   
//		ModelAndView mav = new ModelAndView("/mlm/order/list");
//		return mav;
	}
	
	@RequestMapping(value="/mlm/changeTakeStatus/json")
	@ResponseBody
	public Map<String, Object>  changeTakeStatus() {
		String orderid = this.getParaMeter("orderid");
		if(StringUtil.isBlank(orderid)) throw new MlmException("300", "订单不存在");
		orderDao.changeOrderTakeStatus(orderid);
		
//		Order order = orderService.getOrderAndDetails(orderid);
//		//只有取单时才打印小票
//		if(order.getTakeStatus()==Order.TAKE_STATUS_YES){
//			int height = 175 + 3 * 15 + 20;  
//			// 通俗理解就是书、文档  
//			Book book = new Book();  
//			// 打印格式  
//			PageFormat pf = new PageFormat();  
//			pf.setOrientation(PageFormat.PORTRAIT);  
//			// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
//			Paper p = new Paper();  
//			p.setSize(230, height);  
//			p.setImageableArea(5, -20, 230, height + 20);  
//			pf.setPaper(p);  
//			// 把 PageFormat 和 Printable 添加到书中，组成一个页面  
//			book.append(new PrintUtil(order), pf);  
//			// 获取打印服务对象  
//			PrinterJob job = PrinterJob.getPrinterJob();  
//			job.setPageable(book);  
//			try {  
//				job.print();  
//			} catch (PrinterException e) {  
//				log.info("打印失败");
//			}  
//		}
		
		return success();
	}
	
	@RequestMapping(value = "h5/orderPrint")
	public ModelAndView orderPrint() {
		String id = this.getParaMeter("id");
		Order order = orderDao.getOrder(id);
		if(order!=null) {
			order.setSendTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getSendTime()));
			order.setUpdateTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, order.getUpdateTime()));
		}
		List<OrderDetail> list = orderDao.listOrderDetail(id);
		ModelAndView mav = new ModelAndView("/mlm/orderPrint");
		mav.addObject("order", order);
		mav.addObject("list", list);
		return mav;
	}
}
