package com.lun.mlm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lun.mlm.service.MailService;
import com.lun.mlm.utils.Constants;
import com.lun.mlm.utils.ConstantsSession;
import com.lun.mlm.utils.Context;
import com.lun.mlm.utils.DateUtil;


public class MlmController extends BaseController {

	@Autowired MailService mailService;
	
	public String getLoginName(){
		return (String) this.getSession(ConstantsSession.LOGIN_NAME);
	}
	
	protected void operatorMerchantMail(String content){
//		mailService.send(content, Constants.MERCHANT_MAIL_SUBJECT, Context.MERCHANT_ADD_MONITOR);
	}
	
}
