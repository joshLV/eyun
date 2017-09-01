package com.rainsoft.base.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 公共工具类
 * 
 * @author jinyanjie
 */
public class CommonUtil {
    /**
     * 获取当前年
     * 
     * @param value
     * @return
     * @throws Exception
     * @author jinyanjie
     */
    public static int getyear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * 
     * @param value
     * @return
     * @throws Exception
     * @author jinyanjie
     */
    public static int getmonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日
     * 
     * @param value
     * @return
     * @throws Exception
     * @author jinyanjie
     */
    public static int getday() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
    }

    /**
     * 随机获取字符串
     * 
     * @param length
     *            随机字符串长度
     * 
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
		if (length <= 0) {
		    return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
			'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
			'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
		    stringBuffer.append(randomChar[Math.abs(random.nextInt())
			    % randomChar.length]);
		}
		return stringBuffer.toString();
    }
    
    /**
     * 随机获取(数字)字符串
     * 
     * @param length
     *            随机字符串长度
     * 
     * @return 随机字符串
     */
    public static String getRandomNumberStr(int length) {
		if (length <= 0) {
		    return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
		    stringBuffer.append(randomChar[Math.abs(random.nextInt())
			    % randomChar.length]);
		}
		return stringBuffer.toString();
    }
    
    /**
     * 随机获取字符串
     * @param code 		不包含该字符
     * @param length	随机字符串长度
     * @return
     */
    public static String getNotRepeatNumberStr(char code, int length){
    	if (length <= 0) {
		    return "";
		}
    	//System.out.println("**********" + code);
    	int num = (int)code;
    	char[] randomChar = new char[10];
    	for(int i = 0; i <= 9; i++){
    		if(i != num){
    			randomChar[i] = (char) i;
    		}
    	}
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
		    stringBuffer.append(randomChar[Math.abs(random.nextInt())
			    % randomChar.length]);
		}
		//System.out.println("**********" + randomChar);
		return stringBuffer.toString();
    }


    /**
     * MD5 密码加密
     * 
     * @return 32位密文
     */
    public static String getMd5(String plainText) {
		String re_md5 = new String();
		try {
		    MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(plainText.getBytes());
		    byte b[] = md.digest();
		    int i;
		    StringBuffer buf = new StringBuffer("");
		    for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
			    i += 256;
			if (i < 16)
			    buf.append("0");
			buf.append(Integer.toHexString(i));
		    }
	
		    re_md5 = buf.toString();
	
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
		return re_md5;
    }

    /**
     * 判断是否指定字符串为空字符串(null或者长度为0
     * 
     * @param stringValue
     * @return boolean
     */
    public static boolean isEmpty(String stringValue) {
		if (stringValue == null || stringValue.trim().length() < 1
			|| stringValue.trim().equalsIgnoreCase("null")) {
	
		    return true;
		} else {
		    return false;
		}
	    }
	
	    public static boolean isNumber(String str) {
		if (isEmptyString(str)) {
		    return false;
		}
	
		for (int i = 0; i < str.length(); i++) {
		    char ch = str.charAt(i);
	
		    if (ch < '0' || ch > '9') {
			return false;
	
		    }
		}
		return true;
    }

    /**
     * 判断是否指定字符串为空字符串(null或者长度为0
     * 
     * @param stringValue
     * @return boolean
     */
    public static boolean isEmptyString(String stringValue) {
		if (stringValue == null || stringValue.trim().length() < 1
			|| stringValue.trim().equalsIgnoreCase("null")) {
		    return true;
		} else {
		    return false;
		}
    }
    
	public static String getDateStr(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(time);
	}

}
