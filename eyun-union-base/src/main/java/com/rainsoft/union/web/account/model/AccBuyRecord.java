package com.rainsoft.union.web.account.model;

import java.math.BigDecimal;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 账户充值信息
 * @author wangqian
 * 
 */
public class AccBuyRecord extends PersistenceCommon {
	
	private String uuId;     
	private int buyWWBiRecordId;
	private int userId;        //操作人，会员ID或managerID。支付渠道1-5之间的，则为managerID，大于5的为会员ID
	private String relatingId;   //凭据号。下线汇款，现场支付为对应的单据号。网上支付时为null，只有调用支付接口后才会有该号码
	private char payChannel;   ///*支付渠道.1:汇款；2：转账；3：现场支付；6、易宝支付；7、。。。 */
	private BigDecimal rmbFee;       //人民币支付金额
	private BigDecimal wwbiFee;      //购买旺旺币金额
	private String useType;
	private String payStatus;  //支付状态
	private String wwbQtyFee;  //旺旺币数量
	
	
	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	public int getBuyWWBiRecordId() {
		return buyWWBiRecordId;
	}
	public void setBuyWWBiRecordId(int buyWWBiRecordId) {
		this.buyWWBiRecordId = buyWWBiRecordId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRelatingId() {
		return relatingId;
	}
	public void setRelatingId(String relatingId) {
		this.relatingId = relatingId;
	}
	public char getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(char payChannel) {
		this.payChannel = payChannel;
	}
	
	public BigDecimal getRmbFee() {
		return rmbFee;
	}
	public void setRmbFee(BigDecimal rmbFee) {
		this.rmbFee = rmbFee;
	}
	public BigDecimal getWwbiFee() {
		return wwbiFee;
	}
	public void setWwbiFee(BigDecimal wwbiFee) {
		this.wwbiFee = wwbiFee;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getWwbQtyFee() {
		return wwbQtyFee;
	}
	public void setWwbQtyFee(String wwbQtyFee) {
		this.wwbQtyFee = wwbQtyFee;
	}

	

	
}
