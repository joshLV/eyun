package com.rainsoft.riplat.web.systemManage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 场所基础资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
public class PlaceData extends PersistenceCommon {
	/***** 场所资料的对应实体 *****/
	private String placeCode; // 场所编码
	private String placeName; // 场所名称
	private String placeTypeName; // 场所类型名称
	private String contact; // 联系人
	private String tel; // 固定电话
	private String mobile; // 手机号码
	private String email; // 电子邮箱
	private String addr; // 联系地址
	private int areaId; // 区域编码
	private String areaName; // 区域全名称 格式：例如：贵州省-铜仁市-江口县
	private Integer userId; // 场所会员ID

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPlaceTypeName() {
		return placeTypeName;
	}

	public void setPlaceTypeName(String placeTypeName) {
		this.placeTypeName = placeTypeName;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

}
