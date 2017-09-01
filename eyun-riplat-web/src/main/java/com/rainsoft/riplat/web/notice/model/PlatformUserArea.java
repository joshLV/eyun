package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.Date;

import com.rainsoft.base.common.model.IdEntity;

/*
 * 平台用户管理的区域
 */
public class PlatformUserArea extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3003628174997580384L;

	private String userId;//登录ID
    
    private Date createTime;//创建时间
    
    private String areaId;//管理区域
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
    
}
