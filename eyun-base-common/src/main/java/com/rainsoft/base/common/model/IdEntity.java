package com.rainsoft.base.common.model;

import java.io.Serializable;

/**
 * 功能说明：统一定义id的entity基类
 * 
 * @author admin
 * @created 2014年6月12日 下午3:06:02
 * @updated
 */
public abstract class IdEntity implements Serializable{
	/**
	 * 对象序列化
	 */
	private static final long serialVersionUID = -3937924917935866704L;
	
	protected Integer id;
	protected String placeCode;			/**场所编号*/

	public Integer getId() {

		return id;

	}

	public void setId(Integer id) {

		this.id = id;

	}

	private String searchValue;

	public String getSearchValue() {

		return searchValue;

	}

	public void setSearchValue(String searchValue) {

		this.searchValue = searchValue;

	}

	public String getPlaceCode() {
		return placeCode;
	}
	
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

}