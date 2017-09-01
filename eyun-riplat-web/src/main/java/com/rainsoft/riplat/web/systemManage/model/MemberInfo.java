package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 
 * 个人信息实体
 *
 */
public class MemberInfo extends PersistenceCommon {
	
	/**修改会员当前密码 参数 */
	private Integer userId;			//用户编号
	private String currentPassword;	//用户当前密码，加密
	private String newUserPassword;	// 设置密码，加密
	/** 会员信息  参数  */
	private String memberRealName;		//会员真实名称		
	private String tel;				//联系电话
	private String mobile;			//联系手机	
	private String email;			//电子邮箱	
	private String areaID;			//所在地区
	private String addr;			//地址	
	private String nickName;		//用户别名
	private String areaName;            // 区域全名称 格式：例如：贵州省-铜仁市-江口县
	

	public String getMemberRealName() {
		return memberRealName;
	}
	public void setMemberRealName(String memberRealName) {
		this.memberRealName = memberRealName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAreaID() {
		return areaID;
	}
	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewUserPassword() {
		return newUserPassword;
	}
	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
