package com.lun.mlm.service;

public interface WxService {
	
	//关注
	public void subscribe(String openid, String eventKey, int msgTime);

	//取消关注
	public void unsubscribe(String openid, int msgTime);
	
	//响应消息
	public void sendNotifyMsg(String openid, String msgId);
	
}
