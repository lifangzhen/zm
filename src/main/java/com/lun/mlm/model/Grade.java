package com.lun.mlm.model;

import java.math.BigDecimal;
import java.util.Date;

public class Grade {

	private String id;
	private Integer version;
	private Date createTime;
	private String name;
	private BigDecimal prepay;
	private Integer rate;
	private Date updateTime;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrepay() {
		return prepay;
	}
	public void setPrepay(BigDecimal prepay) {
		this.prepay = prepay;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
