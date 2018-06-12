package com.lun.mlm.service;

public interface MailService {

	public void send(String content, String subject, String mailToStr);
}
