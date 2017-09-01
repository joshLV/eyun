package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.union.web.pubdata.model.PlaceEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ParamFilter extends IdEntity {

	private int placeId;
	private String placeCode; //场所编号
	private String placeName; //场所名称
	private String serialNum;  //设备序列号
	private Integer memberID;  //用户id
	private String memberName; //用户名
	//for place Sms recharge
	private String smsFeeOwnerFlag;     //场所短信付费方式 S:自费，Y：公司付费
	private Integer placeDeviceId;      //场所设备Id

	//for whiteList manage(白名单管理)
	private String mobile;  //手机号
	private int entityId;   //对象主键
	private String eyunId;  //易韵账号
	private String idCard;  //身份证号
	private String devMac;  //设备Mac地址

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}


	public String getEyunId() {
		return eyunId;
	}

	public void setEyunId(String eyunId) {
		this.eyunId = eyunId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	@Override
	public String getPlaceCode() {
		return placeCode;
	}

	@Override
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}

	public Integer getMemberID() {
		return memberID;
	}

	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	public String getDevMac() {
		return devMac;
	}

	public void setDevMac(String devMac) {
		this.devMac = devMac;
	}
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getSmsFeeOwnerFlag() {
		if(smsFeeOwnerFlag==null
				|| "".equals(smsFeeOwnerFlag.trim())){
			setSmsFeeOwnerFlag(PlaceEnum.SMSFEE_PLACE.getCode());//默认场所付费
		}
		return smsFeeOwnerFlag;
	}

	public void setSmsFeeOwnerFlag(String smsFeeOwnerFlag) {
		this.smsFeeOwnerFlag = smsFeeOwnerFlag;
	}

	public Integer getPlaceDeviceId() {
		return placeDeviceId;
	}

	public void setPlaceDeviceId(Integer placeDeviceId) {
		this.placeDeviceId = placeDeviceId;
	}

	//--------------------------------------------------------------------------
	// add by qianna
	//--------------------------------------------------------------------------
	public Map<String,Object> toMap(){
		Field[] fields = getClass().getDeclaredFields();
		Map<String, Object> map = new HashMap<String, Object>();
		if(fields != null && fields.length > 0 ){
			Field tempField = null; //每个属性对象
			String fieldName = "";//属性名称
			Object fieldVal = null;//属性值
			for( int i = 0 ; i < fields.length ; i++ ){
				tempField = fields[i];
				if(tempField != null){
					try {
						fieldVal = tempField.get(this);
						if(fieldVal != null && !"".equals(fieldVal) && !"null".equals(fieldVal)){
							map.put(tempField.getName(), fieldVal);
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return map;
	}


}
