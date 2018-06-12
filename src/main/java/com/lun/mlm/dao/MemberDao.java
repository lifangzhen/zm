package com.lun.mlm.dao;

import java.util.List;

import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.PrepayDeduct;


public interface MemberDao {

	public List<Grade> listGrade();
	
	public  Grade getGrade(String id);
	
	public void addGrade(Grade grade);
	
	public void updateGrade(Grade grade);
	
	public void deleteGrade(String id);
	
	public void addMember(Member member);
	
	public void updateMember(Member member);
	
	public Member getMember(String id);
	
	public List<Member> listMember(String trueName, String phone, String gradeId, Integer start, Integer end);
	
	public Integer countMember(String trueName, String phone, String gradeId);
	
	public void addPrepayDeduct(PrepayDeduct pd);
}
