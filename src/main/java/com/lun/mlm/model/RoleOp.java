package com.lun.mlm.model;

import java.util.Date;

public class RoleOp {

	private String id;
	private Integer version;
	private Date createTime;
	private String roleId;
	private String opsId;
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
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOpsId() {
		return opsId;
	}
	public void setOpsId(String opsId) {
		this.opsId = opsId;
	}
	
}
