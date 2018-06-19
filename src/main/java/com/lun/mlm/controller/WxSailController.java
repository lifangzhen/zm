package com.lun.mlm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lun.mlm.dao.MsgDao;
import com.lun.mlm.model.*;
import com.lun.mlm.utils.ApiResponse;
import com.lun.mlm.utils.IDGenerator;
import com.townmc.mp.model.MpUser;
import com.townmc.mp.model.Token;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.service.OrderService;
import com.lun.mlm.utils.Context;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.MpException;
import com.townmc.mp.TokenManager;
import com.wxopen.util.StringUtil;


@Controller
public class WxSailController extends BaseController  {
	private Logger logger = LoggerFactory.getLogger(WxSailController.class);
	@Autowired TokenManager tokenManager;
	@Autowired MemberService memberService;
	@Autowired MemberDao memberDao;
	@Autowired WechatDao wechatDao;
	@Autowired OrderService orderService;
	@Autowired
	MsgDao msgDao;

	@RequestMapping(value = "h5/redirect")
	public void redirect(String storeId,String tableId, String uid, HttpServletResponse response) {
		try {
			String redirect = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
					"appid=wx85b3700a9516c64f&redirect_uri=http%3A%2F%2Fzm.herison.com.cn%2Fh5%2Findex" +
					"%3FstoreId%3D"+storeId;
			if (StringUtil.isNotBlank(tableId)){
				redirect = redirect + "%26tableId%3D"+tableId;
			}
			if (StringUtil.isNotBlank(uid)){
				redirect = redirect + "%26uid%3D"+uid;
			}
			redirect = redirect+"&response_type=code&scope=snsapi_userinfo&state=null#wechat_redirect";

			System.out.println("redirect:++++++++++++++++"+redirect+"+++++++++++++++++++++");
			response.sendRedirect(redirect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "h5/index/test")
	public ModelAndView test() {
		ModelAndView mav = new ModelAndView("wx/index");
		mav.addObject("uid", "abc001");
		mav.addObject("storeId", "001");
		mav.addObject("tableId", "001");
		return mav;
	}


	@RequestMapping(value = "h5/index")
	public ModelAndView sailindex(String code, String state, String storeId, String tableId, String uid, HttpServletResponse response) {
		logger.info("index----storeId:"+storeId+"+++tableId:"+tableId+"++++uid:"+uid);
		if(StringUtil.isBlank(code)) throw new MlmException("300", "code获取失败");
		ModelAndView mav = new ModelAndView("wx/index");
		try{
			DefaultWechat wechat = new DefaultWechat();
			wechat.setAppid(Context.WX_APPID);
			wechat.setSecret(Context.WX_SECRET);
			wechat.setTokenManager(tokenManager);
			String openid = wechat.getOpenidByCode(code);
			MpUser mpUser = wechat.getUser(openid);
			ZmUser zmUser = msgDao.getUserByOpenId(openid);
			String id = "";
			if (zmUser==null){
				id = IDGenerator.nextId();
				zmUser = new ZmUser();
				zmUser.setId(id);
				zmUser.setOpen_id(openid);
				zmUser.setHead_img(mpUser.getHeadimgurl());
				zmUser.setName(mpUser.getNickname());
				zmUser.setArea(mpUser.getCountry()+mpUser.getProvince()+mpUser.getCity());
				if (mpUser.getSex()!=null){
					zmUser.setSex(mpUser.getSex()==1?"male":"female");
				}else{
					zmUser.setSex("unknown");
				}
				msgDao.addZmUser(zmUser);

				if (StringUtil.isNotBlank(uid)){
					logger.info("++++id:"+id+"+++++uid:"+uid);
					ZmFriend zmFriend = msgDao.getZmFriend(id, uid);
					if (zmFriend==null){
						logger.info("+++++++zmfriend is null+++++");
						ZmFriend zmFriend1 = new ZmFriend();
						zmFriend1.setId(IDGenerator.nextId());
						zmFriend1.setUser_id(id);
						zmFriend1.setFriend_user_id(uid);
						zmFriend1.setStatus(0);
						msgDao.addZmFriend(zmFriend1);
						ZmFriend zmFriend2 = new ZmFriend();
						zmFriend2.setId(IDGenerator.nextId());
						zmFriend2.setUser_id(uid);
						zmFriend2.setFriend_user_id(id);
						zmFriend2.setStatus(0);
						msgDao.addZmFriend(zmFriend2);
					}

				}
			}else{
				id = zmUser.getId();
			}

			mav.addObject("uid", id);
			mav.addObject("storeId", storeId);
			mav.addObject("tableId", tableId);
		}catch(MpException e){
			if(e.getMessage().contains("40029")){
				return mav;
			}
		}

		return mav;
	}
	@RequestMapping(value = "h5/config")
	@ResponseBody
	public ApiResponse getConfig(String pageUrl) {
		DefaultWechat wechat = new DefaultWechat();
		wechat.setAppid(Context.WX_APPID);
		wechat.setSecret(Context.WX_SECRET);
		wechat.setTokenManager(tokenManager);
		Map<String, Object> re = wechat.getJsConfig(Context.WX_APPID, pageUrl);
		return ApiResponse.success(re);
	}

	@RequestMapping(value = "h5/msg/{storeId}/{tableId}/{page}")
	@ResponseBody
	public ApiResponse tablemsg(@PathVariable("storeId") String storeId,
							@PathVariable("tableId") String tableId,
							@PathVariable("page") Integer page) {
		List<ZmMsg> list = msgDao.listByStoreIdAndTableId(storeId, tableId, page);
		return ApiResponse.success(list);
	}

	@RequestMapping(value = "h5/msg/{storeId}/{page}")
	@ResponseBody
	public ApiResponse storemsg(@PathVariable("storeId") String storeId,
							@PathVariable("page") Integer page) {
		List<ZmMsg> list = msgDao.listByStoreIdAndTableId(storeId, null, page);
		return ApiResponse.success(list);
	}

	@RequestMapping(value = "h5/banner/{storeId}")
	@ResponseBody
	public ApiResponse listBanner(@PathVariable("storeId") String storeId) {
		List<ZmBanner> list = msgDao.listByStoreId(storeId);
		return ApiResponse.success(list);
	}


	
	@RequestMapping(value = "h5/h5index")
	public ModelAndView h5index() {
		WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
		ModelAndView mav = new ModelAndView("wx/index1");
		mav.addObject("minPay", wp.getMinPay());
		return mav;
	}
	
	@RequestMapping(value = "h5/h5order")
	public ModelAndView h5order() {
		ModelAndView mav = new ModelAndView("/wx/order");
		return mav;
	}
	
	@RequestMapping(value = "h5/myorderUi")
	public ModelAndView myorderUi() {
		ModelAndView mav = new ModelAndView("/wx/myOrder");
		return mav;
	}
	
	@RequestMapping(value = "h5/myaccountUi")
	public ModelAndView myaccountUi() {
		ModelAndView mav = new ModelAndView("/wx/myAccount");
		return mav;
	}
	
	@RequestMapping(value="h5/feedback", method=RequestMethod.POST)
	public void payfeedback(HttpServletRequest request,HttpServletResponse response) throws DocumentException {
		ServletInputStream input = null;
		try {
			input = request.getInputStream();
			SAXReader reader = new SAXReader();
			reader.setEncoding("utf-8");
            Document doc = reader.read(input);
            Element root = doc.getRootElement();
            String return_code = root.elementText("return_code");
            if(!"SUCCESS".equals(return_code)){
                throw new MlmException("error", "微信支付失败");
            }else{
                String appid = root.elementText("appid");
                String mch_id = root.elementText("mch_id");
                String orderid = root.elementText("out_trade_no"); //即订单ID
                String cash_fee = root.elementText("cash_fee");
                orderService.orderFeedback(appid, mch_id, orderid, cash_fee);
            }
		} catch(MlmException me) {
			//TODO 处理支付失败的订单
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            response.setContentType("");
            PrintWriter out = null;
            try {
                StringBuffer outStr = new StringBuffer();
                out = response.getWriter();
                outStr.append("<xml>");
                outStr.append("<return_code><![CDATA[SUCCESS]]></return_code>");
                outStr.append("<return_msg><![CDATA[OK]]></return_msg>");
                outStr.append("</xml>");
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
