package com.lun.mlm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.utils.Context;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.TokenManager;
import com.wxopen.model.UserInfo;
import com.wxopen.util.StringUtil;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	private static final Log log = LogFactory.getLog(MemberServiceImpl.class);
	@Autowired MemberDao memberDao;
	@Autowired TokenManager tokenManager;
	
	@Override
	public List<Grade> listGradeByCharge() {
		List<Grade> list = memberDao.listGrade();
		//除去充值为0的项
		List<Grade> rlist = new ArrayList<Grade>();
		if(list!=null&&list.size()>0){
			for(int i=list.size()-1;i>=0;i--){
				if(list.get(i).getPrepay().compareTo(new BigDecimal(0))==0){
					list.remove(list.get(i));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<Member> listMember(String trueName, String phone, String gradeId,
			Integer start, Integer end) {
		List<Member> memberList = memberDao.listMember(trueName, phone, gradeId, start, end);
		List<Grade> gradeList = memberDao.listGrade();
		if(memberList!=null&&memberList.size()>0){
			for(Member m:memberList){
				for(Grade g:gradeList){
					if(g.getId().equals(m.getGradeId())){
						m.setGrade(g);
					}
				}
			}
		}
		return memberList;
	}

	@Override
	public Member getMember(String id, boolean addIfNotExit) {
		Member member = memberDao.getMember(id);
		if(member!=null &&StringUtil.isNotBlank(member.getGradeId())){
			Grade grade = memberDao.getGrade(member.getGradeId());
			member.setGrade(grade);
		}else if(member==null && addIfNotExit){
			DefaultWechat wechat = new DefaultWechat();
			wechat.setAppid(Context.WX_APPID);
			wechat.setSecret(Context.WX_SECRET);
			wechat.setTokenManager(tokenManager);
			UserInfo info = wechat.getUser(id);
			Grade defautGrade = this.getDefalutGrade();
			String nickName = info.getNickname();
			if(StringUtil.isNotBlank(nickName)){
				nickName = nickName.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5_#@*$-]", "");//去掉未知符号
				if (nickName.length() > 20) {
					nickName = nickName.substring(0, 20);
				}
			}
			if(defautGrade == null) throw new MlmException("300", "找不到默认的会员等级");
			member = new Member();
			member.setId(id);
			member.setNickName(nickName);
			member.setTrueName(nickName);
			member.setHeadImg(info.getHeadimgurl());
			member.setSex(info.getSex());
			member.setCity(info.getCity());
			member.setSubscribe(info.getSubscribe());
			member.setSubscribeTime(new Date(info.getSubscribeTime()));
			member.setUnionid(info.getUnionid());
			member.setGradeId(defautGrade.getId());
			member.setGrade(defautGrade);
			member.setPrepay(new BigDecimal(0));
			memberDao.addMember(member);
		}
		return member;
	}

	@Override
	public void addOrUpdateGrade(String id, String name, BigDecimal prepay,
			Integer rate) {
		//等级校验
		if(rate<0) throw new MlmException("300", "折扣最小为0%,表示白送");
		if(rate>100) throw new MlmException("300", "折扣最大为100%,表示没有折扣");
		List<Grade> list = memberDao.listGrade();
		if(list!=null && list.size()>0){
			for(Grade g:list){
				if(StringUtil.isBlank(id)){
					if(g.getName().equals(name)) throw new MlmException("300", "等级名称重复了");
					if(g.getPrepay().compareTo(prepay)==0) throw new MlmException("300", "已经有充值["+prepay+"]的等级了");
					if(g.getRate().equals(rate)) throw new MlmException("300", "已经有折扣为["+rate+"]的等级了");
				}else{
					if(g.getName().equals(name)&&!g.getId().equals(id)) throw new MlmException("300", "等级名称重复了");
					if(g.getPrepay().compareTo(prepay)==0&&!g.getId().equals(id)) throw new MlmException("300", "已经有充值["+prepay+"]的等级了");
					if(g.getRate() == rate&&!g.getId().equals(id)) throw new MlmException("300", "已经有折扣为["+rate+"]的等级了");
				}
			}
		}
		Grade grade = new Grade();
		grade.setName(name);
		grade.setPrepay(prepay);
		grade.setRate(rate);
		if(StringUtil.isBlank(id)){
			id = StringUtil.getUUID();
			grade.setId(id);
			memberDao.addGrade(grade);
		}else{
			grade.setId(id);
			memberDao.updateGrade(grade);
		}
	}

	@Override
	public Grade getDefalutGrade() {
		List<Grade> list = memberDao.listGrade();
		return list==null?null:list.get(list.size()-1);
	}

}
