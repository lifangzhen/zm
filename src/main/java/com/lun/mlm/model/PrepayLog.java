package com.lun.mlm.model;

import java.math.BigDecimal;
import java.util.Date;

public class PrepayLog {
	public static final Integer STATUS_DELETE = 0;       //删除
	public static final Integer STATUS_NOT_PAY = 1;   //未付款
	public static final Integer STATUS_PAY = 2;             //已付款

	private String id;
	private Integer version;
	private Date createTime;
	private String openid;
	private String trueName;
	private String gradeId;
	private String prepayId;
	private BigDecimal prepay;
	private String day;
	private Integer status;
	private Date updateTime;
	
	private String updateTimeStr;
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
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public BigDecimal getPrepay() {
		return prepay;
	}
	public void setPrepay(BigDecimal prepay) {
		this.prepay = prepay;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
}
