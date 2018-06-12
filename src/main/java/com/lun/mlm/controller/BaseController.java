package com.lun.mlm.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;

import com.lun.mlm.model.PageParam;
import com.lun.mlm.utils.StringUtil;

/**
 * Controller基类
 *  BaseController
 * @author policy
 * @createdDate:2016年8月17日上午10:20:20
 * @version:1.0
 */
public class BaseController {
	 protected HttpServletRequest request;
	 protected HttpServletResponse response;
	 protected HttpSession session; 
	
	@ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession(true);  
    } 
	
	public String getParaMeter(String key){
		return this.request.getParameter(key);
	}
	
	public void setSession(String key , Object value){
		this.removeSession(key);
		session.setAttribute(key, value);
	}
	
	public Object getSession(String key){
		return session.getAttribute(key);
	}
	
	public void removeSession(String key){
		session.removeAttribute(key);
	}
	
	public void removeAllSession(){
		Enumeration keys = 	session.getAttributeNames();
		while(keys.hasMoreElements()){
			session.removeAttribute((String) keys.nextElement());
		}
	}
	
	public Map<String, Object> success(){
		return this.success(false, "");
	}
	
	public Map<String, Object> success(Boolean close){
		return this.success(close, "");
	}
	
	public Map<String, Object> success(Boolean close, String navTabId){
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("statusCode", "200");
		re.put("message", "操作成功");
		if(close){
			re.put("callbackType", "closeCurrent");
		}
		if(StringUtil.isNotBlank(navTabId)){
			re.put("navTabId", navTabId);
		}
		return re;
	}
	
	public Map<String, Object> success(Map<String, Object> result){
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("statusCode", "200");
		Iterator it = result.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			re.put(key, result.get(key));
		}
		return re;
	}
	
	public PageParam getPageParam(){
		String pageNumStr = this.request.getParameter("pageNum");
		String numPerPageStr = this.request.getParameter("numPerPage");
		Integer pageNum = PageParam.PAGE_NUM;
		Integer numPerPage = PageParam.NUM_PER_PAGE;
		if(StringUtils.isNotBlank(pageNumStr)){
			pageNum = Integer.parseInt(pageNumStr);
		}
		if(StringUtils.isNotBlank(numPerPageStr)){
			numPerPage = Integer.parseInt(numPerPageStr);
		}
		Integer start = (pageNum-1)*numPerPage;
		Integer end = pageNum*numPerPage;
		PageParam pp = new PageParam();
		pp.setStart(start);
		pp.setEnd(end);
		pp.setNumPerPage(numPerPage);
		pp.setPageNum(pageNum);
		return pp;
		
	}
	
}
