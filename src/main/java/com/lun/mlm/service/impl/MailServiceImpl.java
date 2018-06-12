package com.lun.mlm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lun.mlm.mail.MailParam;
import com.lun.mlm.mail.MailSend;
import com.lun.mlm.service.MailService;
import com.lun.mlm.utils.StringUtil;


@Service("mailService")
public class MailServiceImpl implements MailService {
//	@Autowired MailSend mailSend;
	
	@Override
	public void send(String content, String subject, String mailToStr) {
//		if(StringUtil.isBlank(mailToStr)) return;
//		String[] mailTo = mailToStr.split(",");
//		if(mailTo.length==0){
//			mailTo[0] = mailToStr;
//		}
//		for(int i=0;i<mailTo.length;i++){
//			MailParam mailParam = new MailParam();
//			mailParam.setContent(content);
//			mailParam.setSubject(subject);
//			mailParam.setTo(mailTo[i]);
//			mailSend.sendMail(mailParam);
//		}
	}

}
