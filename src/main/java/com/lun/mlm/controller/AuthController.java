package com.lun.mlm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.RoleList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.OperatorDao;
import com.lun.mlm.model.Operator;
import com.lun.mlm.model.PageParam;
import com.lun.mlm.model.Role;
import com.lun.mlm.service.OperatorService;
import com.lun.mlm.utils.ConstantsRefresh;
import com.lun.mlm.utils.StringUtil;

/**
 * 权限相关
 *  AuthController
 * @author policy
 * @createdDate:2016年8月22日上午1:00:46
 * @version:1.0
 */
@Controller
public class AuthController extends BaseController {
	@Autowired OperatorService operatorService;
	@Autowired OperatorDao operatorDao;

	@RequestMapping(value="/mlm/listRoleUi")
	public ModelAndView listRoleUi() {
		String roleName = this.request.getParameter("roleName");
		
		PageParam pp = this.getPageParam();
		List<Role> list = operatorService.listRolesByName(roleName, pp.getStart(), pp.getEnd());
		int count = operatorDao.countRolesByName(roleName);
		ModelAndView mav = new ModelAndView("/mlm/role/list");
		mav.addObject("roleName", roleName);
		mav.addObject("list", list);
		mav.addObject("numPerPage",pp.getNumPerPage()); 
		mav.addObject("totalCount",count);
		mav.addObject("currentPage",pp.getPageNum());
		mav.addObject("pageCount",PageParam.pageCount(count, pp.getNumPerPage()));
		return mav;
	}
	
	@RequestMapping(value="/mlm/addRoleUi")
	public ModelAndView addRoleUi() {
		ModelAndView mav = new ModelAndView("/mlm/role/add");
		Map<String, Object> map = new HashMap<String, Object>();
		return mav;
	}
	
	@RequestMapping(value="/mlm/addRole/json")
	@ResponseBody
	public Map<String, Object>  addRole() {
		String roleName = this.getParaMeter("roleName");
		if(StringUtil.isBlank(roleName)) throw new MlmException("300", "不能为空");
		int count = operatorDao.countRolesByName(roleName);
		if(count>0) throw new MlmException("300", "角色已存在");
		Role role = new Role();
		role.setId(StringUtil.getUUID());
		role.setName(roleName);
		operatorDao.addRole(role);
		return success(true, ConstantsRefresh.ROLE_LIST);
	}
	
	@RequestMapping(value="/mlm/deleteRole/json")
	@ResponseBody
	public Map<String, Object> deleteRole(){
		String id = this.getParaMeter("id");
		if(StringUtil.isBlank(id)) throw new MlmException("300", "该角色异常");
		int count = operatorDao.countOperatorByRoleId(id);
		if(count>0) throw new MlmException("300", "有"+count+"个操作员是该角色");
		operatorService.deleteRole(id);
		return success();
	}
	
	@RequestMapping(value="/mlm/authAssignUi")
	public ModelAndView authAssignUi() {
		String roleId = this.getParaMeter("roleId");
		String menuTree = operatorService.getMenuTree(roleId);
		if(StringUtil.isBlank(roleId)) throw new MlmException("300", "角色不存在");
		List<Operator> operatorList = operatorDao.listOperatorByRoleId(roleId);
		ModelAndView mav = new ModelAndView("/mlm/role/authAssign");
		mav.addObject("roleId", roleId);
		mav.addObject("menuTree", menuTree);
		mav.addObject("operatorList", operatorList);
		return mav;
	}
	
	@RequestMapping(value="/mlm/authAssign/json")
	@ResponseBody
	public Map<String, Object> authAssign(){
		String roleId = this.getParaMeter("roleId");
		String menuIds = this.getParaMeter("menuIds");
		if(StringUtil.isBlank(roleId)) throw new MlmException("300", "角色不存在");
		if(StringUtil.isBlank(menuIds)) throw new MlmException("300", "没有权限被选中");
		operatorService.updateRoleOp(roleId, menuIds);
		return success(true);
	}
	
	@RequestMapping(value="/mlm/operatorUi")
	public ModelAndView operatorUi() {
		List<Operator> list = operatorDao.listOperatorByRoleId(null);
		ModelAndView mav = new ModelAndView("/mlm/role/oper/list");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value="/mlm/delOper/json")
	@ResponseBody
	public Map<String, Object>  delOper() {
		String id = this.getParaMeter("id");
		operatorDao.deleteOperator(id);
		return success();
	}
	
	@RequestMapping(value="/mlm/addOperUi")
	public ModelAndView addOperUi() {
		List<Role> roleList = operatorDao.listRolesByName("", null, null);
		ModelAndView mav = new ModelAndView("/mlm/role/oper/add");
		mav.addObject("roleList", roleList);
		return mav;
	}
	
	@RequestMapping(value="/m/addOper/json")
	@ResponseBody
	public Map<String, Object>  addOper() {
		String loginName = this.getParaMeter("loginName");
		String pwd = this.getParaMeter("pwd");
		String pwd2 = this.getParaMeter("pwd2");
		String linkMan = this.getParaMeter("linkMan");
		String linkMobile = this.getParaMeter("linkMobile");
		String roleId = this.getParaMeter("role");
		if(!pwd.equals(pwd2)) throw new MlmException("300", "两次密码不一致");
		Operator opcheck = operatorDao.getOperatorByLoginName(loginName);
		if(opcheck!=null) throw new MlmException("300", "登录名已存在");
		Operator oper = new Operator();
		oper.setId(StringUtil.getUUID());
		oper.setLoginName(loginName);
		oper.setPasswd(StringUtil.md5(pwd));
		oper.setLinkMan(linkMan);
		oper.setLinkMobile(linkMobile);
		oper.setRoleId(roleId);
		operatorDao.addOperator(oper);
		return success(true, ConstantsRefresh.OPERATOR_UI);
	}
	
}
