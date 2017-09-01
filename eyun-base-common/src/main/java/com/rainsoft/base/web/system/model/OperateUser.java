package com.rainsoft.base.web.system.model;

import com.rainsoft.base.common.model.PersistenceCommon;


public class OperateUser extends PersistenceCommon {
	/** 用户登录名 */
	private String memberName;
	/** 登录密码 */
	private String memberPwd;
	/** 真实姓名*/
	private String memberRealName;
	/** 登录错误次数  默认为0, 当会员登录成功时将出错次数设为0 */
	private int loginErrorqty;
	/** 父账号id*/
	private Integer parentMemberId;
	/** 角色id*/
	private Integer roleID;
	/** 创建人名称*/
	private String creatorName;
	/** 更新人名称*/
	private String updatorName;
	/** 角色名称*/
	private String roleName;
	/** 用户所属区域id*/
	private String areaId;
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPwd() {
		return memberPwd;
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public String getMemberRealName() {
		return memberRealName;
	}
	public void setMemberRealName(String memberRealName) {
		this.memberRealName = memberRealName;
	}
	public int getLoginErrorqty() {
		return loginErrorqty;
	}
	public void setLoginErrorqty(int loginErrorqty) {
		this.loginErrorqty = loginErrorqty;
	}
	public Integer getParentMemberId() {
		return parentMemberId;
	}
	public void setParentMemberId(Integer parentMemberId) {
		this.parentMemberId = parentMemberId;
	}
	public Integer getRoleID() {
		return roleID;
	}
	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getUpdatorName() {
		return updatorName;
	}
	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
