package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 账户管理实体类
 * @author ww
 *
 * 2015年12月07日
 */
public class AccountManage extends PersistenceCommon{
	private String loginName;	//登录名
	private String placeid;	//场所名
	private String deviceid;;	//设备编号
	private String registerDate;//注册时间
	private Integer result = -1;         //存储过程返回结果，解绑用户状态，0 解绑成功，-1，解绑失败
//	private String memberid;
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 用户别名
	 */
	private String nickName;
	/**
	 * 登陆密码，加密
	 */
	private String currentPassword;
	/**
	 * 登陆账号，可以是用户名、用户id
	 * 	 */
	private String currentMemberId;
	
	
	/**
	 * 设置支付密码，加密
	 */
	private String payPassword;
	
	/**
	 * 当前支付密码，加密
	 */
	private String currentPwd;
	

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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public AccountManage(){}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
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

}
