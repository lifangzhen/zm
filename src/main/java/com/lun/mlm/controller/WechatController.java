package com.lun.mlm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.WechatMenu;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.service.WechatService;
import com.lun.mlm.utils.Base64Plus;
import com.lun.mlm.utils.Constants;
import com.lun.mlm.utils.Context;
import com.lun.mlm.utils.StringUtil;
import com.lun.mlm.web.annotations.TcResponseBody;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.TokenManager;
import com.townmc.mp.model.Menu;

@Controller
public class WechatController extends MlmController {
	@Autowired private TokenManager tokenManager;
	@Autowired private WechatDao wechatDao;
	@Autowired private WechatService wechatService;

	/**
	 * 获取菜单列表
	 * @return
	 */
	@RequestMapping(value = "/h5/getMenu/json")
	@TcResponseBody
	public Map<String, Object> getMenu() {
		String menujson = wechatService.getMenuJson();
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("menujson", menujson);
		return re;
	}
	
	/**
	 * 添加菜单
	 * @param fatherId
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/h5/addMenu/json")
	@TcResponseBody
	public Map<String, Object> addMenu() {
		String father = this.getParaMeter("father");
		String level = this.getParaMeter("level");
		String number = this.getParaMeter("number");
		String name = this.getParaMeter("name");
		String type = this.getParaMeter("type");
		String content = this.getParaMeter("content");
		String id = wechatService.addMenu(father, Integer.parseInt(level), Integer.parseInt(number), name, type, content);
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("id", id);
		return re;
	}
	
	/**
	 * 更新菜单
	 * @param menujson
	 * @return
	 */
	@RequestMapping(value = "/h5/updateMenu/json")
	@TcResponseBody
	public Map<String, Object> updateMenu() {
		String father = this.getParaMeter("father");
		String id = this.getParaMeter("id");
		String level = this.getParaMeter("level");
		String number = this.getParaMeter("number");
		String name = this.getParaMeter("name");
		String type = this.getParaMeter("type");
		String content = this.getParaMeter("content");
		wechatService.updateMenu(id, name, type, content);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}
	
	@RequestMapping(value = "/h5/deleteMenu/json")
	@TcResponseBody
	public Map<String, Object> deleteMenu() {
		String id = this.getParaMeter("id");
		String level = this.getParaMeter("level");
		wechatService.deleteMenu(id, Integer.parseInt(level));
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}
	
	@RequestMapping(value="/mlm/publishUi")
	public ModelAndView publishUi() {
		ModelAndView mav = new ModelAndView("/mlm/wx/publish");
		return mav;
	}
	
	@RequestMapping(value="/mlm/publish/json")
	@ResponseBody
	public Map<String, Object> publish() {
		WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
		DefaultWechat dw = new DefaultWechat();
		dw.setAppid(wp.getId());
		dw.setSecret(wp.getSecret());
		dw.setTokenManager(tokenManager);
		
		List<Menu> menus = new LinkedList<Menu>();
		List<WechatMenu> menulist = wechatDao.listMenu(WechatMenu.LEVEL_ONE, null);
		if(menulist!=null&&menulist.size()>0){
			if(menulist.size()>3) throw new MlmException("300", "一级菜单超过3个了");
			for(WechatMenu wm:menulist){
				List<Menu>subButton = new ArrayList<Menu>();
				List<WechatMenu> sublist = wechatDao.listMenu(WechatMenu.LEVEL_TWO, wm.getId());
				Menu menu = new Menu();
				menu.setName(wm.getName());
				if(sublist!=null&&sublist.size()>0){
					if(sublist.size()>5) throw new MlmException("300", "子菜单超过5个了");
					for(WechatMenu sub:sublist){
						Menu subMenu = new Menu();
						subMenu.setName(sub.getName());
						if(WechatMenu.TYPE_VIEW.equals(sub.getType())){
							subMenu.setType(sub.getType());
							subMenu.setUrl(sub.getContent());
						}else{
							subMenu.setType(WechatMenu.TYPE_CLICK);
							subMenu.setKey(sub.getId());
						}
						subButton.add(subMenu);
					}
				}else{
					if(WechatMenu.TYPE_VIEW.equals(wm.getType())){
						menu.setType(wm.getType());
						menu.setUrl(wm.getContent());
					}else{
						menu.setType(WechatMenu.TYPE_CLICK);
						menu.setKey(wm.getId());
					}
				}
				menu.setSubButton(subButton);
				menus.add(menu);
			}
		}
		
		dw.createMenu(menus);
		return success();
	}
	
	@RequestMapping(value="/mlm/testUi", method=RequestMethod.GET)
	public ModelAndView test() throws IOException {
		ModelAndView mav = new ModelAndView("/mlm/test");
		return mav;
	}
}
