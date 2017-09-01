package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 设备基础资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015年12月3日 12:46:51
 */
public class DeviceData extends PersistenceCommon {
	/** 产品ID **/
	private String hardwareID;
	/** 场所ID **/
	private int placeID;
	/** 场所名称 **/
	private String placeName;

	/** 会员ID **/
	private Integer userId;
	/** 设备注册时间 **/
	private String registerTime;

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getHardwareID() {
		return hardwareID;
	}

	public void setHardwareID(String hardwareID) {
		this.hardwareID = hardwareID;
	}

	public int getPlaceID() {
		return placeID;
	}

	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
