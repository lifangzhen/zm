package com.lun.mlm.model;

import java.util.Date;
import java.util.List;

public class Category {

	private String id;
	private Integer version;
	private Date createTime;
	private String name;
	private Integer number;
	private Date updateTime;
	private List<Dish> list;
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<Dish> getList() {
		return list;
	}
	public void setList(List<Dish> list) {
		this.list = list;
	}
	
}
