package com.rainsoft.base.common.utils;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/***
 * 
 *
 * 类功能说明：易韵消息接口认证token摘要类
 * 这个摘要类实现为单例，校验一个签名是否合法的例子如下 
 *
 * <pre>
 * YiYunMessageDigest yyDigest = YiYunMessageDigest.getInstance();
 * boolean bValid = yyDigest.validate(signature, timestamp, nonce);
 * </pre>
 * 生成新的签名
 * <pre>
 * yyDigest.getSignature();
 * </pre>
 * 
 * @author sh_j_baoxu
 *
 * @date 2016年4月12日 下午6:02:51
 */
public class YiYunMessageDigest {
	private static MessageDigest digest;

	/**
	 * 
	 *
	 * 类功能说明： 单例持有类
	 *
	 * @author sh_j_baoxu
	 *
	 * @date 2016年4月12日 下午6:03:43
	 */
	private static class SingletonHolder {
		static final YiYunMessageDigest INSTANCE = new YiYunMessageDigest();
	}

	/**
	 * 
	 * 方法功能说明：获取单例
	 *
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 下午6:03:52
	 * @return
	 */
	public static YiYunMessageDigest getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private YiYunMessageDigest() {
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("init MessageDigest error:" + e);
		}
	}

	/**
	 * 
	 * 方法功能说明：将字节数组转换成16进制字符串
	 *
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 下午6:04:11
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder sbDes = new StringBuilder();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				sbDes.append("0");
			}
			sbDes.append(tmp);
		}
		return sbDes.toString();
	}

	/**
	 * 
	 * 方法功能说明：字符串进行SHA-1加密，并返回16进制字符串
	 * 
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 上午10:38:57
	 * @param strSrc
	 * @return
	 */
	private static String encrypt(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		digest.update(bt);
		strDes = byte2hex(digest.digest());
		return strDes;
	}

	/**
	 * 
	 * 方法功能说明：校验请求的签名是否合法
	 *
	 * 加密/校验流程： 1. 将token、timestamp、nonce三个参数进行字典序排序 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于易韵
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 下午6:04:24
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean validate(String signature, String timestamp, String nonce) {
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		String token = getToken();
		String[] arrTmp = { token, timestamp, nonce };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.将三个参数字符串拼接成一个字符串进行sha1加密
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String expectedSignature = encrypt(sb.toString());
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于易韵
		if (expectedSignature.equals(signature)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 方法功能说明：校验请求的签名是否合法
	 *
	 * 加密/校验流程： 1. 将token、timestamp、nonce三个参数进行字典序排序 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于易韵
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 下午6:04:24
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param timeOut 过期时间（单位/分）
	 * @return
	 */
	public boolean validate(String signature, String timestamp, String nonce, Long timeOut) {
		// 签名是否过期
		if (null != timeOut && timeOut != 0L) {
			Long interval = (System.currentTimeMillis() - Long.parseLong(timestamp)) / (1000 * 60);
			if (interval >= timeOut) {
				return false;
			}
		}
		return validate(signature, timestamp, nonce);
	}

	/**
	 * 
	 * 方法功能说明：校验请求的签名是否合法
	 *
	 * 加密/校验流程： 1. 将token、timestamp、nonce三个参数进行字典序排序 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于易韵
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 下午6:04:24
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param isTimeOut 开启过期验证，过期时间为5分钟
	 * @return
	 */
	public boolean validate(String signature, String timestamp, String nonce, Boolean isTimeOut) {
		return validate(signature, timestamp, nonce, 5L);
	}

	/**
	 * 
	 * 方法功能说明：获取系统token 
	 * TODO 后期该值需从配置文件或者常量类中取
	 * 
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 上午10:39:45
	 * @return
	 */
	private static String getToken() {
		return "ne@bu)in!fo^";
	}

	/**
	 * 
	 * 方法功能说明：生成新的签名
	 * 加密流程：
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 上午10:59:49
	 * @return Map<String, String> key:signature、timestamp、nonce
	 */
	public Map<String, String> getSignature() {
		Map<String, String> map = new HashMap<String, String>();
		String timestamp = String.valueOf(System.currentTimeMillis());
		String nonce = getRandomString(13);
		String token = getToken();
		String[] arrTmp = { token, timestamp, nonce };
		Arrays.sort(arrTmp);
		StringBuffer sb = new StringBuffer();
		// 2.将三个参数字符串拼接成一个字符串进行sha1加密
		for (int i = 0; i < arrTmp.length; i++) {
			sb.append(arrTmp[i]);
		}
		String signature = encrypt(sb.toString());
		map.put("signature", signature);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		return map;
	}

	/**
	 * 
	 * 方法功能说明：返回长度为【strLength】的随机数
	 * 
	 * @author sh_j_baoxu
	 * @data 2016年4月12日 上午10:50:02
	 * @param length
	 * @return
	 */
	private static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}