package com.lun.mlm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.model.Member;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.service.WxService;
import com.lun.mlm.utils.Context;
import com.townmc.mp.MsgHandler;

@Service("msgHandler")
public class MsgHandlerImpl implements MsgHandler {
	private static final Log log = LogFactory.getLog(MsgHandlerImpl.class);
	@Autowired WxService wxMlmService;
	@Autowired MemberService memberService;
	@Autowired MemberDao memberDao;

	/**
	 * 文本消息
	 * @param openid
	 * @param msgTime
	 * @param content
	 * @return 
	 */
	public boolean textMessage(String openid, int msgTime, String content,
			String msgId) {
		log.debug("textMessage openid:"+openid);
//		return apiWechatReplyService.textReply(openid, msgTime, content, msgId);
		return true;
	}

	/**
	 * 图片消息
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param picUrl 图片链接
	 * @param mediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
	 * @param msgId 消息id，64位整型
	 */
	public void imageMessage(String openid, int msgTime, String picUrl, String mediaId, String msgId) {
		log.debug("recive image from openid:" + openid + ". picUrl:" + picUrl);

	}

	/**
	 * 语音消息
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param mediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
	 * @param format 语音格式，如amr，speex等
	 * @param msgId 消息id，64位整型
	 * @param recognition 语音识别结果（开通语音识别功能的前提下）
	 */
	public void voiceMessage(String openid, int msgTime, String mediaId, String format, String msgId, String recognition) {
		log.debug("recive voice from openid:" + openid + ". mediaId:" + mediaId + ". format:" + format + ". msgId:" + msgId + ". recognition:" + recognition);

	}

	/**
	 * 视频消息
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param mediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	 * @param thumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	 * @param msgId 消息id，64位整型
	 */
	public void videoMessage(String openid, int msgTime, String mediaId, String thumbMediaId, String msgId) {
		log.debug("recive video from openid:" + openid + ". mediaId:" + mediaId);

	}

	/**
	 * 地理位置消息
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param longitude 地理位置经度
	 * @param latitude 地理位置维度
	 * @param scale 地图缩放大小
	 * @param label 地理位置信息
	 * @param msgId 消息id，64位整型
	 */
	public void locationMessage(String openid, int msgTime, double longitude, double latitude, int scale, String label, String msgId) {
		log.debug("recive location from openid:" + openid + ". label:" + label);

	}

	/**
	 * 链接消息
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param title 消息标题
	 * @param description 消息描述
	 * @param url 消息链接
	 * @param msgId 消息id，64位整型
	 */
	public void linkMessage(String openid, int msgTime, String title, String description, String url, String msgId) {
		log.debug("recive link from openid:" + openid + ". label:" + url);

	}

	/**
	 * 关注事件
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 */
	public void subscribeEvent(String openid, int msgTime) {
		log.debug("subscribeEvent openid:"+openid);
		memberService.getMember(openid, true);
		Map<String, Object> map = new HashMap<String, Object>();
		String text = Context.WX_WELCOME;
		map.put("openid", openid);
		map.put("text", text);
		//TODO 关注
	}

	/**
	 * 扫描带参数二维码事件（用户未关注时，进行关注后的事件推送）
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param eventKey 事件KEY值，qrscene_为前缀，后面为二维码的参数值。如qrscene_123123
	 * @param ticket 二维码的ticket，可用来换取二维码图片
	 */
	public void subscribeQrEvent(String openid, int msgTime, String eventKey, String ticket) {
		log.debug("subscribeQrEvent openid:"+openid);
		memberService.getMember(openid, true);
		Map<String, Object> map = new HashMap<String, Object>();
		String text = Context.WX_WELCOME;
		map.put("openid", openid);
		map.put("text", text);
		//扫描二维码关注
	}

	/**
	 * 取消关注事件
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 */
	public void unsubscribeEvent(String openid, int msgTime) {
		log.debug("unsubscribeEvent openid:"+openid);
		Member m = new Member();
		m.setId(openid);
		m.setSubscribe(Member.SUBSCRIBE_NO);
		memberDao.updateMember(m);
	}

	/**
	 * 扫描带参数二维码事件（用户已关注时的事件推送）
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param eventKey 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 * @param ticket 二维码的ticket，可用来换取二维码图片
	 */
	public void scanEvent(String openid, int msgTime, String eventKey, String ticket) {
		log.debug("scanEvent openid:"+openid);
	}

	/**
	 * 上报地理位置事件
	 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，<br />
	 * 或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。<br />
	 * 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param longitude 地理位置经度
	 * @param latitude 地理位置纬度
	 * @param precision 地理位置精度
	 */
	public void locationEvent(String openid, int msgTime, double longitude, double latitude, double precision) {
		log.debug("recive locationEvent from openid:" + openid + ". precision:" + precision);

	}

	/**
	 * 自定义菜单事件
	 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，请注意，点击菜单弹出子菜单，不会产生上报
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param eventKey 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	public void clickEvent(String openid, int msgTime, String eventKey) {
		log.debug("recive clickEvent from openid:" + openid + ". eventKey:" + eventKey);
		wxMlmService.sendNotifyMsg(openid, eventKey);
	}

	/**
	 * 点击菜单跳转链接时的事件推送
	 * @param openid 发送方帐号
	 * @param msgTime 消息创建时间
	 * @param eventKey 事件KEY值，设置的跳转URL
	 */
	public void viewEvent(String openid, int msgTime, String eventKey) {
		log.debug("recive viewEvent from openid:" + openid + ". eventKey:" + eventKey);

	}

}
