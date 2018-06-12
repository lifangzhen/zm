package com.lun.mlm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class MsgTemplate implements Serializable {
	
	private static final long serialVersionUID = 6272770362231744540L;
	
	public String id; 
	public Integer version;
	public Date createTime; //创建时间
	public String msgType; //text文本，image图片，video视频，audio音频，single单条图文，multi多条图文
	public String merchantId; //
	public String title; //标题
	public String summary; //摘要
	public byte[] content; //内容
	public String thumbUrl; //图片、音频、视频缩略图
	public String url; //图片、音频、视频URL
	public BigDecimal msgSize; //字数，图片、音频、视频大小
	public BigDecimal msgDuration; //音频、视频长度
	public BigDecimal msgWidth; //图片、视频宽度
	public BigDecimal msgHeight; //图片、视频高度
	public String multiMsgId;
	public Integer position; //多条图文对应的小单条图文的排列顺序
	public Integer isTemplate; //是否是模版1/0
	public Integer status; //是否被删除1/0
	public String mid;
	public Integer count;
	public String contentUrl; //单、多条图文消息正文的链接地址
	public Date updateTime; //修改时间
	public List<MsgTemplate> childList; //多条图文对应的单条图文列表
	
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
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public List<MsgTemplate> getChildList() {
		return childList;
	}
	public Integer getIsTemplate() {
		return isTemplate;
	}
	public void setIsTemplate(Integer isTemplate) {
		this.isTemplate = isTemplate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setChildList(List<MsgTemplate> childList) {
		this.childList = childList;
	}
	public BigDecimal getMsgSize() {
		return msgSize;
	}
	public void setMsgSize(BigDecimal msgSize) {
		this.msgSize = msgSize;
	}
	public BigDecimal getMsgDuration() {
		return msgDuration;
	}
	public void setMsgDuration(BigDecimal msgDuration) {
		this.msgDuration = msgDuration;
	}
	public BigDecimal getMsgWidth() {
		return msgWidth;
	}
	public void setMsgWidth(BigDecimal msgWidth) {
		this.msgWidth = msgWidth;
	}
	public BigDecimal getMsgHeight() {
		return msgHeight;
	}
	public void setMsgHeight(BigDecimal msgHeight) {
		this.msgHeight = msgHeight;
	}
	public String getMultiMsgId() {
		return multiMsgId;
	}
	public void setMultiMsgId(String multiMsgId) {
		this.multiMsgId = multiMsgId;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int convertMsgType() {
		if ("text".equals(msgType)) {
			return 1;
		} else if("image".equals(msgType)) {
			return 2;
		} else if("audio".equals(msgType)) {
			return 3;
		} else if("video".equals(msgType)) {
			return 4;
		} else if("single".equals(msgType)) {
			return 6;
		} else if("multi".equals(msgType)) {
			return 7;
		} else {
			return 0;
		}
	}
}
