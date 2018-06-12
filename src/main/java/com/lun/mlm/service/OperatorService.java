package com.lun.mlm.service;

import java.util.List;
import java.util.Map;

import com.lun.mlm.model.Role;

public interface OperatorService {

	/**
	 * 获取角色权限
	 * @param roleId
	 * @return
	 */
	public List<Map<String, Object>>listOpsByRoleId(String roleId);
	
	/**
	 * 获取角色列表
	 * @param name
	 * @return
	 */
	public List<Role> listRolesByName(String name, Integer start, Integer end); 
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void deleteRole(String roleId);
	
	/**
	 * 角色权限树
	 * @param roleId
	 * @return
	 */
	public String getMenuTree(String roleId);
	
	/**
	 * 修改角色权限
	 * @param roleId
	 * @param opIds
	 */
	public void updateRoleOp(String roleId, String opIds);
}
