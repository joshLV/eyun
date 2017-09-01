package com.rainsoft.base.common.model;

/**
 * 功能说明：简单树基类
 * 
 * @author admin
 * @created 2014年6月14日 下午1:03:33
 * @updated
 */
public abstract class SimpleTreePersistenceComon extends PersistenceCommon {

	/** 节点名称 */
	protected String name;
	/** 节点名称code */
	protected String code;
	/** 节点链接的目标 URL */
	protected String url;
	/** 节点链接的目标 图片 */
	protected String icon;
	/**
	 * 节点的父级id 第一层节点为"-1"
	 * */
	protected String pId;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getCode() {

		return code;

	}

	public void setCode(String code) {

		this.code = code;

	}

	public String getUrl() {

		return url;

	}

	public void setUrl(String url) {

		this.url = url;

	}

	public String getIcon() {

		return icon;

	}

	public void setIcon(String icon) {

		this.icon = icon;

	}

	public String getpId() {

		return pId;

	}

	public void setpId(String pId) {

		this.pId = pId;

	}
}