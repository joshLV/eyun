package com.rainsoft.base.common.web.vo;

import java.util.Date;

public class AuthenToken {

	// 登录用户id
	private Integer userId;
	// 登录名称
	private String loginName;
	// 用户別名
	private String anotherName;
	// 用户登录时间
	private Date loginTime;
	// 默认场所
	private String defaultPlaceCode = "-1";
	
	private String platformId;//用户平台

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public Integer getUserId() {

		return userId;

	}

	public void setUserId(Integer userId) {

		this.userId = userId;

	}

	public String getLoginName() {

		return loginName;

	}

	public void setLoginName(String loginName) {

		this.loginName = loginName;

	}

	public String getAnotherName() {

		return anotherName;

	}

	public void setAnotherName(String anotherName) {

		this.anotherName = anotherName;

	}

	public Date getLoginTime() {

		return loginTime;

	}

	public void setLoginTime(Date loginTime) {

		this.loginTime = loginTime;

	}

	public String getDefaultPlaceCode() {
		return defaultPlaceCode;
	}

	public void setDefaultPlaceCode(String defaultPlaceCode) {
		this.defaultPlaceCode = defaultPlaceCode;
	}
	
}