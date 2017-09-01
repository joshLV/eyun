package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 账号管理实体类
 *
 */
public class AccountManage extends PersistenceCommon {
	
	
	private String loginName;	//登录名
	private String placeid;		//场所id
	private String placeCode;	//场所code
	private String deviceid;;	//设备编号
	private Integer userId;	//账户Id
	private String nickName;	//用户别名
	private String currentMemberId;//登陆账号，可以是用户名、账户Id
	private String currentPassword;//登陆密码，加密
	private String payPassword;	//设置支付密码，加密
	private String currentPwd;	//当前支付密码，加密
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getCurrentPwd() {
		return currentPwd;
	}

	public void setCurrentPwd(String currentPwd) {
		this.currentPwd = currentPwd;
	}

	public AccountManage(){}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCurrentMemberId() {
		return currentMemberId;
	}
	public void setCurrentMemberId(String currentMemberId) {
		this.currentMemberId = currentMemberId;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPlaceid() {
		return placeid;
	}

	public void setPlaceid(String placeid) {
		this.placeid = placeid;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeCode == null) ? 0 : placeCode.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountManage other = (AccountManage) obj;
		if (placeCode == null) {
			if (other.placeCode != null)
				return false;
		} else if (!placeCode.equals(other.placeCode))
			return false;
		return true;
	}
}
