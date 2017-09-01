/**
 * @Description:短信使用记录实体类
 * @author:huxiaolong
 * @date:2016年4月18日下午12:58:37
 */
package com.rainsoft.union.web.smsmanage.model;

import java.math.BigDecimal;

import com.rainsoft.base.common.model.PersistenceCommon;

public class SmsManage extends PersistenceCommon implements Cloneable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7578494575004301472L;
	private Integer userId;	//用户id
	private Integer placeId;	//场所id
	private Integer smsRecordId; // 发送短信流水ID
	private String placeName; // 场所名称
	private String mobile; // 手机号码
	private Integer contentId; // 发送内容ID
	private String sendDate; // 发送时间
	private String status;	//状态
	private BigDecimal useMoney; //使用人民币金额
	private BigDecimal smsNum; // 短信统计值
	private String smsType; // 短信类型 01:国内 02:国际
	// 查询时间区间
	private String startTime; // 起始时间
	private String endTime; // 结束时间
	private Integer total; // 总记录数
	private Integer countSms; // 记录发送总数
	private String forCount;//标记参数
	private String useType; //1、购买短信；2：广告位；3：购买旺旺吧系统使用
	private String relatingId;//凭据号, 可以为null
	private BigDecimal accountBal;//账户余额
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}
	public Integer getSmsRecordId() {
		return smsRecordId;
	}
	public void setSmsRecordId(Integer smsRecordId) {
		this.smsRecordId = smsRecordId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(BigDecimal useWwbiMoney) {
		this.useMoney = useWwbiMoney;
	}
	public BigDecimal getSmsNum() {
		return smsNum;
	}
	public void setSmsNum(BigDecimal smsNum) {
		this.smsNum = smsNum;
	}
	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCountSms() {
		return countSms;
	}
	public void setCountSms(Integer countSms) {
		this.countSms = countSms;
	}
	public String getForCount() {
		return forCount;
	}
	public void setForCount(String forCount) {
		this.forCount = forCount;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String userType) {
		this.useType = userType;
	}
	public String getRelatingId() {
		return relatingId;
	}
	public void setRelatingId(String relatingId) {
		this.relatingId = relatingId;
	}
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
}
