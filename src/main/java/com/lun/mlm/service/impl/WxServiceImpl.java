package com.lun.mlm.service.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lun.mlm.MlmException;
import com.lun.mlm.controller.ApiController;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.MsgTemplate;
import com.lun.mlm.model.WechatMenu;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.service.WxService;
import com.lun.mlm.utils.Context;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.MpException;
import com.townmc.mp.TokenManager;
import com.townmc.mp.Wechat;

@Service("wxNotifyService")
public class WxServiceImpl implements WxService {
	private static final Log log = LogFactory.getLog(WxServiceImpl.class);
	@Autowired MemberDao memberDao;
	@Autowired WechatDao wechatDao;
	@Autowired TokenManager tokenManager;
	@Override
	public void subscribe(String openid, String eventKey, int msgTime) {
		
	}

	@Override
	public void unsubscribe(String openid, int msgTime) {
		
	}

	@Override
	public void sendNotifyMsg(String openid, String msgId) {
		DefaultWechat wechat = new DefaultWechat();
		WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
		wechat.setAppid(wp.getId());
		wechat.setSecret(wp.getSecret());
		wechat.setTokenManager(tokenManager);
		
		WechatMenu menu = wechatDao.getMenu(msgId);
		if(menu == null) throw new MlmException("300", "消息不存在, msgId:"+msgId);
		
		String content = "";
		try{
			if(WechatMenu.TYPE_TEXT.equals(menu.getType())){
				content = new String(menu.getContent());
				wechat.sendTextMsg(openid, content);
			}else if(WechatMenu.TYPE_IMAGE.equals(menu.getType())){
				String picUrl = menu.getContent();
				File file = new File("/home/mediaPic");
				 if(!file.exists()){
					file.mkdirs();
				 }
				 String mediaFilePath = "/home/mediaPic"+ "/" + picUrl.substring(picUrl.lastIndexOf("/") + 1);
				 try {
						URL u = new URL(picUrl);
						HttpURLConnection uc = (HttpURLConnection) u.openConnection();
						uc.setConnectTimeout(30 * 1000); //设置超时30秒
						uc.setReadTimeout(30 * 1000);
						uc.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
						uc.setRequestProperty("Accept","*/*");
						uc.setRequestMethod("GET");
						uc.setUseCaches(false);
						uc.setRequestProperty("Content-Type", "application/octet-stream");
						uc.addRequestProperty("Accept-Encoding", "gzip");
						uc.setDoInput(true);
						uc.connect();
						if (uc.getResponseCode() == HttpURLConnection.HTTP_OK){
							DataInputStream in = new DataInputStream(uc.getInputStream());
							DataOutputStream out = new DataOutputStream(new FileOutputStream(mediaFilePath));
							byte[] buffer = new byte[in.available()];
							int count = 0;
							while ((count = in.read(buffer)) > 0) {
								out.write(buffer, 0, count);
							}
							out.close();
							in.close();
							uc.disconnect();
						}
						file = new File(mediaFilePath);
						if (!file.exists() || file.length() <= 0) System.out.println("msg file null");;
					} catch (Exception e) {
						e.printStackTrace();
					}
				String mediaId = wechat.uploadPic(WechatMenu.TYPE_IMAGE, file);
				file.delete();
				wechat.sendMediaMsg(openid, Wechat.MsgType.image, mediaId);
			}else{
				log.debug("sendNotifyMsg of other type, msgId:"+msgId);
			}
		}catch(MpException me){
			log.error("mp error:"+me.getMessage());
		}catch (Exception e) {
			log.error("sendNotifyMsg catch an error:"+e.getMessage());
		}
		
	}
	
}
