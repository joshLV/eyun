package com.rainsoft.base.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 获取该月的天数
	 * @param day
	 * @return
	 */
	public static int getMonthDaySum(String day){
		String[] str = day.split("-");
		 int year=Integer.parseInt(str[0]);
		 int month=Integer.parseInt(str[1]);
		 int Oyear=0;
		 for(int i=1900;i<year;i++){     
		          if(i%4==0&&i%100!=0||i%400==0){
		           Oyear=Oyear+366;
		          }else{
		           Oyear=Oyear+365;
		          }         
		} 
		 int[] arr={ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; 
		 if(year%4==0&&year%100!=0||year%400==0){
		  arr[1]=29;
		 }
		 int monthDay=arr[month-1];
		 return monthDay;
	}

	/**
	 * 将一个java.util.Date类型变量依指定格式转换为字符串
	 * 格式参照Constants类
	 * @param date 转换目标date
	 * @param dataFormat 转换格式
	 * @return 
	 */
	public static String dateToString(Date date, String dataFormat) {
		if(date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dataFormat);
		return format.format(date);
	}

	/**
	 * 根据指定格式将String格式Date转换为指定格式字符串
	 * 格式参照Constants类
	 * @param date 转换目标date
	 * @param dataFormat 转换格式
	 * @return 
	 */
	public static String formatDateString(String date, String dataFormat) {
		if(StringUtil.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dataFormat);
		return format.format(date);
	}
	
	/**
	 * 依指定格式将一个字符串转化为java.util.Date类型
	 * 
	 * @param src
	 *            要转换的字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date parseDate(String src, String format) {
		Date date = null;
		if (src == null || src.equals(""))
			return null;
		try {
			date = new SimpleDateFormat(format).parse(src);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return date;
	}

	/**
	 * 依指定格式将一个字符串转化为java.util.Date类型
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param parsePatterns
	 *            可能出现的所有日期格式
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String str, String[] parsePatterns) {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException(
					"Date and Patterns must not be null");
		}

		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if (date != null && pos.getIndex() == str.length()) {
				return date;
			}
		}
		return null;
	}
	
	
	/**
	 * 日期计算（计算日期中某位加上某值后的日期）
	 * 
	 * @param date
	 *            原日期
	 * @param type
	 *            日期某一位
	 * @param d
	 *            要加上的值
	 * @return
	 */
	public static Date afterTheDate(Date date, String type, int d) {
		if (d == 0)
			return date;
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			if (type.equals("d")) {
				c.add(Calendar.DATE, d);
			} else if (type.equals("M")) {
				c.add(Calendar.MONTH, d);
			} else if (type.equals("y")) {
				c.add(Calendar.YEAR, d);
			} else if (type.equals("H")) {
				c.add(Calendar.HOUR, d);
			} else if (type.equals("m")) {
				c.add(Calendar.MINUTE, d);
			} else if (type.equals("s")) {
				c.add(Calendar.SECOND, d);
			}
			return c.getTime();
		} else
			return null;
	}
	
	

	/**
	 * 得到两日期间的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int dateDiff(Date d1, Date d2) {
		long l = d1.getTime() - d2.getTime();
		long days = l / 1000 / 60 / 60 / 24;

		return new Long(days).intValue();
	}
}
