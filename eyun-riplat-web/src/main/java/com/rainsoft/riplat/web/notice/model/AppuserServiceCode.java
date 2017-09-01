package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;

/**
 * 易达与场所绑定关系
 * @author admin
 *
 */
public class AppuserServiceCode implements Serializable {

	private static final long serialVersionUID = 748601129164047323L;

	private String serviceCode;// 场所Id
	
	private String edaId;// 易达用户Id
	
	private String bindStatus;//绑定状态

	
	public String getEdaId() {
		return edaId;
	}

	public void setEdaId(String edaId) {
		this.edaId = edaId;
	}

	public String getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
