package com.rainsoft.union.web.account.model;

import com.rainsoft.base.common.model.PersistenceCommon;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 旺旺币购买数据封装类
 * @author huxiaolong
 */
public class WwbBuyRecord extends PersistenceCommon{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2549313702044516508L;
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private Integer userId;					//会员id
	private Integer placeDeviceId;			//场所id
	private BigDecimal rMBFee;				//消费人民币数额
	private BigDecimal wWBFee;				//充值旺旺币数额
	private BigDecimal giveWwbFee;			//赠送旺旺币数额
	private String remark;				//记录
	private String status;				//状态
	private Integer optor;				//操作人id
	private String optorName;			//操作人姓名
	private Date optTime;				//操作时间
	private String relatingId;			//订单编号
	private Integer totalRecord;		//总记录数
	private String payChannel;//支付方式
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPlaceDeviceId() {
		return placeDeviceId;
	}
	public void setPlaceDeviceId(Integer placeDeviceId) {
		this.placeDeviceId = placeDeviceId;
	}
	public BigDecimal getRMBFee() {
		return rMBFee;
	}
	public void setRMBFee(BigDecimal rMBFee) {
		this.rMBFee = rMBFee;
	}
	public BigDecimal getWWBFee() {
		return wWBFee;
	}
	public void setWWBFee(BigDecimal wWBFee) {
		this.wWBFee = wWBFee;
	}
	public BigDecimal getGiveWwbFee() {
		return giveWwbFee;
	}
	public void setGiveWwbFee(BigDecimal giveWwbFee) {
		this.giveWwbFee = giveWwbFee;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOptor() {
		return optor;
	}
	public void setOptor(Integer optor) {
		this.optor = optor;
	}
	public String getOptorName() {
		return optorName;
	}
	public void setOptorName(String optorName) {
		this.optorName = optorName;
	}
	public Date getOptTime() {
		return optTime;
	}
	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}
	public String getRelatingId() {
		return relatingId;
	}
	public void setRelatingId(String relatingId) {
		this.relatingId = relatingId;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public String getOptTimeStr() {
		return sdf.format(optTime);
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
}
