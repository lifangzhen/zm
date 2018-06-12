package com.lun.mlm.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtil {

	/**
	 * 打印类的信息,获取成员函数
	 * @param obj
	 */
	public static void printClassMessage(Object obj){
		Class c = obj.getClass();
		System.out.println("类的名称是:"+c.getName());
		//获取类的所有方法，包括继承而来的
		//getDeclaredMethods()只包含自己申明的方法
		Method[] mds = c.getMethods();//c.getDeclaredMethods()
		for (Method method : mds) {
			//获取返回类型的类类型
			Class returnType = method.getReturnType();
			System.out.print(returnType.getName()+" ");
			//获取类名称
			System.out.print(method.getName()+"(");
			//获取参数类型-->得到参数列表的类型的类类型
			Class[] cp = method.getParameterTypes();
			for (Class class1 : cp) {
				System.out.print(class1.getName()+",");
			}
			System.out.println(" )");
		}
		
	}

	/**
	 * 获取成员变量信息（自己声明的）
	 * @param obj
	 */
	public static void printFieldMessage(Object obj) {
		Class c = obj.getClass();
		/**
		 * 成员变量也是对象
		 * java.lang.reflect.Field
		 * Field类封装了关于成员变量的操作
		 * getFields()方法获取的是所有的public的成员变量信息
		 * getDeclaredFields()获取的是该类自己声明的成员变量信息
		 */
		Field[] field = c.getDeclaredFields();
		for (Field f : field) {
			Class fieldType = f.getType();
			String typeName = fieldType.getName();
			String fieldName = f.getName();
			System.out.println(typeName +" "+fieldName);
		}
	}
	
	/**
	 * 获取构造方法
	 * @param obj
	 */
	public static void printConMessage(Object obj){
		Class c = obj.getClass();
		/**
		 * 构造函数也是对象
		 * java.lang.Constructor中封装了构造函数的信息
		 * getConstructors()获取所有的public构造函数
		 * getDeclaredConstructors()获取所有的构造函数
		 */
		Constructor[] cs = c.getDeclaredConstructors();
		for (Constructor constructor : cs) {
			System.out.print(constructor.getName()+"(");
			//获取构造函数的参数列表-->得到的是参数列表的类类型
			Class[] paramTypes = constructor.getParameterTypes();
			for (Class class1 : paramTypes) {
				System.out.print(class1.getName()+",");
			}
			System.out.println(")");
			
		}
	}
}
