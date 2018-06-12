package com.lun.mlm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lun.mlm.model.ZmMsg;
import com.lun.mlm.utils.ApiResponse;
import com.townmc.mp.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;
import com.lun.mlm.dao.MemberDao;
import com.lun.mlm.dao.OrderDao;
import com.lun.mlm.dao.WechatDao;
import com.lun.mlm.model.Member;
import com.lun.mlm.model.WechatParam;
import com.lun.mlm.service.MemberService;
import com.lun.mlm.service.OrderService;
import com.lun.mlm.utils.Context;
import com.lun.mlm.web.annotations.TcResponseBody;
import com.townmc.mp.DefaultWechat;
import com.townmc.mp.MpException;
import com.townmc.mp.TokenManager;
import com.wxopen.util.StringUtil;


@Controller
public class WxSailController extends BaseController  {
	@Autowired TokenManager tokenManager;
	@Autowired MemberService memberService;
	@Autowired MemberDao memberDao;
	@Autowired WechatDao wechatDao;
	@Autowired OrderService orderService;
	@RequestMapping(value = "h5/redirect")
	public void redirect(String msgId, HttpServletResponse response) {
		try {
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx85b3700a9516c64f&redirect_uri=http%3A%2F%2Fzm.herison.com.cn%2Fh5%2Findex%3Fmsg%3D"+msgId+"&response_type=code&scope=snsapi_base&state=null#wechat_redirect");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "h5/index")
	public ModelAndView sailindex(String code, String state, String msgId, HttpServletResponse response) {
		if(StringUtil.isBlank(code)) throw new MlmException("300", "code获取失败");
		ModelAndView mav = new ModelAndView("/wx/index");
		System.out.println("indexUrl:"+request.getServerName());
		try{
			DefaultWechat wechat = new DefaultWechat();
			wechat.setAppid(Context.WX_APPID);
			wechat.setSecret(Context.WX_SECRET);
			wechat.setTokenManager(tokenManager);
			String openid = wechat.getOpenidByCode(code);
			WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
			mav.addObject("openid", openid);
			mav.addObject("msgId", msgId);
			mav.addObject("minPay", wp.getMinPay());
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
//		String pageUrl = "http://"+request.getServerName()+"/h5/index";
		System.out.println("pageUrl:"+pageUrl);
		DefaultWechat wechat = new DefaultWechat();
		wechat.setAppid(Context.WX_APPID);
		wechat.setSecret(Context.WX_SECRET);
		wechat.setTokenManager(tokenManager);
		Map<String, Object> re = wechat.getJsConfig(Context.WX_APPID, pageUrl);
		return ApiResponse.success(re);
	}

	@RequestMapping(value = "h5/msg/{storeId}/{tableId}")
	@ResponseBody
	public ApiResponse test(@PathVariable("storeId") String storeId,
							@PathVariable("tableId") String tableId) {
		System.out.println(storeId+":"+tableId);
		List<ZmMsg> list = new ArrayList<ZmMsg>();
		for (int i=0;i<10;i++){
			ZmMsg zmMsg = new ZmMsg();
			zmMsg.setId("000"+i);
			zmMsg.setUser_id("000"+i);
			zmMsg.setTable_id("000"+i);
			zmMsg.setStore_id("000"+i);
			zmMsg.setDetail("我是00"+i+",这是我的留言，哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
			zmMsg.setShare_count(i*i);
			zmMsg.setPraize_count(i);
			zmMsg.setComment_count(i+i);
			zmMsg.setStatus(0);
			list.add(zmMsg);
		}
		return ApiResponse.success(list);
	}
	
	@RequestMapping(value = "h5/h5index")
	public ModelAndView h5index() {
		WechatParam wp = wechatDao.getWechat(Context.WX_APPID);
		ModelAndView mav = new ModelAndView("/wx/index");
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
