package com.lun.mlm.model;

import java.math.BigDecimal;
import java.util.Date;

public class PrepayDeduct {

	private String id;
	private Integer version;
	private Date createTime;
	private String openid;
	private String trueName;
	private String phone;
	private String orderid;
	private BigDecimal deduct;
	private BigDecimal pre;
	private BigDecimal later;
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public BigDecimal getDeduct() {
		return deduct;
	}
	public void setDeduct(BigDecimal deduct) {
		this.deduct = deduct;
	}
	public BigDecimal getPre() {
		return pre;
	}
	public void setPre(BigDecimal pre) {
		this.pre = pre;
	}
	public BigDecimal getLater() {
		return later;
	}
	public void setLater(BigDecimal later) {
		this.later = later;
	}
	
}
