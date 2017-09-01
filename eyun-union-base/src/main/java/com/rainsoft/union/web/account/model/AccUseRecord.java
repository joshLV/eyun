package com.rainsoft.union.web.account.model;

import java.math.BigDecimal;

import com.rainsoft.base.common.model.PersistenceCommon;


/**
 * 账户支出记录表
 * @author huxiaolong
 */
public class AccUseRecord extends PersistenceCommon{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4084566186862470858L;
	private int useWWBiRecordID;//使用旺旺币记录id
	private int userId; // 会员ID;
	private int placeID;//场所id
	private String useType;//使用类型 1、购买短信；2：广告位；3：上机服务费用购买；4：上机服务赠送费用；5：购买进销存；6：购买云备份
	private BigDecimal useWWBiMoney;//使用旺旺币金额
	private String optTime;//系统时间
	private String remarks;//备注
	private String status;//[Y/N]有效/无效；
	private Integer totalRecord; // 总记录数
	private String placename;//场所名称
	private String membername;//会员账号不区分大小写，新建账号时，要将账号内容全部变成小写
	private String startTime;					//消费开始时间
	private String endTime;					//消费结束时间
	
	public int getUseWWBiRecordID() {
		return useWWBiRecordID;
	}
	public void setUseWWBiRecordID(int useWWBiRecordID) {
		this.useWWBiRecordID = useWWBiRecordID;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPlaceID() {
		return placeID;
	}
	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public BigDecimal getUseWWBiMoney() {
		return useWWBiMoney;
	}
	public void setUseWWBiMoney(BigDecimal useWWBiMoney) {
		this.useWWBiMoney = useWWBiMoney;
	}
	public String getOptTime() {
		return optTime;
	}
	public void setOptTime(String optTime) {
		if (optTime != null && optTime.length() > 10) {
			this.optTime = optTime.substring(0, optTime.lastIndexOf("."));
		} else {
			this.optTime = optTime;
		}
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
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
