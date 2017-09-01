package com.rainsoft.union.web.account.model;

import com.rainsoft.base.common.model.PersistenceCommon;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 旺旺币消费数据封装类
 * @author huxiaolong
 */
public class WwbUseRecord extends PersistenceCommon{
	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -8687682085546338231L;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String placeCode;				//场所编号
	private String serialNum;				//序列号
	private String orderNum;				//订单号
	private String name;					//消费人姓名
	private String certificateNum;			//消费人证件号
	private String cardNum;					//消费人卡号
	private String cardType;				//消费人卡类型 消费卡类型:1：临时卡；2：'固定时长'; 3：'固定时段';4：'有限制固定时长';5：'按金额计费';6：'按时间计费';7：'包时间';8：'连锁计费';
	private BigDecimal money;				//消费金额
	private Date startTime;					//消费开始时间
	private Date endTime;					//消费结束时间
	private BigDecimal orderMoneyCount;		//订单总消费金额
	private Date orderStartTime;			//订单开始时间
	private Date orderEndTime;				//订单结束时间
	private Integer totalRecord;			//总记录数
	private BigDecimal balance;				//余额
	private Integer placeDeviceId;			//场所设备id
	
	public Integer getPlaceDeviceId() {
		return placeDeviceId;
	}
	public void setPlaceDeviceId(Integer placeDeviceId) {
		this.placeDeviceId = placeDeviceId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getOrderMoneyCount() {
		return orderMoneyCount;
	}
	public void setOrderMoneyCount(BigDecimal orderMoneyCount) {
		this.orderMoneyCount = orderMoneyCount;
	}
	public Date getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public Date getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public String getOrderStartTimeStr() {
		return null == orderStartTime ? null : sdf.format(orderStartTime);
	}
	
	public String getOrderEndTimeStr() {
		return null == orderEndTime ? null : sdf.format(orderEndTime);
	}
	
	public String getStartTimeStr() {
		return null == startTime ? null : sdf.format(startTime);
	}
	
	public String getEndTimeStr() {
		return null == startTime ? null : sdf.format(endTime);
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
