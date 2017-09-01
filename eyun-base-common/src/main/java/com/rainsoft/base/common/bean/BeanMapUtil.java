package com.rainsoft.base.common.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaBean and map converter.
 * 
 * 
 */
public final class BeanMapUtil {
	
	/**
	 * Converts a map to a JavaBean.
	 * 
	 * @param type type to convert
	 * @param map map to convert
	 * @return JavaBean converted
	 * @throws IntrospectionException failed to get class fields
	 * @throws IllegalAccessException failed to instant JavaBean
	 * @throws InstantiationException failed to instant JavaBean
	 * @throws InvocationTargetException failed to call setters
	 */
	public static final Object toBean(Class<?> type, Map<String, ? extends Object> map) 
			throws IntrospectionException, IllegalAccessException,	InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i< propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[1];
				args[0] = value;
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
	
	/**
	 * Converts a JavaBean to a map.
	 * 
	 * @param bean JavaBean to convert
	 * @return map converted
	 * @throws IntrospectionException failed to get class fields
	 * @throws IllegalAccessException failed to instant JavaBean
	 * @throws InvocationTargetException failed to call setters
	 */
	public static final Map<String, Object> toMap(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i< propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				}				
			}
		}
		return returnMap;
	}
	
	/**
	 * bean to map
	 * @param entity
	 * @return
	 */
	public static Map<String, String> beanToMap(Object entity) {
		Map<String, String> dataMap = new HashMap<String, String>();
		Field[] fields = entity.getClass().getDeclaredFields();
		for(Field field : fields) {
			String firstLetter = field.getName().substring(0, 1).toUpperCase();
			String methodName = "get" + firstLetter + field.getName().substring(1);
			try {
				Method method = entity.getClass().getMethod(methodName, new Class[]{});
				Object obj = method.invoke(entity, new Object[] {});
				String str = "";
				if(null != obj) {
					str = obj.toString();
				}
				dataMap.put(field.getName(), str);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return dataMap;
	}
}
