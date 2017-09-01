package com.rainsoft.riplat.web.notice.model;

import com.rainsoft.base.common.model.IdEntity;

import java.io.Serializable;
import java.util.Date;

public class PlatformUser extends IdEntity implements Serializable {

    private static final long serialVersionUID = 3876085842837503829L;
    
    private String loginUsername;//登录名
    
    private String loginPassword;//密码
    
    private String userRealname;//用户名

    private int creator;  //创建人 add by qianna
    
    private Date createTime;//创建时间

    private int updator;  //更新人 add by qianna
    private Date updateTime;//修改时间
    
    private String status;//状态 0：禁用，1：启用（默认）,3:删除
    
    private String platformId;//平台ID    
    
    private String platformName;//平台名称
    
    private String parentUserId;//父账号ID
    
    private String edaId;//易达用户名
    
    //vo
    
    private String oldPlatformId;//旧平台Id
    
    private String operate;//当前同步的操作 0:
    
    private String areaIds;//管理的区域ids


    
	public String getEdaId() {
        return edaId;
    }

    public void setEdaId(String edaId) {
        this.edaId = edaId;
    }

    public String getOldPlatformId() {
        return oldPlatformId;
    }

    public void setOldPlatformId(String oldPlatformId) {
        this.oldPlatformId = oldPlatformId;
    }

    public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}

	public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
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

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }
}
