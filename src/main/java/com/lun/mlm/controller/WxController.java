package com.lun.mlm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.utils.Context;
import com.lun.mlm.utils.SignUtil;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.MsgHandler;
import com.townmc.mp.TokenManager;

@Controller
public class WxController {
	private static final Log log = LogFactory.getLog(WxController.class);
	@Autowired MsgHandler msgHandler;
	@Autowired TokenManager tokenManager;
	@Autowired WechatDao wechatDao;
	/**
	 * 公共平台验证URL和TOKEN的接口
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="h5/notify", method=RequestMethod.GET)
	public void notify(String signature, String timestamp, String nonce, String echostr,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		// 签名验证
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		} else {
			out.print("error");
		}
		out.close();
	}
	
	@RequestMapping(value="h5/notify", method=RequestMethod.POST)
	public void notify(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException {
		log.debug("------notify-----");
		ServletInputStream in = null;
		Map<String, Object> re = null;
		WechatParam wp = null;
		boolean isAutoReply = false;
		try {
			in = request.getInputStream();
			DefaultWechat wechat = new DefaultWechat();
			wp = wechatDao.getWechat(Context.WX_APPID);
			wechat.setAppid(wp.getId());
			wechat.setSecret(wp.getSecret());
			wechat.setTokenManager(tokenManager);
			if(in != null){
				re = wechat.receiveMsg(in, msgHandler);
			}
			log.debug("msgType:"+(String)re.get("msgType"));
			
			if("text".equals((String)re.get("msgType"))) {
				isAutoReply = (Boolean)re.get("isAutoReply");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			response.setContentType("");
			
			PrintWriter out = null;
			try {
				StringBuffer outStr = new StringBuffer();
				out = response.getWriter();
				if(re != null) {
					String type = (String)re.get("msgType");
					// 如果已经进行过自定义回复，就不需要到多客服了
					if(!isAutoReply && 
							("text".equals(type) || "image".equals(type) || "voice".equals(type) || 
									"video".equals(type))) {
						outStr.append("<xml>");
						outStr.append("<ToUserName><![CDATA[").append(re.get("openid")).append("]]></ToUserName>");
						outStr.append("<FromUserName><![CDATA[").append(wp.getWechatId()).append("]]></FromUserName>");
						outStr.append("<CreateTime>").append(re.get("msgTime")).append("</CreateTime>");
						outStr.append("<MsgType><![CDATA[transfer_customer_service]]></MsgType>");
						outStr.append("</xml>");
					} else {
						outStr.append("");
					}
				} else {
					outStr.append("");
				}
				out.print(outStr.toString());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(out != null) out.close();
			}
		}
	}
}
