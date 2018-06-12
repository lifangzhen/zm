package com.lun.mlm.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.service.WxPayService;
import com.lun.mlm.utils.Context;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.TokenManager;
import com.townmc.mp.Wechat;
import com.townmc.mp.model.MpUser;
import com.townmc.mp.model.Unifiedorder;

@Service("wxPayService")
public class WxPayServiceImpl implements WxPayService {
	private static final Log log = LogFactory.getLog(WxPayServiceImpl.class);
	@Autowired WechatDao wechatDao;
	@Autowired TokenManager tokenManager;
	
	@Override
	public Map<String, Object> unifiedPay(String openid, String orderId,
			String attach, String body, String deviceInfo, Integer totalFee, 
			String tradeType, String createIp) {
		DefaultWechat wechat = new DefaultWechat();
		wechat.setAppid(Context.WX_APPID);
		wechat.setSecret(Context.WX_SECRET);
		wechat.setTokenManager(tokenManager);
		if(tradeType==null || "".equals(tradeType)) tradeType ="JSAPI";
		if(deviceInfo==null || "".equals(deviceInfo)) deviceInfo ="WEB";
		MpUser mpUser = wechat.getUser(openid);
		if (mpUser.getSubscribe() != 1) {
			throw new MlmException("300", "请先关注该公众号!");
		}
		WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(Context.WX_APPID);
		unifiedorder.setAttach(attach);
		unifiedorder.setBody(body == null ? "" : body.trim());
		unifiedorder.setDevice_info(deviceInfo);
		unifiedorder.setMch_id(wp.getMchId());
		unifiedorder.setNotify_url(wp.getNotifyUrl());
		unifiedorder.setOpenid(openid);
		unifiedorder.setOut_trade_no(orderId);
		unifiedorder.setSpbill_create_ip(createIp);
		unifiedorder.setTotal_fee(totalFee);
		unifiedorder.setTrade_type(tradeType);
		
		Map<String, Object> map = wechat.unifiedOrder(unifiedorder, wp.getApiKey());
		log.info("prepay_id:"+map.get("prepay_id"));
		map.put("out_trade_no", orderId);
		return map;
	}

}
