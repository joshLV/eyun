package com.rainsoft.base.common.utils;


/**
 * 字符串处理工具类
 *
 * @author jinyanjie
 */
public class StringUtil {

    /**
     * 判断是否指定字符串为空字符串(null或者长度为0)
     *
     * @param stringValue
     * @return boolean
     */
    public static boolean isEmpty(String stringValue) {
        if (stringValue == null || stringValue.trim().length() < 1 || stringValue.trim().equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否指定字符串为非空字符串
     *
     * @param stringValue
     * @return boolean
     */
    public static boolean isNotEmpty(String stringValue) {
        if (stringValue == null || stringValue.trim().length() < 1 || stringValue.trim().equalsIgnoreCase("null")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否指定字符串为空字符串(null或者长度为0
     *
     * @param stringValue
     * @return boolean
     */
    public static boolean isEmptyString(String stringValue) {
        if (stringValue == null || stringValue.trim().length() < 1 || stringValue.trim().equalsIgnoreCase("null")) {
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
     * 字符串倒序
     *
     * @param str 字符串
     * @return 倒序后的字符串
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

}
