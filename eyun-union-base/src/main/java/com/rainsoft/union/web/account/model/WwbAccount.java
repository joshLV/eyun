package com.rainsoft.union.web.account.model;

import java.math.BigDecimal;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 旺旺币基本账户信息封装类
 * @author huxiaolong
 */
public class WwbAccount extends PersistenceCommon{
	/**
	 * 对象序列化
	 */
	private static final long serialVersionUID = 3475706046560756950L;
	
	private Integer userId;					//会员id
	private BigDecimal totalBuyWwb;			//旺旺币总充值数
	private BigDecimal totalUseWwb;			//旺旺币总使用数
	private BigDecimal totalGiveWwb;		//赠送旺旺币总数
	private BigDecimal totalUseGiveWwb;		//赠送旺旺币使用数
	private String status;					//状态
	private String totalPoint;				//总积分
	private String totalUsePoint;			//总使用积分
	private BigDecimal balance;				//余额
	private String queryType;				//查询类型 1：充值， 2：消费
	private String useType;					//使用类型
	private Integer placeDeviceId;			//场所id
	private String remark;					//备注
	private String placeCode;				//场所编号
	private String serialNum;				//序列号
	private String orderNum;				//订单号
	private String startTime;				//开始时间
	private String endTime;					//结束时间
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public BigDecimal getTotalBuyWwb() {
		return totalBuyWwb;
	}
	public void setTotalBuyWwb(BigDecimal totalBuyWwb) {
		this.totalBuyWwb = totalBuyWwb;
	}
	public BigDecimal getTotalUseWwb() {
		return totalUseWwb;
	}
	public void setTotalUseWwb(BigDecimal totalUseWwb) {
		this.totalUseWwb = totalUseWwb;
	}
	public BigDecimal getTotalGiveWwb() {
		return totalGiveWwb;
	}
	public void setTotalGiveWwb(BigDecimal totalGiveWwb) {
		this.totalGiveWwb = totalGiveWwb;
	}
	public BigDecimal getTotalUseGiveWwb() {
		return totalUseGiveWwb;
	}
	public void setTotalUseGiveWwb(BigDecimal totalUseGiveWwb) {
		this.totalUseGiveWwb = totalUseGiveWwb;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getTotalUsePoint() {
		return totalUsePoint;
	}
	public void setTotalUsePoint(String totalUsePoint) {
		this.totalUsePoint = totalUsePoint;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public Integer getPlaceDeviceId() {
		return placeDeviceId;
	}
	public void setPlaceDeviceId(Integer placeDeviceId) {
		this.placeDeviceId = placeDeviceId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
