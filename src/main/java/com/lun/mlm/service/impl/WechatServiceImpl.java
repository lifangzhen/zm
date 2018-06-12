package com.lun.mlm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.lun.mlm.MlmException;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.MsgTemplate;
import com.lun.mlm.model.WechatMenu;
import com.lun.mlm.service.WechatService;
import com.townmc.mp.json.JSONArray;
import com.townmc.mp.json.JSONObject;
import com.wxopen.util.StringUtil;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {
	@Autowired WechatDao wechatDao;
	@Override
	public String addMenu(String father, Integer level, Integer number, String name,
			String type, String content) {
		if(WechatMenu.TYPE_VIEW.equals(type)&&!content.startsWith("http")) content="http://"+content;
		WechatMenu menu = new WechatMenu();
		String id = StringUtil.getUUID();
		menu.setId(id);
		menu.setLevel(level);
		menu.setNumber(number);
		menu.setName(name);
		menu.setFather(father);
		menu.setType(type);
		menu.setContent(content);
		menu.setStatus(WechatMenu.STATUS_NORMAL);
		wechatDao.addMenu(menu);
		return id;
	}

	@Override
	public void updateMenu(String id, String name, String type, String content) {
		if(WechatMenu.TYPE_VIEW.equals(type)&&!content.startsWith("http")) content="http://"+content;
		WechatMenu menu = new WechatMenu();
		menu.setId(id);
		menu.setName(name);
		menu.setType(type);
		menu.setContent(content);
		wechatDao.updateMenu(menu);
	}

	@Override
	public void deleteMenu(String id, Integer level) {
		wechatDao.deleteMenu(id);
	}

	@Override
	public String getMenuJson() {
		List<WechatMenu>list = wechatDao.listMenu(WechatMenu.LEVEL_ONE, null);
		String json = "";
		if(list!=null&&0<list.size()&&list.size()<4){
			List<Map<String, Object>>mapList = this.menuToMap(list);
			for(Map<String, Object> map:mapList){
				List<WechatMenu>childList = wechatDao.listMenu(WechatMenu.LEVEL_TWO, map.get("id").toString());
				if(childList!=null && 0<childList.size()){
					if(childList.size()>5) throw new MlmException("300", "菜单个数不能超过5个");
					List<Map<String, Object>> childMap = this.menuToMap(childList);
					map.put("sub_button_list", childMap);
				}
			}
			JSONArray jsonArr = new JSONArray(mapList);
			json = jsonArr.toString();
		}else{
			throw new MlmException("300", "菜单参数有误，联系开发者");
		}
		return json;
	}
	
	public List<Map<String, Object>> menuToMap(List<WechatMenu> list){
		List<Map<String, Object>> relist = new ArrayList<Map<String, Object>>();
		if(list!=null&&0<list.size()){
			for(WechatMenu wm:list){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", wm.getId());
				map.put("father", wm.getFather());
				map.put("name", wm.getName());
				map.put("type", wm.getType());
				map.put("level", wm.getLevel());
				map.put("number", wm.getNumber());
				map.put("content", wm.getContent());
				relist.add(map);
			}
		}
		return relist;
	}

}
