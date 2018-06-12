package com.lun.mlm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public final static String FULL_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";// 时间精确到秒
	public final static String DATE_PATTERN = "yyyy-MM-dd";// 时间精确到天
	public final static String MONTH_PATTERN = "yyyy-MM";// 时间精确到月
	public final static String DATE_HOUR_PATTERN = "yyyy-MM-dd HH";// 时间精确到天
	public final static String FROM_MONTH_PATTERN = "MM-dd HH:mm:ss";// 时间精确到秒
	public final static String NOT_SEPARATOR_DATE_PATTERN = "yyyyMMdd";// 没有分隔�?
	public final static String NOT_SEPARATOR_TIME_PATTERN = "yyyyMMddHHmmss";// 时间精确到秒

	public DateUtil() {
	}

	/**
	 * 将时间转换成规定格式的字符串
	 * 
	 * @param pattern
	 *            String 格式
	 * @param date
	 *            Date �?��转换的时�?
	 * @return String 转换后的时间字符�?
	 */
	public static String dateToString(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	

	/**
	 * 将当前时间转换成字符�?
	 * 
	 * @return String 转换后的时间字符�?
	 */
	public static String currenDateToString() {
		return dateToString(FULL_TIME_PATTERN, new Date());
	}

	/**
	 * 将当前时间转换成指定格式的字符串
	 * 
	 * @param pattern
	 *            String 指定格式
	 * @return String 转换后的时间字符�?
	 */
	public static String currenDateToString(String pattern) {
		return dateToString(pattern, new Date());
	}

	/**
	 * 比较两日期是否相�?
	 * 
	 * @param pattern
	 *            String 格式
	 * @param sFirstDate
	 *            String 比较的第�?��日期字符�?
	 * @param sSecondlyDate
	 *            String 比较的第二个日期字符�?
	 * @return boolean true 第一个日期在第二个日期之前； false 第一个日期和第二个日期相等或之后
	 */
	public static boolean compareDate(String pattern, String sFirstDate, String sSecondlyDate) {
		Date firstDate = formatDate(pattern, sFirstDate);
		Date secondlyDate = formatDate(pattern, sSecondlyDate);
		return firstDate.before(secondlyDate);
	}
	
	public static boolean compareDate(String pattern, Date firstDate, Date secondDate) {
		String sFirstDate = dateToString(pattern, firstDate);
		String sSecondlyDate = dateToString(pattern, secondDate);
		return compareDate(pattern, sFirstDate, sSecondlyDate);
	}

	/**
	 * 格式化日�?
	 * 
	 * @param pattern
	 *            String 格式
	 * @param sDate
	 *            String 日期字符�?
	 * @return Date 格式化后的日�?
	 */
	public static Date formatDate(String pattern, String sDate) {
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			date = dateFormat.parse(sDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	/**
	 * 功能：滚动日�?
	 * 
	 * @param date
	 *            Date �?��滚动的日�?
	 * @param field
	 *            int �?��滚动的字段（参照Calendar的字段）
	 * @param step
	 *            int 步长 正数时是增加时间，�?负数时是减少时间
	 * @return Date 滚动后的日期
	 */
	public static Date rollDate(Date date, int field, int step) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, step);
		return calendar.getTime();
	}

	/**
	 * 设置时间
	 * 
	 * @param date
	 *            Date 设置的时�?
	 * @param field
	 *            int 设置的字段（参照Calendar的字段）
	 * @param value
	 *            int 时间
	 * @return Date 设置后的时间
	 */
	public static Date setDate(Date date, int field, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(field, value);
		return calendar.getTime();
	}

	/**
	 * 获得时间中的字段的�?
	 * 
	 * @param field
	 *            int 时间字段（参照Calendar的字段）
	 * @return int 时间字段�?
	 */
	public static int getField(int field) {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(field);
	}
	
	public static String getSimpleDateFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(DATE_PATTERN);
        String result=sdf.format(date.getTime());
        return result;
    }
	
	public static String getHostSerialDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyMMdd");
        String result=sdf.format(date.getTime());
        return result;
	}
	
	/**
	 * 获取当月1号
	 * @return
	 */
	public static String getMonth01(){
		return currenDateToString(DateUtil.MONTH_PATTERN)+"-01";
	}
	
	public static Date getTodayEnd(){
		String end = DateUtil.currenDateToString(DATE_PATTERN)+" 23:59:59";
		return DateUtil.formatDate(DateUtil.FULL_TIME_PATTERN, end);
	}
	
}
