package com.lun.mlm.web.annotations;

/**
 * 返回对象
 *  TcReturnValue
 * @author policy
 * @createdDate:2016年7月21日下午11:55:35
 * @version:1.0
 */
public class TcReturnValue {

	private static final long serialVersionUID = 8100825604764704382L;

	// 返回数据
	private Object result;

	// 状态码
	private String statusCode;

	// 消息
	private String message;
	
	private String navTabId;
	
	private String callbackType;
	
	private String forwardUrl;
	
	public TcReturnValue(Object returnValue) {
		this.result = returnValue;
		this.statusCode = "200";
		this.message = "操作成功";
//		this.callbackType = "closeCurrent";
		
//		this.callbackType = "forward";
//		this.navTabId = "listAgent";
//		this.forwardUrl = "";
	}
	
	public TcReturnValue(Object returnValue, String statusCode, String message) {
		this.result = returnValue;
		this.statusCode = statusCode;
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	
}
