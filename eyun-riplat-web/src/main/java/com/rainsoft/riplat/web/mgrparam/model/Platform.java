package com.rainsoft.riplat.web.mgrparam.model;

import java.io.Serializable;

import com.rainsoft.base.common.model.IdEntity;

public class Platform extends IdEntity implements Serializable {
   
    private static final long serialVersionUID = -9070985343492044971L;

    private String platformId;//平台ID    
    
    private String platformName;//平台名称
    
    private String platformIP;//平台Ip
    
    private String platformType;//平台类型
    
    private String activeable;//能否被激活使用：0：可以被激活；1：已经激活使用
    
    private String platformUserName;//用户名称
    
    private String platformUserPhone;//用户电话
    
    private String platformUserAddress;//用户地址
    
    //vo
    private String activeableName;//激活状态
    
    private String platformTypeName;//平台类型名称
    
    public String getActiveableName() {
        return activeableName;
    }

    public void setActiveableName(String activeableName) {
        this.activeableName = activeableName;
    }

    public String getPlatformTypeName() {
        return platformTypeName;
    }

    public void setPlatformTypeName(String platformTypeName) {
        this.platformTypeName = platformTypeName;
    }

    public String getActiveable() {
        return activeable;
    }

    public void setActiveable(String activeable) {
        this.activeable = activeable;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformIP() {
        return platformIP;
    }

    public void setPlatformIP(String platformIP) {
        this.platformIP = platformIP;
    }

	public String getPlatformUserName() {
		return platformUserName;
	}

	public void setPlatformUserName(String platformUserName) {
		this.platformUserName = platformUserName;
	}

	public String getPlatformUserPhone() {
		return platformUserPhone;
	}

	public void setPlatformUserPhone(String platformUserPhone) {
		this.platformUserPhone = platformUserPhone;
	}

	public String getPlatformUserAddress() {
		return platformUserAddress;
	}

	public void setPlatformUserAddress(String platformUserAddress) {
		this.platformUserAddress = platformUserAddress;
	}
    
    
}
