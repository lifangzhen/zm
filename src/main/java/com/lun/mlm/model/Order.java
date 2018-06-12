package com.lun.mlm.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {

	public static final Integer STATUS_DELETE = 0;                 //删除
	public static final Integer STATUS_NOT_PAY = 1;             //未支付
	public static final Integer STATUS_PAY = 2;                        //已支付
	
	public static final Integer TAKE_TYPE_OUT = 1;                 //外卖
	public static final Integer TAKE_TYPE_SELF = 2;					//自取
	
	public static final Integer PAY_TYPE_PREPAY = 0;          	//预存支付
	public static final Integer PAY_TYPE_WECHAT = 1;          //微信支付
	public static final Integer PAY_TYPE_BOTH = 2;          	   //混合支付
	
	public static final Integer TAKE_STATUS_NO = 0;             //未取单
	public static final Integer TAKE_STATUS_YES = 1;            //已取单
	
	private String id;
	private Integer version;
	private Date createTime;
	private String openid;
	private String trueName;
	private String phone;
	private String gradeId;
	private String gradeName;
	private String addr;
	private BigDecimal pay;
	private BigDecimal wxpay;
	private BigDecimal prepay;
	private Integer dishNum;
	private Integer takeType;
	private Integer payType;
	private String day;
	private Date sendTime;
	private int status;
	private int takeStatus;
	private String mark;
	private Date updateTime;
	
	private String updateTimeStr;
	private String sendTimeStr;
	private List<OrderDetail> list;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public BigDecimal getPay() {
		return pay;
	}
	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	public BigDecimal getWxpay() {
		return wxpay;
	}
	public void setWxpay(BigDecimal wxpay) {
		this.wxpay = wxpay;
	}
	public BigDecimal getPrepay() {
		return prepay;
	}
	public void setPrepay(BigDecimal prepay) {
		this.prepay = prepay;
	}
	public Integer getDishNum() {
		return dishNum;
	}
	public void setDishNum(Integer dishNum) {
		this.dishNum = dishNum;
	}
	public Integer getTakeType() {
		return takeType;
	}
	public void setTakeType(Integer takeType) {
		this.takeType = takeType;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<OrderDetail> getList() {
		return list;
	}
	public void setList(List<OrderDetail> list) {
		this.list = list;
	}
	public int getTakeStatus() {
		return takeStatus;
	}
	public void setTakeStatus(int takeStatus) {
		this.takeStatus = takeStatus;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public String getSendTimeStr() {
		return sendTimeStr;
	}
	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}
	
}
