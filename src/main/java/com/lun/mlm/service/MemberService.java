package com.lun.mlm.service;

import java.math.BigDecimal;
import java.util.List;

import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;

public interface MemberService {
	
	/**
	 * 充值列表
	 * @return
	 */
	public List<Grade>listGradeByCharge();

	/**
	 * 查询会员列表
	 * @param trueName
	 * @param gradeId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Member> listMember(String trueName, String phone, String gradeId, Integer start, Integer end);
	
	/**
	 * 获取member，若member不存在
	 * 则先添加会员，再返回
	 * @param id
	 * @param addIfNotExit
	 * @return
	 */
	public Member getMember(String id, boolean addIfNotExit);
	
	/**
	 * 添加修改等级
	 * @param id
	 * @param name
	 * @param prepay
	 * @param rate
	 */
	public void addOrUpdateGrade(String id, String name, BigDecimal prepay, Integer rate);
	
	/**
	 * 获取默认等级，即充值最低的等级
	 * @return
	 */
	public Grade getDefalutGrade();
}
