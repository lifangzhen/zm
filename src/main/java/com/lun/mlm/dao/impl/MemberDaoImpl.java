package com.lun.mlm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.PrepayDeduct;
import com.wxopen.util.StringUtil;

@Repository("memberDao")
public class MemberDaoImpl extends SqlMapClientDaoSupport implements MemberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Grade> listGrade() {
		return this.getSqlMapClientTemplate().queryForList("Member_SqlMap.listGrade", null);
	}

	@Override
	public Grade getGrade(String id) {
		if(StringUtil.isBlank(id)) throw new MlmException("300", "id不能为空");
		return (Grade) this.getSqlMapClientTemplate().queryForObject("Member_SqlMap.getGrade", id);
	}

	@Override
	public void addGrade(Grade grade) {
		if(grade!=null&&StringUtil.isBlank(grade.getId())) throw new MlmException("300", "id不能为空");
		this.getSqlMapClientTemplate().insert("Member_SqlMap.addGrade", grade);
	}

	@Override
	public void updateGrade(Grade grade) {
		if(grade!=null&&StringUtil.isBlank(grade.getId())) throw new MlmException("300", "id不能为空");
		this.getSqlMapClientTemplate().update("Member_SqlMap.updateGrade", grade);
	}
	
	@Override
	public void deleteGrade(String id) {
		if(StringUtil.isBlank(id)) throw new MlmException("300", "id不能为空");
		this.getSqlMapClientTemplate().delete("Member_SqlMap.deleteGrade", id);
	}
	
	@Override
	public void addMember(Member member) {
		this.getSqlMapClientTemplate().insert("Member_SqlMap.addMember", member);
	}
	
	@Override
	public void updateMember(Member member) {
		this.getSqlMapClientTemplate().update("Member_SqlMap.updateMember", member);
	}
	
	@Override
	public Member getMember(String id) {
		return (Member) this.getSqlMapClientTemplate().queryForObject("Member_SqlMap.getMember", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> listMember(String trueName, String phone, String gradeId,
			Integer start, Integer end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trueName", trueName);
		map.put("phone", phone);
		map.put("gradeId", gradeId);
		map.put("start", start);
		map.put("end", end);
		return this.getSqlMapClientTemplate().queryForList("Member_SqlMap.listMember", map);
	}

	@Override
	public Integer countMember(String trueName, String phone, String gradeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trueName", trueName);
		map.put("phone", phone);
		map.put("gradeId", gradeId);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("Member_SqlMap.countMember", map);
	}

	@Override
	public void addPrepayDeduct(PrepayDeduct pd) {
		this.getSqlMapClientTemplate().insert("Member_SqlMap.addPrepayDeduct", pd);
	}

}
