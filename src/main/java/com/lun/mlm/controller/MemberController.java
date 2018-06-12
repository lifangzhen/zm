package com.lun.mlm.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.OrderDao;
import com.lun.mlm.model.Category;
import com.lun.mlm.model.Grade;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.PageParam;
import com.lun.mlm.model.PrepayLog;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.service.OrderService;
import com.lun.mlm.utils.ConstantsRefresh;
import com.lun.mlm.utils.StringUtil;

@Controller
public class MemberController extends MlmController {
	@Autowired MemberService memberService;
	@Autowired MemberDao memberDao;
	@Autowired OrderService orderService;
	@Autowired OrderDao orderDao;

	/**
	 * 会员列表页面
	 * @return
	 */
	@RequestMapping(value="/mlm/memberListUi")
	public ModelAndView memberListUi() {
		String trueName = this.getParaMeter("trueName");
		String phone = this.getParaMeter("phone");
		String gradeId = this.getParaMeter("gradeId");
		PageParam pp = this.getPageParam();
		List<Member> list = memberService.listMember(trueName, phone, gradeId, pp.getStart(), pp.getEnd());
		Integer count = memberDao.countMember(trueName, phone, gradeId);
		List<Grade> gradeList = memberDao.listGrade();
		ModelAndView mav = new ModelAndView("/mlm/member/member/list");
		mav.addObject("trueName", trueName);
		mav.addObject("phone", phone);
		mav.addObject("gradeId", gradeId);
		mav.addObject("list", list);
		mav.addObject("gradeList", gradeList);
		mav.addObject("numPerPage",pp.getNumPerPage()); 
		mav.addObject("totalCount",count);
		mav.addObject("currentPage",pp.getPageNum());
		mav.addObject("pageCount",PageParam.pageCount(count, pp.getNumPerPage()));
		return mav;
	}
	
	/**
	 * 会员更改页
	 * @return
	 */
	@RequestMapping(value="/mlm/updateMemberUi")
	public ModelAndView updateMemberUi() {
		String id = this.getParaMeter("id");
		Member member = memberDao.getMember(id);
		List<Grade> gradeList = memberDao.listGrade();
		ModelAndView mav = new ModelAndView("/mlm/member/member/update");
		mav.addObject("member", member);
		mav.addObject("gradeList", gradeList);
		return mav;
	}
	
	/**
	 * 更改会员
	 * @return
	 */
	@RequestMapping(value="/mlm/updateMember/json")
	@ResponseBody
	public Map<String, Object>  updateMember() {
		String openid = this.getParaMeter("openid");
		String trueName = this.getParaMeter("trueName");
		String phone = this.getParaMeter("phone");
		String addr = this.getParaMeter("addr");
		String gradeId = this.getParaMeter("gradeId");
		String prepay = this.getParaMeter("prepay");
		Member member = new Member();
		member.setId(openid);
		member.setTrueName(trueName);
		member.setPhone(phone);
		member.setAddr(addr);
		member.setGradeId(gradeId);
		if(StringUtil.isNotBlank(prepay)){
			member.setPrepay(new BigDecimal(prepay));
		}
		memberDao.updateMember(member);
		return success(true, ConstantsRefresh.MEMBER_LIST);
	}
	
	/**
	 * 充值页面
	 * @return
	 */
	@RequestMapping(value="/mlm/chargeListUi")
	public ModelAndView chargeListUi() {
		String openid = this.getParaMeter("openid");
		String trueName = this.getParaMeter("trueName");
		String day = this.getParaMeter("day");
		PageParam pp = this.getPageParam();
		List<PrepayLog> list = orderService.listPrepayLog(openid, trueName, day, pp.getStart(), pp.getEnd());
		Integer count = orderDao.countPrepayLog(openid, trueName, day);
		
		ModelAndView mav = new ModelAndView("/mlm/member/charge/list");
		mav.addObject("openid", openid);
		mav.addObject("trueName", trueName);
		mav.addObject("day", day);
		mav.addObject("list", list);
		mav.addObject("numPerPage",pp.getNumPerPage()); 
		mav.addObject("totalCount",count);
		mav.addObject("currentPage",pp.getPageNum());
		mav.addObject("pageCount",PageParam.pageCount(count, pp.getNumPerPage()));
		return mav;
	}
	
	/**
	 * 会员等级页面
	 * @return
	 */
	@RequestMapping(value="/mlm/gradeUi")
	public ModelAndView gradeUi() {
		List<Grade> list = memberDao.listGrade();
		ModelAndView mav = new ModelAndView("/mlm/member/charge/grade");
		mav.addObject("list", list);
		return mav;
	}
	
	/**
	 * 添加会员等级页面
	 * @return
	 */
	@RequestMapping(value="/mlm/addGradeUi")
	public ModelAndView addGradeUi() {
		String id = this.getParaMeter("id");
		Grade grade = new Grade();
		if(StringUtil.isNotBlank(id)) grade = memberDao.getGrade(id);
		ModelAndView mav = new ModelAndView("/mlm/member/charge/add");
		mav.addObject("grade", grade);
		return mav;
	}
	
	/**
	 * 添加会员等级
	 * @return
	 */
	@RequestMapping(value="/mlm/addGrade/json")
	@ResponseBody
	public Map<String, Object>  addGrade() {
		String id = this.getParaMeter("id");
		String name = this.getParaMeter("name");
		String prepayStr = this.getParaMeter("prepayStr");
		String rateStr = this.getParaMeter("rateStr");
		if(StringUtil.isBlank(prepayStr) || StringUtil.isBlank(rateStr)) throw new MlmException("300", "参数不能为空");
		
		BigDecimal prepay = new BigDecimal(prepayStr);
		int rate = Integer.parseInt(rateStr);
		memberService.addOrUpdateGrade(id, name, prepay, rate);
		
		return success(true, ConstantsRefresh.GRADE_UI);
	}
	
	/**
	 * 删除会员等级
	 * @return
	 */
	@RequestMapping(value="/mlm/deleteGrade/json")
	@ResponseBody
	public Map<String, Object>  deleteGrade() {
		String id = this.getParaMeter("id");
		Integer count = memberDao.countMember(null, null, id);
		if(count>0) throw new MlmException("300", "有"+count+"个会员已经使用该等级");
		memberDao.deleteGrade(id);
		return success();
	}
	
}
