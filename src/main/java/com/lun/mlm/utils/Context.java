package com.lun.mlm.utils;

import java.util.Map;

/**
 * 配置文件
 *  Context
 * @author policy
 * @createdDate:2016年8月11日下午4:37:34
 * @version:1.0
 */
public class Context {

//	public static String TEST_CRM_IP_63;
//	public static String TEST_CRM_PORT_63;
//	public static String TEST_CRM_USER_63;
//	public static String TEST_CRM_PASSWD_63;
	
//	public static String Mail_MONITOR;
//	public static String SMS_MAIL_MONITOR;
//	public static String MERCHANT_ADD_MONITOR;
//	public static String AGENT_ADD_MONITOR;
	
	public static String WX_APPID;
	public static String WX_SECRET;
	
	public static String WX_WELCOME;
	public static String WX_TAKE_ADDR;
	public static String WX_TAKE_PHONE;

	static {
		Map<String, String> map = ResourceUtil.getResource("sys").getMap();
//		TEST_CRM_IP_63 = map.get("test.crm.ip");
//		TEST_CRM_PORT_63 = map.get("test.crm.port");
//		TEST_CRM_USER_63 = map.get("test.crm.user");
//		TEST_CRM_PASSWD_63 = map.get("test.crm.passwd");
		
//		Mail_MONITOR=map.get("mail.monitor");
//		SMS_MAIL_MONITOR=map.get("mail.sms.monitor");
//		MERCHANT_ADD_MONITOR=map.get("merchant.add.monitor");
//		AGENT_ADD_MONITOR=map.get("agent.add.monitor");
		
		WX_APPID=map.get("wx.appid");
		WX_SECRET=map.get("wx.secret");
		
		WX_WELCOME=map.get("wx.welcome");
		WX_TAKE_ADDR=map.get("wx.takeaddr");
		WX_TAKE_PHONE=map.get("wx.takephone");
	}
}
