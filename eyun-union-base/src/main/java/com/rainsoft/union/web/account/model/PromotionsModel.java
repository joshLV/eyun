package com.rainsoft.union.web.account.model;

import java.io.Serializable;

/**
 * 优惠活动实体
 * Created by sun on 2016/9/21.
 */
public class PromotionsModel implements Serializable {
    private static final long serialVersionUID = -7491584963369269094L;
    private  Integer id;
    private String placeName;
    private String placeCode;
    private String serialNum;
    private String presentNum;
    private String presentTime;
    private String status;      //'状态Y:已到账 P:审核通过 N:未审核(未到帐)，0：未付款；3：作废；8：已付款(也表示赠送成功，旺旺币已添加到账号)；9：付款不成功'
    private Double wwbFee;
    private int placeDeviceId;  // 场所设备关联主键
    private int userId;         // session的会员ID 用于记录操作人

    private String startTime;
    private String endTime;

    private String remark;

    private String payChannel; //4:现金充值 9：赠送 这里的业务只涉及这两种类型

    private String channelName; // 支付方式名称 用于表格显示

    private Double rmbFee;     // 充值金额

    private String certificate; //凭证 证书
    public PromotionsModel() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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

    public String getPresentNum() {
        return presentNum;
    }

    public void setPresentNum(String presentNum) {
        this.presentNum = presentNum;
    }

    public String getPresentTime() {
        return presentTime;
    }

    public void setPresentTime(String presentTime) {
        this.presentTime = presentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getWwbFee() {
        return wwbFee;
    }

    public void setWwbFee(Double wwbFee) {
        this.wwbFee = wwbFee;
    }

    public int getPlaceDeviceId() {
        return placeDeviceId;
    }

    public void setPlaceDeviceId(int placeDeviceId) {
        this.placeDeviceId = placeDeviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Double getRmbFee() {
        return rmbFee;
    }

    public void setRmbFee(Double rmbFee) {
        this.rmbFee = rmbFee;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
