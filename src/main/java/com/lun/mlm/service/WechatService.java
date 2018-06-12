package com.lun.mlm.service;

public interface WechatService {

	public String addMenu(String father, Integer level, Integer number, String name, String type, String content);
	
	public void updateMenu(String id, String name, String type, String content);
	
	public void deleteMenu(String id, Integer level);
	
	public String getMenuJson();
}
