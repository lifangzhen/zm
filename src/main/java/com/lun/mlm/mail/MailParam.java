package com.lun.mlm.mail;

/**
 * 邮件参数
 *  MailParam
 * @author policy
 * @createdDate:2016年7月15日下午7:00:05
 * @version:1.0
 */
public class MailParam {

	private String from;            //写件人
	private String to;					//收件人
	private String subject;         //主题
	private String content;        //Text内容
	
	public MailParam(){}
	
	public MailParam(String to, String subject, String content){
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
