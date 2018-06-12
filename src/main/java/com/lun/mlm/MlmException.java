package com.lun.mlm;

/**
 * 运行时异常
 *  MlmException
 * @author policy
 * @createdDate:2016年8月16日下午11:29:35
 * @version:1.0
 */
public class MlmException extends RuntimeException {

	
	private static final long serialVersionUID = -1368846796586596848L;
	
	protected String statusCode;
	protected String message;
	
	public MlmException(String statusCode, String message) {
		super(message);
		
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public MlmException(String statusCode, String message, String jumpTo) {
		super(message);
		
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public MlmException(String statusCode, String message, Throwable throwable) {
		super(message, throwable);
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

}
