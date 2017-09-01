
package com.rainsoft.union.web.account.model;

import java.math.BigDecimal;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 账户信息
 * @author wangqian
 */
public class AccAccount extends PersistenceCommon{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1981264964508171260L;
	private String uuId;
	private Integer useWWBiRecordID;
	private Integer buyWWBiRecordId;//旺旺币充值记录ID
	private int userId;//会员ID
	private String placeName; // 场所名称
	private BigDecimal accountBal;//账户币余额
	private BigDecimal totalbuywwbifee;//账户总充值金额
	private BigDecimal totalusewwbifee;//账户总消费金额
	private BigDecimal rmbFee;  //人民币支付金额
	private BigDecimal wwbiFee; // 购买旺旺币金额
	private String useType;	//使用类型
	private String payStatus; //支付状态
	private String relatingId; //凭据号, 可以为null
	
	public String getRelatingId() {
		return relatingId;
	}
	public void setRelatingId(String relatingId) {
		this.relatingId = relatingId;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public BigDecimal getTotalbuywwbifee() {
		return totalbuywwbifee;
	}
	public void setTotalbuywwbifee(BigDecimal totalbuywwbifee) {
		this.totalbuywwbifee = totalbuywwbifee;
	}
	public BigDecimal getTotalusewwbifee() {
		return totalusewwbifee;
	}
	public void setTotalusewwbifee(BigDecimal totalusewwbifee) {
		this.totalusewwbifee = totalusewwbifee;
	}
	public Integer getBuyWWBiRecordId() {
		return buyWWBiRecordId;
	}
	public void setBuyWWBiRecordId(Integer buyWWBiRecordId) {
		this.buyWWBiRecordId = buyWWBiRecordId;
	}
	public Integer getUseWWBiRecordID() {
		return useWWBiRecordID;
	}
	public void setUseWWBiRecordID(Integer useWWBiRecordID) {
		this.useWWBiRecordID = useWWBiRecordID;
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
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
	
}
