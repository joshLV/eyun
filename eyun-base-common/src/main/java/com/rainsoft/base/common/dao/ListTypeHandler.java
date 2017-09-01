package com.rainsoft.base.common.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;

@SuppressWarnings("rawtypes")
public class ListTypeHandler implements TypeHandler{
	private static final String TYPE = "RESOURCE_PRIVILEGE";
	private static final String TYPE_TABLE = "RESOURCE_PRIVILEGE_TABLE";
	@SuppressWarnings("unchecked")
	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		//获取OracleConnection  
		C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = (C3P0NativeJdbcExtractor) new C3P0NativeJdbcExtractor();  
		OracleConnection oracleConn=(OracleConnection)cp30NativeJdbcExtractor.getNativeConnection(ps.getConnection());  
		//这个parameter就是我们自己在mapper中传入的参数  
		List<Object> params = (ArrayList<Object>)parameter;
		//获取传入的对象的类型
		Class classType = params.get(0).getClass();
		//获取该类的所有属性的get方法
		Method[] methods = getGetMethod(classType);
		StructDescriptor sd = new StructDescriptor(TYPE,oracleConn);
		STRUCT[] result = new STRUCT[params.size()];
		for(int index = 0; index < params.size(); index++){
			Object[] obj = new Object[methods.length];
			for(int j = 0; j < methods.length; j++) {
				try {
					//取出各属性的值
					obj[j] = methods[j].invoke(params.get(index), new Object[] {});
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			result[index] = new STRUCT(sd,oracleConn,obj);  
		}

		ArrayDescriptor ad = ArrayDescriptor.createDescriptor(TYPE_TABLE,oracleConn);  
		ARRAY oracle_array = new ARRAY(ad,oracleConn,result);  
		ps.setArray(i, oracle_array); 
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
	
	//获取类的get方法
	private Method[] getGetMethod(final Class<? extends Object> classType) {
		Field[] fields = classType.getDeclaredFields();
		int arrLength = fields.length;
		Method[] methods = new Method[arrLength];
		//属性的第一个字母
		String firstLetter = null;
		//方法名称
		String methodName = null;
		for(int index = 0; index < arrLength; index++) {
			firstLetter = fields[index].getName().substring(0, 1).toUpperCase();
			methodName = "get" + firstLetter + fields[index].getName().substring(1);
			try {
				methods[index] = classType.getMethod(methodName, new Class[]{});
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return methods;
	}
}
