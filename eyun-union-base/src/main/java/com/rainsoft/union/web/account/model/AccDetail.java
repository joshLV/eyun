package com.rainsoft.union.web.account.model;

import com.rainsoft.base.common.model.PersistenceCommon;


/**
 * 账户明细信息
 * @author Administrator
 */
public class AccDetail extends PersistenceCommon {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2317096417544958824L;
	private String rowNum;
	private int userId; // 会员ID
	private String buyWwbiRecordId;      //订单ID
	private String relatingId; // 凭据号。下线汇款，现场支付为对应的单据号。网上支付时为null，只有调用支付接口后才会有该号码
	private String payChannel; // /*支付渠道.1:汇款；2：转账；3：现场支付；6、易宝支付；7、。。。 */
	private double rmbFee; // 人民币支付金额
	private double wwbiFee; // 购买旺旺币金额
	private String remarks; // 备注，充值时的备注
	private String payStatus; // 0：未付款；3：作废；8：已付款；9：付款不成功；4：已付款未到账
	private String payStatusRemark; //payStatus文字描述
	private String useType; // 1、购买短信；2：广告位
	private Integer totalRecord; // 总记录数
	private String userName;	//用户名
	private String startTime;	//开始时间
	private String endTime;		//结束时间
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPayStatusRemark() {
		return payStatusRemark;
	}
	public void setPayStatusRemark(String payStatusRemark) {
		this.payStatusRemark = payStatusRemark;
	}
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	public double getRmbFee() {
		return rmbFee;
	}
	public void setRmbFee(double rmbFee) {
		this.rmbFee = rmbFee;
	}
	public double getWwbiFee() {
		return wwbiFee;
	}
	public void setWwbiFee(double wwbiFee) {
		this.wwbiFee = wwbiFee;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getBuyWwbiRecordId() {
		return buyWwbiRecordId;
	}
	public void setBuyWwbiRecordId(String buyWwbiRecordId) {
		this.buyWwbiRecordId = buyWwbiRecordId;
	}
	public String getRelatingId() {
		return relatingId;
	}
	public void setRelatingId(String relatingId) {
		this.relatingId = relatingId;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
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
