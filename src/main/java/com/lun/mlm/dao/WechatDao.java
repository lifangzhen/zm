package com.lun.mlm.dao;

import java.util.List;

import com.lun.mlm.model.MsgTemplate;
import com.lun.mlm.model.WechatMenu;
import com.lun.mlm.model.WechatParam;

public interface WechatDao {

	public WechatParam getWechat(String id);
	
	public void insertParam(WechatParam wp);
	
	public void updateParam(WechatParam wp);
	
	public WechatMenu getMenu(String id);
	
	public void addMenu(WechatMenu menu);
	
	public void updateMenu(WechatMenu menu);
	
	public void deleteMenu(String id);
	
	public List<WechatMenu> listMenu(Integer level, String father);
}
