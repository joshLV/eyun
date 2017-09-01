package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.Date;

import com.rainsoft.base.common.model.IdEntity;

/**
 * 易达APP会员表
 * @author admin
 *
 */
public class EdaAppMembers extends IdEntity implements Serializable {

	private static final long serialVersionUID = 3672762473614482376L;
	
	private String edaId;//易达账号
    private String userName;//用户名
    private String mobile;//手机号码
    private String nationality;//手机地区编号 用于区分手机号码所属地区
    private String loginPassword;//登录密码（MD5加密）
    private String deviceName;//设备名称
    private String appId;//设备号
    private String userMac;//设备MAC
    private String mobileModel;//手机型号
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private String status;//状态1:正常使用（用户可以正常登录）  0:禁止使用（用户无法正常登录）
    private String appVersion;//版本号
    private String operate;//操作类型 0：新增或修改 1：删除
    private String areaId;//区域id
    private String areaName;//区域名称
    private Integer registerType = 2;//1:钱易版（企业）,2:网吧, 3:公安
    
    
    //vo
    private String edaName;//易达
    
    private String userId;//创建人
    
    private String isSelected;//是否已选中
    
    private String groupId;//组Id
        
    public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getEdaName() {
        return edaName;
    }
    public void setEdaName(String edaName) {
        this.edaName = edaName;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getIsSelected() {
        return isSelected;
    }
    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
    public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getEdaId() {
		return edaId;
	}
	public void setEdaId(String edaId) {
		this.edaId = edaId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUserMac() {
		return userMac;
	}
	public void setUserMac(String userMac) {
		this.userMac = userMac;
	}
	public String getMobileModel() {
		return mobileModel;
	}
	public void setMobileModel(String mobileModel) {
		this.mobileModel = mobileModel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public Integer getRegisterType() {
		return registerType;
	}
	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}    
    
}
