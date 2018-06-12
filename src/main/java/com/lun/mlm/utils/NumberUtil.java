package com.lun.mlm.utils;

public class NumberUtil {

	public static Integer parseInt(String str){
		Integer re = null;
		if(StringUtil.isNotBlank(str)){
			re = Integer.parseInt(str);
		}
		return re;
	}
}
