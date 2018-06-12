package com.lun.mlm.test;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodDemo2 {

	public static void main(String[] args){
		ArrayList list = new ArrayList();
		
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("hello");
//		list1.add(20); 错误的
		Class c = list.getClass();
		Class c1 = list1.getClass();
		System.out.println(c==c1);
		//反射的操作都是编译后的操作
		
		/*
		 * c==c1结果返回true说明编译之后集合的范型是去范型化的
		 * java中集合的范型，是防止错误输入的，只在编译阶段有效
		 * 绕过编译就无效了
		 * 验证：我么可以通过方法的反射来操作，绕过编译
		 */
		
		try{
			Method m = c1.getMethod("add", Object.class);
			m.invoke(list1, 20);
			System.out.println(list1.size());
			System.out.println(list1);
//			for (String string : list1) {
//				System.out.println(string);
//			}//现在不能这样遍历
		}catch(Exception e){
			
		}
		
	}
}
