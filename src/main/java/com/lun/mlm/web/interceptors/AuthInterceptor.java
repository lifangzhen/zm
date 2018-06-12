package com.lun.mlm.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 权限拦截
 *  AuthInterceptor
 * @author policy
 * @createdDate:2016年8月16日下午11:34:46
 * @version:1.0
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static final Log log = LogFactory.getLog(AuthInterceptor.class);
	@Value("${contextPath}") private String contextPath;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(request.getServletPath().equals("/mlm/dologin/json")) {
			return true;
		}
		
		HttpSession session = request.getSession(true);
		String loginName = (String)session.getAttribute("loginName");
		
		if(null == loginName) {
			response.getWriter().write("{\"statusCode\":\"301\",\"message\":\"会话已过期，请重新登陆\"}");
			return false;
		} else {
			//
		}
		
		return true;
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	private boolean isNotBlank(String str) {
		if(str == null) {
			return false;
		}
		if(str.trim().equals("")) {
			return false;
		}
		return true;
	}
}
