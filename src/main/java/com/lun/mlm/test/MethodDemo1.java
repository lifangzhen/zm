package com.lun.mlm.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法的反射操作
 *  Demo1
 * @author policy
 * @createdDate:2016年10月14日下午12:05:14
 * @version:1.0
 */
public class MethodDemo1 {
	public static void main(String[] args){
		A a1 = new A();
		Class c = a1.getClass();
		try {
			/**
			 * 获取方法 名称和参数列表来决定
			 * getMethod获取的是public 方法
			 * getDeclaredMethod自己声明的方法
			 */
//			Method m = c.getMethod("print", new Class[]{int.class, int.class});
			Method m = c.getMethod("print", int.class, int.class);
			//方法的放射操作
			//方法的反射操作是用m对象方法进行调用
			//方法如果没有返回值返回null,有返回值返回具体返回值
			Object o = m.invoke(a1, 10, 20);
			
			System.out.println("===================");
			Method m1 = c.getMethod("print", String.class, String.class);
			m1.invoke(a1, "hello", "world");
			
			System.out.println("===================");
			Method m2 = c.getMethod("print");
			m2.invoke(a1);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}

class A{
	public void print(){
		System.out.println("hello world");
	}
	public void print(int a, int b){
		System.out.println(a+b);
	}
	
	public void print(String a, String b){
		System.out.println(a.toUpperCase()+","+b.toUpperCase());
	}
}
