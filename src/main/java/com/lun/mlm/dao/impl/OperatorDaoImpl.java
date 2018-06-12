package com.lun.mlm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.OperatorDao;
import com.lun.mlm.model.Operator;
import com.lun.mlm.model.Ops;
import com.lun.mlm.model.Role;
import com.lun.mlm.model.RoleOp;
import com.wxopen.util.StringUtil;

@Repository("operatorDao")
public class OperatorDaoImpl extends SqlMapClientDaoSupport implements OperatorDao {

	@Override
	public List<Ops> listOpsByRoleId(String roleId) {
		return this.getSqlMapClientTemplate().queryForList("Operator_SqlMap.listOpsByRoleId", roleId);
	}
	
	@Override
	public void addOperator(Operator oper) {
		if(oper==null || StringUtil.isBlank(oper.getId())) throw new MlmException("300", "operator param error");
		this.getSqlMapClientTemplate().insert("Operator_SqlMap.addOperator", oper);
	}

	@Override
	public Operator getOperatorByLoginName(String loginName) {
		return (Operator) this.getSqlMapClientTemplate().queryForObject("Operator_SqlMap.getOperatorByLoginName", loginName);
	}
	
	@Override
	public void updateOperator(Operator record) {
		this.getSqlMapClientTemplate().update("Operator_SqlMap.updateOperator", record);
	}
	
	@Override
	public List<Operator> listOperatorByRoleId(String roleId) {
		return this.getSqlMapClientTemplate().queryForList("Operator_SqlMap.listOperatorByRoleId", roleId);
	}
	
	@Override
	public void deleteOperator(String id) {
		this.getSqlMapClientTemplate().delete("Operator_SqlMap.deleteOperator", id);
	}

	@Override
	public List<Role> listRolesByName(String name, Integer start, Integer end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("name", name);
		return this.getSqlMapClientTemplate().queryForList("Operator_SqlMap.listRolesByName", map);
	}
	
	@Override
	public Role getRole(String id) {
		return (Role) this.getSqlMapClientTemplate().queryForObject("Operator_SqlMap.getRole", id);
	}

	@Override
	public Integer countRolesByName(String name) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Operator_SqlMap.countRolesByName", name);
	}

	@Override
	public void addRole(Role role) {
		this.getSqlMapClientTemplate().insert("Operator_SqlMap.addRole", role);
	}

	@Override
	public Integer countOperatorByRoleId(String roleId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Operator_SqlMap.countOperatorByRoleId", roleId);
	}

	@Override
	public void deleteRole(String id) {
		this.getSqlMapClientTemplate().delete("Operator_SqlMap.deleteRole", id);
	}

	@Override
	public void deleteRoleOp(String roleId) {
		this.getSqlMapClientTemplate().delete("Operator_SqlMap.deleteRoleOp", roleId);
	}

	@Override
	public List<RoleOp> listRoleOp(String roleId) {
		return this.getSqlMapClientTemplate().queryForList("Operator_SqlMap.listRoleOp", roleId);
	}

	@Override
	public void addRoleOp(List<RoleOp> list) {
		this.getSqlMapClientTemplate().insert("Operator_SqlMap.addRoleOp", list);
	}

}
