package com.lun.mlm.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lun.mlm.MlmException;

/**
 * 异常处理
 *  HandlerException
 * @author policy
 * @createdDate:2016年7月22日上午12:05:35
 * @version:1.0
 */
public class HandlerException implements HandlerExceptionResolver {
	private static final Log log = LogFactory.getLog(HandlerException.class);
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = new ModelAndView();
		String statusCode = "300";
		String message = "请求出错";
		String requestUri = request.getRequestURI();
		boolean isJson = false;

		if(requestUri.endsWith("/json")) {
			isJson = true;
		}
		
		if(ex instanceof MlmException) {
			MlmException je = (MlmException)ex;
			statusCode = je.getStatusCode();
			message = je.getMessage();
		} else if(ex instanceof MissingServletRequestParameterException) {
			statusCode = "300";
			message = "请检查必传的参数是否完整！";
		} else {
			log.error(ex);
			ex.printStackTrace();
		}
		
		if(isJson) {
			mav.setViewName("errors/json");
		} else {
			mav.setViewName("errors/page");
		}
		
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		
		// 出现异常时记录请求参数
		Map<String, String[]> reqMap = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		sb.append("HandlerException. ").append("statusCode:[").append(statusCode);
		sb.append("].message:[").append(message).append("]. request param:[");
		for(Map.Entry<String, String[]> entry : reqMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(":").append(entry.getValue()[0]).append(",");
		}
		sb.append("]");
		log.error(sb.toString());
		return mav;
	}

}
