package com.lun.mlm.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 邮件发送类
 *  MailSend
 * @author policy
 * @createdDate:2016年7月15日下午7:09:12
 * @version:1.0
 */
@Component("mailSend")
public class MailSend {
//	@Autowired private JavaMailSender javaMailSender;
//	@Autowired private SimpleMailMessage simpleMailMessage;
//	@Autowired private ThreadPoolTaskExecutor threadPool;
//
//	public void sendMail(final MailParam mailParam){
//		threadPool.execute(new Runnable() {
//			public void run() {
//				try{
//					simpleMailMessage.setFrom(simpleMailMessage.getFrom());
//					simpleMailMessage.setTo(mailParam.getTo());
//					simpleMailMessage.setSubject(mailParam.getSubject());
//					simpleMailMessage.setText(mailParam.getContent());
//					javaMailSender.send(simpleMailMessage);
//				}catch(MailException e){
//					throw e;
//				}
//			}
//		});
//	}
}
