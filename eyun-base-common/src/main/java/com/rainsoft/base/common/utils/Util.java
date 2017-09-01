package com.rainsoft.base.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 * 功能说明：工具类
 * 
 * @author ducc
 * @created 2014年7月7日 下午2:57:10
 * @updated
 */
public class Util {

	/**
	 * 
	 * <br/>
	 * 方法描述: 根据类路径创建实例
	 * 
	 * @param url
	 *            类路径
	 * @return Object
	 * @throws Exception
	 *             Object
	 */
	public static Object createObject(String url) throws Exception {

		Object o = (Object) Class.forName(url).newInstance();
		return o;

	}

	/**
	 * 
	 * 方法功能描述:将double类型转换为指定格式
	 * 
	 * @param pattern
	 *            pattern:两位小数"0.00" <br>
	 *            三位小数"0.000"
	 * @param source
	 * @return String
	 */
	public static String formatData(String pattern, double source) {

		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(source);

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:将字符串转化为double类型 （默认值为 0.0）
	 * 
	 * @param o
	 *            需要转化的字符串对象
	 * @return double 数字
	 */
	public static double todouble(Object o) {

		if (o != null) {

			return Double.parseDouble(o.toString());

		} else {

			return 0.0;

		}

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:将字符串转化为long类型 （默认值为 0）
	 * 
	 * @param o
	 *            需要转化的字符串对象
	 * @return long数字
	 */
	public static long tolong(Object o) {

		if (o != null) {

			return Long.parseLong(o.toString());

		} else {

			return 0;

		}

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:将字符串转化为int类型 （默认值为 0）
	 * 
	 * @param o
	 *            需要转化的字符串对象
	 * @return int 数字
	 */
	public static int toint(Object o) {

		if (o != null) {

			return Integer.parseInt(o.toString());

		} else {

			return 0;

		}

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:判断对象是否为空
	 * 
	 * @param o
	 *            检测的对象
	 * @return boolean 表示为空 false 表示不为空
	 */
	public static boolean isNull(Object o) {

		if (o == null || "".equals(o) || "null".equals(o)) {

			return true;

		} else {

			return false;

		}

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:判断对象是否不为空
	 * 
	 * @param o
	 *            检测的对象
	 * @return boolean 表示为不为空 false 表示为空
	 */
	public static boolean isNotNull(Object o) {

		return !isNull(o);

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:字符编码转换：从iso转化成Utf8
	 * 
	 * @param source
	 *            源字符串
	 * @return String 编码后的字符串
	 */
	public static String isoToUtf8(String source) {

		String target = "";
		try {

			target = new String(source.getBytes("iso-8859-5"), "utf-8");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}
		return target;

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:字符编码转换：从iso转化成GBK
	 * 
	 * @param source
	 *            源字符串
	 * @return String 编码后的字符串
	 */
	public static String isoToGBK(String source) {

		String target = "";
		try {

			target = new String(source.getBytes("iso-8859-5"), "GBK");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}
		return target;

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:字符编码转换：从iso转化成GB2312
	 * 
	 * @param source
	 *            源字符串
	 * @return String 编码后的字符串
	 */
	public static String isoToGB2312(String source) {

		String target = "";
		try {

			target = new String(source.getBytes("iso-8859-5"), "gb2312");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}
		return target;

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 依照指定属性集合排序
	 * 
	 * @param collection
	 *            需要排序的集合
	 * @param sortCol
	 *            排序字段
	 * @param isAsc
	 *            true 升序 false 降序
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List sortCollection(List collection, String sortCol, boolean isAsc) {

		for (int i = 0; i < collection.size(); i++) {

			for (int j = i + 1; j < collection.size(); j++) {

				BeanWrapper bwi = new BeanWrapperImpl(collection.get(i));
				BeanWrapper bwj = new BeanWrapperImpl(collection.get(j));
				int leftI = (Integer) bwi.getPropertyValue(sortCol);
				int leftJ = (Integer) bwj.getPropertyValue(sortCol);
				if (isAsc) {

					if (leftI > leftJ) {

						Object obj = collection.get(j);
						collection.set(j, collection.get(i));
						collection.set(i, obj);

					}

				} else {

					if (leftI < leftJ) {

						Object obj = collection.get(j);
						collection.set(j, collection.get(i));
						collection.set(i, obj);

					}

				}

			}

		}
		return collection;

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 解析配置文件 ，得到的是一个Map 配置文件的写法如下: priceRule.subject.type.size=3
	 * priceRule.subject.type.0=0,\u5206\u652f\u79d1\u76ee
	 * priceRule.subject.type.1=1,\u586b\u62a5\u79d1\u76ee
	 * priceRule.subject.type.2=2,\u5206\u644a\u79d1\u76ee
	 * 
	 * @param fileName
	 *            要解析的文件全名，没有后缀名
	 * @param key
	 *            表示大小的key
	 * @return Map<String,String>
	 */
	public static Map<String, String> parsePropertiesReturnMap(String fileName, String key) {

		Map<String, String> map = new TreeMap<String, String>();
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		String value = rb.getString(key);
		int count = 0;
		if (null != value) {

			count = Integer.parseInt(value);

		}
		for (int i = 0; i < count; i++) {

			String subKey = key.replaceAll("size", (i + ""));
			map.put(rb.getString(subKey).split(",")[0], rb.getString(subKey).split(",")[1]);

		}
		return map;

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 解析配置文件 ，得到的是一个List 配置文件的写法如下: priceRule.subject.type.size=3
	 * priceRule.subject.type.0=0,\u5206\u652f\u79d1\u76ee
	 * priceRule.subject.type.1=1,\u586b\u62a5\u79d1\u76ee
	 * priceRule.subject.type.2=2,\u5206\u644a\u79d1\u76ee
	 * 
	 * @param fileName
	 *            要解析的文件全名，没有后缀名
	 * @param key
	 *            表示大小的key
	 * @return List<PropertiesItem>
	 */
	public static List<PropertiesItem> parsePropertiesReturnList(String fileName, String key) {

		List<PropertiesItem> list = new ArrayList<PropertiesItem>();
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		String value = rb.getString(key);
		int count = 0;
		if (null != value) {

			count = Integer.parseInt(value);

		}
		for (int i = 0; i < count; i++) {

			String subKey = key.replaceAll("size", (i + ""));
			PropertiesItem item = new PropertiesItem();
			item.setId(rb.getString(subKey).split(",")[0]);
			item.setName(rb.getString(subKey).split(",")[1]);
			list.add(item);

		}
		return list;

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 解析出配置文件。返回一个str
	 * 
	 * @param fileName
	 *            要解析的文件全名，没有后缀名
	 * @param key
	 *            key
	 * @return String
	 */
	public static String parsePropertiesReturnStr(String fileName, String key) {

		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		return rb.getString(key);

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 解析出配置文件。返回一个str,逗号右边的
	 * 
	 * @param fileName
	 *            要解析的文件全名，没有后缀名
	 * @param key
	 *            key
	 * @return String
	 */
	public static String parsePropertiesReturnStrAfterComma(String fileName, String key) {

		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		return rb.getString(key).split(",")[1];

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 解析出配置文件。返回一个str,逗号左边的
	 * 
	 * @param fileName
	 *            要解析的文件全名，没有后缀名
	 * @param key
	 *            key
	 * @return String
	 */
	public static String parsePropertiesReturnStrBeforeComma(String fileName, String key) {

		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		return rb.getString(key).split(",")[0];

	}

	/**
	 * 
	 * <br/>
	 * 方法描述:获取uuid去掉“-” <br/>
	 * xlp
	 * 
	 * @return String
	 */
	public static String getUuid() {

		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");

	}

	public static boolean isNumber(String numStr) {

		Pattern pattern = Pattern.compile("[0-9]*"); // Character.isDigit(char);
														// or ascii码
		return pattern.matcher(numStr).matches();

	}

	public static boolean isInteger(String value) {

		try {

			Integer.parseInt(value);
			return true;

		} catch (NumberFormatException e) {

			return false;

		}

	}

	public static boolean isDouble(String value) {

		try {

			Double.parseDouble(value);
			return true;

		} catch (NumberFormatException e) {

			return false;

		}

	}

	public static boolean isInteger4Reg(String value) {

		// "^[0-9]*(\.[0])?$";
		Pattern pattern = Pattern.compile("^[0-9]*(\\.[0])?$"); // Character.isDigit(char);
																// or ascii码
		return pattern.matcher(value).matches();

	}

	/**
	 * 
	 * <br/>
	 * 方法描述: 按数据库总记录数与每页记录数计算出总页数
	 * 
	 * @param logCount
	 *            数据库的总页数
	 * @param pageSize
	 *            页面显示时的第页的记录数
	 * @return int 返回此次查询的总页数
	 */
	public static int calculateTotalPage(int logCount, int pageSize) {

		if (logCount % pageSize == 0) {

			return logCount / pageSize;

		} else {

			return (logCount / pageSize) + 1;

		}

	}

	// 将一整型数值转化为大写字符串,如将"123"转化为"一百二十三"
	public static String changeNum(int oldNumber) {

		String newNumber = "";
		char[] temp = String.valueOf(oldNumber).toCharArray();

		for (int i = 0; i < temp.length; i++) {

			boolean sign = true;
			switch (temp[i]) {

			case '1':
				newNumber += "一";
				break;
			case '2':
				newNumber += "二";
				break;
			case '3':
				newNumber += "三";
				break;
			case '4':
				newNumber += "四";
				break;
			case '5':
				newNumber += "五";
				break;
			case '6':
				newNumber += "六";
				break;
			case '7':
				newNumber += "七";
				break;
			case '8':
				newNumber += "八";
				break;
			case '9':
				newNumber += "九";
				break;
			case '0':
				if (i != temp.length - 1) {

					if (temp[i + 1] == '0') {

						sign = false;
						break;

					} else {

						newNumber += "零";
						break;

					}

				} else
					break;

			}
			if (!newNumber.endsWith("零") && sign == true) {

				switch (temp.length - i) {

				case 12:
					newNumber += "千";
					break;
				case 11:
					newNumber += "百";
					break;
				case 10:
					newNumber += "十";
					break;

				case 8:
					newNumber += "千";
					break;
				case 7:
					newNumber += "百";
					break;
				case 6:
					newNumber += "十";
					break;

				case 4:
					newNumber += "千";
					break;
				case 3:
					newNumber += "百";
					break;
				case 2:
					newNumber += "十";
					break;

				}

			}
			switch (temp.length - i) {

			case 9:
				newNumber += "亿";
				break;
			case 5:
				newNumber += "万";
				break;

			}

		}
		if (newNumber.startsWith("一十")) {

			newNumber = newNumber.replaceFirst("一十", "十");

		}
		newNumber = newNumber.replaceAll("亿万", "亿");
		newNumber = newNumber.replaceAll("零万", "万");
		return newNumber;

	}

	// 类似js中的join使用 比如说 ： List(^) --->> xxx^yyy^
	public static String join(Collection<String> s, String delimiter) {

		if (s.isEmpty())
			return "";
		Iterator<String> iter = s.iterator();
		StringBuffer buffer = new StringBuffer(iter.next());
		while (iter.hasNext())
			buffer.append(delimiter).append(iter.next());
		return buffer.toString();

	}

	/**
	 * 将输入流转为字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();

		}
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {

			while ((line = reader.readLine()) != null) {

				sb.append(line);

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				is.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}
		return sb.toString();

	}

	/**
	 * 去除集合中的Null对象
	 * 
	 * @param collection
	 *            原集合对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection removeNullObject4Collection(Collection collection) {

		if (collection == null)
			return collection;
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {

			Object o = iterator.next();
			if (o == null)
				iterator.remove();

		}
		return collection;

	}
	
	
	/**
	 * 去除空值及签名方式
	 * @param map
	 * @return
	 */
	public static Map<String, String> paramsFilter(Map<String, String> map) {
		Map<String, String> dataMap = new HashMap<String, String>();
		if(null == map || map.size() <= 0) {
			return new HashMap<String, String>();
		}
		for(String key : map.keySet()) {
			String value = map.get(key);
			if(null == value || "".equals(value) || "sign_type".equals(key) || "sign".equals(key)) {
				continue;
			} else {
				dataMap.put(key, value);
			}
		}
		return dataMap;
	}
	
	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param map
	 * @return
	 */
	public static String createLinkString(Map<String, String> map) {
		String paramsStr = "";
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		for(int i = 0; i < keys.size(); i ++) {
			if(i == keys.size() - 1) {
				paramsStr = paramsStr + keys.get(i) + "=" + map.get(keys.get(i));
			} else {
				paramsStr = paramsStr + keys.get(i) + "=" + map.get(keys.get(i)) + "&";
			}
		}
		return paramsStr;
	}
	

}