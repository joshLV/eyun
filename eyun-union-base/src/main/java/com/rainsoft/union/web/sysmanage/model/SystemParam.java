package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 系统参数
 * 
 */
public class SystemParam extends PersistenceCommon {

	private Integer placeId;				/**场所id*/
	private String placeCode;				/**场所编号*/
	private String placeName;				/**场所名称*/
	private String CtrlCode;				/**控制码*/
	private String protocol;				/**协议*/
	private String discountInfo;			/**打折信息*/
	
	//Vo
	private Integer memberId;
	private String accountResource;

	

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getAccountResource() {
		return accountResource;
	}

	public void setAccountResource(String accountResource) {
		this.accountResource = accountResource;
	}
	
	public String getPlaceCode() {
		return placeCode;
	}
	
	
	public Integer getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
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
	public String getCtrlCode() {
		return CtrlCode;
	}
	public void setCtrlCode(String ctrlCode) {
		CtrlCode = ctrlCode;
	}
	
	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getDiscountInfo() {
		return discountInfo;
	}
	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}
	

	/**
	 * @Description: 
	 * 将控制码转为对应的文字，N：不开启加入会员功能，Y：开启加入会员功能
	 * @param
	 */
	public String getIfJoinMem() {
		if(this.CtrlCode == null) {
			return null;
		}
		if("Y".equals(this.CtrlCode.substring(0, 1))) {
			return "是";
		} else {
			return "否";
		}
	}
	/**
	 * 
	 * @Description: 
	 * 将控制码转为对应的文字，N：不开启进店提醒功能，
	 * 1：开启进店提醒重点关注访客，2：表示开启进店提醒关注访客，3：开启进店提醒普通访客
	 * @param @return   
	 * @return String  
	 */
	public String getIfRemind() {
		if(this.CtrlCode == null) {
			return null;
		}
		if("1".equals(this.CtrlCode.substring(4, 5))) {
			return "重点关注";
		} else if("2".equals(this.CtrlCode.substring(4, 5))) {
			return "关注";
		} else if("3".equals(this.CtrlCode.substring(4, 5))) {
			return "普通";
		} else {
			return "否";
		}
	}
}
