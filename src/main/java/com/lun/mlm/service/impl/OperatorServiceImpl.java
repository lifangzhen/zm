package com.lun.mlm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.OperatorDao;
import com.lun.mlm.model.Ops;
import com.lun.mlm.model.Role;
import com.lun.mlm.model.RoleOp;
import com.lun.mlm.service.OperatorService;
import com.lun.mlm.utils.DateUtil;
import com.lun.mlm.utils.StringUtil;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService {
	@Autowired OperatorDao operatorDao;
	@Override
	public List<Map<String, Object>> listOpsByRoleId(String roleId) {
		if(StringUtil.isBlank(roleId)) throw new MlmException("300", "缺少角色ID");
		List<Ops> listOps = operatorDao.listOpsByRoleId(roleId);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(listOps!=null && listOps.size()>0){
			for(Ops ao:listOps){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", ao.getId());
				map.put("name", ao.getName());
				map.put("isLeaf", ao.getIsLeaf()+"");
				map.put("level", ao.getLevel()+"");
				map.put("pId", ao.getParentId());
				map.put("url", ao.getUrl());
				map.put("rel", ao.getRel());
				list.add(map);
			}
		}
		return list;
	}
	@Override
	public List<Role> listRolesByName(String name, Integer start, Integer end) {
		List<Role> list = operatorDao.listRolesByName(name, start, end);
		if(list!=null && list.size()>0){
			for(Role r:list){
				r.setCreateTimeStr(DateUtil.dateToString(DateUtil.FULL_TIME_PATTERN, r.getCreateTime()));
			}
		}
		return list;
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void deleteRole(String roleId) {
		operatorDao.deleteRole(roleId);
		operatorDao.deleteRoleOp(roleId);
	}
	
	public String getMenuTree(String roleId) {
		Role role = operatorDao.getRole(roleId);
		if(role==null) throw new MlmException("300", "角色不存在");
		List<RoleOp> roList = operatorDao.listRoleOp(roleId);
		StringBuffer menuIds = new StringBuffer(",");
		if(roList!=null && roList.size()>0){
			for(RoleOp aro:roList){
				menuIds.append(aro.getOpsId()).append(",");
			}
		}
		List<Ops> oList = operatorDao.listOpsByRoleId(null);
		StringBuffer treeBuffer = new StringBuffer();
		this.buildMenuTree("0", treeBuffer, oList, menuIds.toString());
		return treeBuffer.toString();
	}
	
	private void buildMenuTree(String pId, StringBuffer treeBuffer, List<Ops> oList, String menuIds ){
		if (pId.equals("0")) {
			treeBuffer.append("<ul class=\"tree treeFolder treeCheck expand\" >");
		} else {
			treeBuffer.append("<ul>");
		}
		List<Ops> sonMenuList = getSonMenuListByPid(pId.toString(), oList);
		for(Ops o:sonMenuList){
			String menuId = o.getId();
			String parentId = o.getParentId();
			String name = o.getName();
			int isLeaf = o.getIsLeaf();
			if(menuIds.indexOf(","+menuId+",")>-1){
				treeBuffer.append("<li><a menuid='" + menuId + "' checked='true' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " </a>");
			}else{
				treeBuffer.append("<li><a menuid='" + menuId + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " </a>");
			}
			if(isLeaf==1){
			}else{
				buildMenuTree(menuId, treeBuffer, oList, menuIds);
			}
			treeBuffer.append("</li>");
		}
		
		treeBuffer.append("</ul>");
		
	}
	
	private List<Ops> getSonMenuListByPid(String pId, List<Ops> menuList) {
		List<Ops> sonMenuList = new ArrayList<Ops>();
		for (Ops menu : menuList) {
			if (menu != null) {
				String parentId = menu.getParentId().toString();// 父id
				if (parentId.equals(pId)) {
					sonMenuList.add(menu);
				}
			}
		}
		return sonMenuList;
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public void updateRoleOp(String roleId, String opIds) {
		List<Ops> opsList = operatorDao.listOpsByRoleId(null);
		List<RoleOp> recordList = new ArrayList<RoleOp>();
		if(opsList!=null && opsList.size()>0){
			for(Ops ops:opsList){
				if(opIds.indexOf(ops.getId())>-1){
					RoleOp ro = new RoleOp();
					ro.setId(StringUtil.getUUID());
					ro.setOpsId(ops.getId());
					ro.setRoleId(roleId);
					recordList.add(ro);
				}
			}
		}
		if(recordList!=null && recordList.size()>0){
			operatorDao.deleteRoleOp(roleId);
			operatorDao.addRoleOp(recordList);
		}
	}

}
