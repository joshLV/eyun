package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.IdEntity;

/**
 * 地区实体类
 * 
 * @author Administrator
 * 
 */
public class BaseArea extends IdEntity {
	/** 区域名称 */
	private String areaName;
	/** 父ID */
	private String pId;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}
}
