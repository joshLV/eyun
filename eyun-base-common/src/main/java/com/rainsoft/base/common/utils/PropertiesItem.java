package com.rainsoft.base.common.utils;

/**
 * 功能说明：以List读取配置文件返回类型 id:对应配置文件中的id值 name:对应配置文件中的name值
 * 
 * @author ducc
 * @created 2014年6月13日 下午3:40:22
 * @updated
 */
public class PropertiesItem {

	private String id; // 配置文件中的id值
	private String name;// 配置文件中的name值

	public String getId() {

		return id;

	}

	public void setId(String id) {

		this.id = id;

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

}