package com.lun.mlm.model;

import java.io.Serializable;
import java.util.Date;

public class WechatParam implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Integer MIN_PAY_DEFAULT = 10;

	private String id;                                 //appid
	private Integer version;
	private Date createTime;
	private String secret;
	private String wechatId;
	private String mchId;
	private String apiKey;
	private String notifyUrl;
    private String accessToken;
    private Date expireTime;
    private Integer minPay;
    private String notifyOpenid;
    private Date updateTime;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getMinPay() {
		return minPay;
	}
	public void setMinPay(Integer minPay) {
		this.minPay = minPay;
	}
	public String getNotifyOpenid() {
		return notifyOpenid;
	}
	public void setNotifyOpenid(String notifyOpenid) {
		this.notifyOpenid = notifyOpenid;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}