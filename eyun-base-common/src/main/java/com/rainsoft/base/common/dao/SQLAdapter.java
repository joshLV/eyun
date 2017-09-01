package com.rainsoft.base.common.dao;

/**
 * 功能说明：sql适配器
 * 
 * @author admin
 * @created 2014年6月29日 下午4:41:04
 * @updated
 */
public class SQLAdapter {

	String sql;

	/**
	 * @param sql2
	 */
	public SQLAdapter(String sql) {

		this.sql = sql;

	}

	/**
	 * @return the sql
	 */
	public String getSql() {

		return sql;

	}

	/**
	 * @param sql
	 *            the sql to set
	 */
	public void setSql(String sql) {

		this.sql = sql;

	}

}