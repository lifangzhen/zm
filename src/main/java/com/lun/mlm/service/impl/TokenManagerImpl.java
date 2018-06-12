package com.lun.mlm.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.WechatParam;
import com.townmc.mp.TokenManager;
import com.townmc.mp.model.Token;
import com.wxopen.util.StringUtil;

/**
 * token存储
 *  TokenManagerImpl
 * @author policy
 * @createdDate:2016年9月6日下午12:02:53
 * @version:1.0
 */
@Service("tokenManager")
public class TokenManagerImpl  implements TokenManager {
	private static final Log log = LogFactory.getLog(TokenManagerImpl.class);
	
	@Autowired WechatDao wechatDao;
	
	// 存储token
	public void toStorage(Token token) {
		WechatParam existToken = wechatDao.getWechat(token.getAppid());
		WechatParam wp = new WechatParam();
		wp.setId(token.getAppid());
		wp.setAccessToken(token.getAccessToken());
		wp.setExpireTime(token.getExpireTime());
		wp.setUpdateTime(token.getUpdateTime());
		if (existToken == null) {
			wechatDao.insertParam(wp);
		} else {
			wechatDao.updateParam(wp);
		}
	}
	
	// 查询token
	public Token get(String appid) {
		WechatParam wp = wechatDao.getWechat(appid);
		Token token = null;
		if (wp != null && StringUtil.isNotBlank(wp.getAccessToken())
				&& wp.getExpireTime().getTime() > System.currentTimeMillis()) {
			token = new Token();
			token.setAppid(wp.getId());
			token.setAccessToken(wp.getAccessToken());
			token.setExpireTime(wp.getExpireTime());
			token.setUpdateTime(wp.getUpdateTime());
		}
		return token;
	}
	
}
