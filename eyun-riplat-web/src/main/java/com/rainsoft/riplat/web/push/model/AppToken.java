package com.rainsoft.riplat.web.push.model;

import java.io.Serializable;

import com.rainsoft.base.common.model.IdEntity;

public class AppToken  extends IdEntity implements Serializable {
	private static final long serialVersionUID = -1584485134246588576L;
	private String edaId;				//易达id
	private String mobileToken;			//手机令牌
	private String notifyPlatform;		//通知平台类型
	public String getEdaId() {
		return edaId;
	}
	public void setEdaId(String edaId) {
		this.edaId = edaId;
	}
	public String getMobileToken() {
		return mobileToken;
	}
	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
	public String getNotifyPlatform() {
		return notifyPlatform;
	}
	public void setNotifyPlatform(String notifyPlatform) {
		this.notifyPlatform = notifyPlatform;
	}
}
