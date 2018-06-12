package com.lun.mlm.dao;

import java.util.List;

import com.lun.mlm.model.Operator;
import com.lun.mlm.model.Ops;
import com.lun.mlm.model.Role;
import com.lun.mlm.model.RoleOp;

public interface OperatorDao {

	/**
	 * 根据角色获取权限
	 * @param roleId
	 * @return
	 */
	public List<Ops> listOpsByRoleId(String roleId);
	
	/**
	 * 添加操作员
	 * @param oper
	 */
	public void addOperator(Operator oper);
	
	/**
	 * 根据LoginName获取operator
	 * @param loginName
	 * @return
	 */
	public Operator getOperatorByLoginName(String loginName);
	
	/**
	 * 更新operator
	 * @param record
	 */
	public void updateOperator(Operator record);
	
	/**
	 * 获取角色相关的操作员
	 * @param roleId
	 * @return
	 */
	public List<Operator> listOperatorByRoleId(String roleId);
	
	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteOperator(String id);
	
	/**
	 * 获取角色列表
	 * @param name
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Role> listRolesByName(String name, Integer start, Integer end);
	
	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	public Role getRole(String id);
	
	/**
	 * 角色数量
	 * @param name
	 * @return
	 */
	public Integer countRolesByName(String name);
	
	/**
	 * 添加角色
	 * @param role
	 */
	public void addRole(Role role);
	
	/**
	 * 角色相关操作员数量
	 * @param roleId
	 * @return
	 */
	public Integer countOperatorByRoleId(String roleId);
	
	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteRole(String id);
	
	/**
	 * 删除角色权限
	 * @param roleId
	 */
	public void deleteRoleOp(String roleId);
	
	/**
	 * 获取角色权限
	 * @param roleId
	 * @return
	 */
	public List<RoleOp> listRoleOp(String roleId);
	
	/**
	 * 批量添加角色权限
	 * @param list
	 */
	public void addRoleOp(List<RoleOp> list);
	
}
