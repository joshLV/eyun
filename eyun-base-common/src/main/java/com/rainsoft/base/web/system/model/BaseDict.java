package com.rainsoft.base.web.system.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 
 * 数据字典
 *
 */
public class BaseDict extends PersistenceCommon{
	
	/**数据标签*/
	private String dataLabel;
	/**编码*/
	private String dataCode;
	/**值*/
	private String dataValue;
	
	public String getDataLabel() {
		return dataLabel;
	}
	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	
}
