package com.lun.mlm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.lun.mlm.MlmException;
import com.lun.mlm.dao.OperatorDao;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.Operator;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.service.OperatorService;
import com.lun.mlm.utils.ConstantsSession;
import com.lun.mlm.utils.Context;
import com.lun.mlm.utils.DateUtil;
import com.lun.mlm.utils.StringUtil;

@Controller
public class LoginController extends MlmController {
	@Autowired OperatorService operatorService;
	@Autowired OperatorDao operatorDao;
	@Autowired WechatDao wechatDao;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login() throws IOException {
		ModelAndView mav = new ModelAndView("/mlm/login");
		return mav;
	}
	
	@RequestMapping(value="/mlm/dologin/json")
	public ModelAndView dologin() {
		ModelAndView mv = new ModelAndView("/mlm/login");
		String loginName = this.request.getParameter("loginName");
		String loginPwd = this.request.getParameter("loginPwd");
		String code = request.getParameter("code");
		String kaptchaExpected = (String)request.getSession() .getAttribute(Constants.KAPTCHA_SESSION_KEY);   
		if(StringUtil.isBlank(code)){
			mv.addObject("codeMsg", "请输入验证码");
			return mv;
		}else if(!code.equalsIgnoreCase(kaptchaExpected)){
			mv.addObject("codeMsg", "验证码有误");
			return mv;
		}
		
		if(StringUtil.isBlank(loginName)) return mv;
		Operator operator = operatorDao.getOperatorByLoginName(loginName);
		mv.addObject("loginName", loginName);
		if(operator==null){
			mv.addObject("loginNameMsg","登录名不存在");
			return mv;
		}
		if (!operator.getPasswd().equals(StringUtil.getMD5(loginPwd))) {
			mv.addObject("loginPwdMsg","密码错误");
			return mv;
		}
		
		this.setSession(ConstantsSession.ROLE_ID, operator.getRoleId());
		this.setSession(ConstantsSession.LOGIN_NAME, loginName);
		this.setSession(ConstantsSession.LINK_MAN, operator.getLinkMan());
		this.setSession(ConstantsSession.LINK_MOBILE, operator.getLinkMobile());
		WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
		ModelAndView mav = new ModelAndView("/mlm/index");
		mav.addObject("loginTime", DateUtil.currenDateToString());
		mav.addObject("loginName", loginName);
		mav.addObject("tree", this.buildPermissionTree(operator.getRoleId()));
		mav.addObject("wp", wp);
		return mav;
	}
	
	@RequestMapping(value="/logoutConfirm", method=RequestMethod.GET)
	public ModelAndView logoutConfirm(HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView("/mlm/logoutConfirm");
		return mav;
	}
	
	@RequestMapping(value="/mlm/info", method=RequestMethod.GET)
	public ModelAndView info(HttpServletResponse response) throws IOException {
		String loginName = (String) this.getSession(ConstantsSession.LOGIN_NAME);
		String linkMan = (String) this.getSession(ConstantsSession.LINK_MAN);
		String linkMobile = (String) this.getSession(ConstantsSession.LINK_MOBILE);
		ModelAndView mav = new ModelAndView("/mlm/user/info");
		mav.addObject("loginName", loginName);
		mav.addObject("linkMan", linkMan);
		mav.addObject("linkMobile", linkMobile);
		return mav;
	}
	
	@RequestMapping(value="/mlm/changePwd", method=RequestMethod.GET)
	public ModelAndView changePwd(HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView("/mlm/user/changePwd");
		return mav;
	}
	
	@RequestMapping(value="/mlm/changePwd/json")
	@ResponseBody
	public Map<String, Object> changePwd(HttpServletRequest request) throws IOException {
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String newPwd2 = request.getParameter("newPwd2");
		
		String loginName = (String) this.getSession("loginName");
		Operator operator = operatorDao.getOperatorByLoginName(loginName);
		
		if(operator==null) throw new MlmException("300", "请先登录");
		if(!newPwd.equals(newPwd2)) throw new MlmException("300", "两次密码不一致");
		if(oldPwd.equals(newPwd)) throw new MlmException("300", "新密码和旧密码不能一样");
		if (!operator.getPasswd().equals(StringUtil.getMD5(oldPwd))) throw new MlmException("300", "旧密码有误");
		
		Operator record = new Operator();
		record.setId(operator.getId());
		record.setPasswd(StringUtil.getMD5(newPwd));
		operatorDao.updateOperator(record);
		return success(true);
	}
	
	/**
	 * 更新系统参数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/mlm/updateParam/json")
	@ResponseBody
	public Map<String, Object> updateParam() throws IOException {
		String roleId = (String) this.getSession(ConstantsSession.ROLE_ID);
		if(!com.lun.mlm.utils.Constants.ROLE_SUPER_ADMIN.equals(roleId)) throw new MlmException("300", "非超级管理员不能修改");
		String wechatId = this.request.getParameter("wechatId");
		String mchId = this.request.getParameter("mchId");
		String apiKey = this.request.getParameter("apiKey");
		String minPay = this.request.getParameter("minPay");
		String notifyOpenid = this.request.getParameter("notifyOpenid");
		if(!StringUtil.isNumeric(minPay)) throw new MlmException("300", "金额不合法");
		Integer min = Integer.parseInt(minPay);
		WechatParam wp = new WechatParam();
		wp.setId(Context.WX_APPID);
		wp.setWechatId(wechatId);
		wp.setMchId(mchId);
		wp.setApiKey(apiKey);
		wp.setMinPay(min);
		wp.setNotifyOpenid(notifyOpenid);
		wechatDao.updateParam(wp);
		return success();
	}
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletResponse response) throws IOException {
		this.removeAllSession();
		ModelAndView mav = new ModelAndView("/mlm/login");
		return mav;
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	public String buildPermissionTree(String roleId) {
		List treeData = null;
		try {
			treeData = operatorService.listOpsByRoleId(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer strJson = new StringBuffer();
		buildAdminPermissionTree("0", strJson, treeData);
		return strJson.toString();
	}
	
private void buildAdminPermissionTree(String pId, StringBuffer treeBuf, List menuList) {
		
		List<Map> listMap = getSonMenuListByPid(pId.toString(), menuList);
		for (Map map : listMap) {
			String id = map.get("id").toString();// id
			String name = map.get("name").toString();// 名称
			String isLeaf = map.get("isLeaf").toString();// 是否叶子
			String level = map.get("level").toString();// 菜单层级（1、2、3、4）
			String url = map.get("url").toString(); // 访问地址
			String navTabId = map.get("rel").toString(); //用于刷新页面
			
			if ("1".equals(level)){
				treeBuf.append("<div class='accordionHeader'>");
				treeBuf.append("<h2>" + name + "</h2>");
				treeBuf.append("</div>");
				treeBuf.append("<div class='accordionContent'>");
			}
			
			if ("1".equals(isLeaf)) {
				treeBuf.append("<li><a href='" + url + "' target='navTab' rel='" + navTabId + "'>" + name + "</a></li>");
			} else {
				
				if ("1".equals(level)){
					treeBuf.append("<ul class='tree treeFolder'>");
				}else{
					treeBuf.append("<li><a>" + name + "</a>");
					treeBuf.append("<ul>");
				}
				
				buildAdminPermissionTree(id, treeBuf, menuList);
				
				if ("1".equals(level)){
					treeBuf.append("</ul>");
				}else{
					treeBuf.append("</ul></li>");
				}
				
			}
			
			if ("1".equals(level)){
				treeBuf.append("</div>");
			}
		}

	}

	private List<Map> getSonMenuListByPid(String pId, List menuList) {
		List sonMenuList = new ArrayList<Object>();
		for (Object menu : menuList) {
			Map map = (Map) menu;
			if (map != null) {
				String parentId = map.get("pId").toString();// 父id
				if (parentId.equals(pId)) {
					sonMenuList.add(map);
				}
			}
		}
		return sonMenuList;
	}
}
