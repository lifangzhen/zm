package com.lun.mlm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 资源文件工具
 *  ResourceUtil
 * @author policy
 * @createdDate:2016年8月1日下午1:43:01
 * @version:1.0
 */
public class ResourceUtil {
	private ResourceBundle resourceBundle;
	
	private ResourceUtil(String resource){
		resourceBundle = ResourceBundle.getBundle(resource);
	}
	
	public static ResourceUtil getResource(String resource){
		return new ResourceUtil(resource);
	}
	
	/**
	 * 将资源转换成map
	 * @return
	 */
	public Map<String, String> getMap(){
		Map<String, String> map = new HashMap<String, String>();
		for(String key:resourceBundle.keySet()){
			map.put(key, resourceBundle.getString(key));
		}
		return map;
	}
}
