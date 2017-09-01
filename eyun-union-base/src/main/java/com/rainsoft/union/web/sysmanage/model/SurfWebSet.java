package com.rainsoft.union.web.sysmanage.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 
 * 上网设置实体
 */
public class SurfWebSet extends PersistenceCommon {
	
	private Integer placeId;	//场所Id
	private String placeCode;	//场所编号
	private String placeName;	//场所名称
	private String placeCtrlCode;//场所控制码
	/** feeCtrlCode 每一位代表不同的控制含义，取值范围为【Y/N】。
	 * 第一位表示白名单用户是否无需审批自动上网；
	   第二位表示本场所的会员是否无需审批自动上网；
	   第三位表示本场所启用黑名单，即黑名单用户不能上网。后面的位预留**/
	private String feeCtrlCode;	//上网计费功能控制,保留暂未实现，预留字段	
	private String surfWay;		//上网方式
	private Integer freeTime;	//免费时长(分钟)
	private String freeHour;	//免费时长(时)
	private int singlePrice;	//单价,单位：元/小时
	private int minMoneyUnit;	//最小计费
	private int startMoney;		//起步价
	
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
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
	public String getPlaceCtrlCode() {
		return placeCtrlCode;
	}
	public void setPlaceCtrlCode(String placeCtrlCode) {
		this.placeCtrlCode = placeCtrlCode;
	}
	public String getFeeCtrlCode() {
		return feeCtrlCode;
	}
	public void setFeeCtrlCode(String feeCtrlCode) {
		this.feeCtrlCode = feeCtrlCode;
	}
	public Integer getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
		if (freeTime == 999999) {
			setFreeHour("不限");
		} else if (freeTime < 3600) {
			setFreeHour(freeTime / 60 + "分钟");
		} else {
			setFreeHour(freeTime / 3600 + "小时");
		}
	}
	public String getFreeHour() {
		return freeHour;
	}
	public void setFreeHour(String freeHour) {
		this.freeHour = freeHour;
	}
	public int getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(int singlePrice) {
		this.singlePrice = singlePrice;
	}
	public int getMinMoneyUnit() {
		return minMoneyUnit;
	}
	public void setMinMoneyUnit(int minMoneyUnit) {
		this.minMoneyUnit = minMoneyUnit;
	}
	public int getStartMoney() {
		return startMoney;
	}
	public void setStartMoney(int startMoney) {
		this.startMoney = startMoney;
	}
	
	/**
	 * 
	 * @Description: 
	 * 将控制码转为对应的文字，N：自动上网，Y:审批上网
	 * @param @return   
	 * @return String  
	 */
	public String getSurfWay() {
			if(this.feeCtrlCode == null) {
				return null;
			}
			if("N".equals(this.feeCtrlCode.substring(4, 5))) {
				return "自动上网";
			
			} else {
				return "审批上网";
			}
	}
}

	