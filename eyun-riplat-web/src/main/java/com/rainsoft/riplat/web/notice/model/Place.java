package com.rainsoft.riplat.web.notice.model;

import com.rainsoft.base.common.model.IdEntity;

import java.io.Serializable;
import java.util.Date;

public class Place extends IdEntity implements Serializable {

	private static final long serialVersionUID = 5359409551098806862L;

	private String placeCode;
	private String placeName;
	private String contact;
	private String tel;
	private String mobile;
	private String email;
	private String addr;
	private Integer areaId;//区域
	private String areaName;
	private Integer placeType;//场所类型
	private Integer memberId;
	private String remarks;
	private Integer status = 8;
	private String longitude;
	private String latitude;
	private String ctrlCode;
	private Integer ifallowVip;
	private String businessFlag;
	private Date createTime;
	private String zip;
	private Integer regplaceId;	
	
	//vo
	private String userId;
	private String userName;//用户名称
	private String groupId;//某组中是否存在该场所
	private Integer isSelected;//是否已选中	
	
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
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getPlaceType() {
		return placeType;
	}

	public void setPlaceType(Integer placeType) {
		this.placeType = placeType;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCtrlCode() {
		return ctrlCode;
	}

	public void setCtrlCode(String ctrlCode) {
		this.ctrlCode = ctrlCode;
	}

	public Integer getIfallowVip() {
		return ifallowVip;
	}

	public void setIfallowVip(Integer ifallowVip) {
		this.ifallowVip = ifallowVip;
	}

	public String getBusinessFlag() {
		return businessFlag;
	}

	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getRegplaceId() {
		return regplaceId;
	}

	public void setRegplaceId(Integer regplaceId) {
		this.regplaceId = regplaceId;
	}


}
