package com.lun.mlm.service;

import java.util.Map;

public interface WxPayService {

	/**
	 * 微信预支付
	 * @param openid
	 * @param orderId
	 * @param attach
	 * @param body
	 * @param deviceInfo
	 * @param totalFee
	 * @param tradeType
	 * @param createIp
	 * @return
	 */
	public Map<String, Object> unifiedPay(String openid, String orderId, String attach, String body, String deviceInfo,
			Integer totalFee, String tradeType,String createIp);
}
