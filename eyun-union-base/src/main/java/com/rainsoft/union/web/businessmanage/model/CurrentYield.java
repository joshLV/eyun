package com.rainsoft.union.web.businessmanage.model;

import com.rainsoft.base.common.utils.DecimalCalculate;
import com.rainsoft.base.common.utils.StringUtil;

import java.io.Serializable;

public class CurrentYield implements Serializable {

    /**
     * 当前收益
     */
    private static final long serialVersionUID = -1683661468507236645L;
    private String serialNumber;//序列号
    private String serviceCode;//场所id
    private String captureTime;//上传时间
    private String memberCard;//会员充值：包含本地充值和连锁充值
    private String temporaryMoney;//散户押金
    private String membersOnEarnings;//会员收益
    private String temporaryOnEarnings;//临时收益
    private String otherEarnings;//其他收益
    private String earningsCount;//总收益

    public CurrentYield(String captureTime, String  earningsCount, String memberCard, String membersOnEarnings, String otherEarnings, String serviceCode, String temporaryMoney, String temporaryOnEarnings) {
        this.captureTime = captureTime;
        this.earningsCount = earningsCount;
        this.memberCard = memberCard;
        this.membersOnEarnings = membersOnEarnings;
        this.otherEarnings = otherEarnings;
        this.serviceCode = serviceCode;
        this.temporaryMoney = temporaryMoney;
        this.temporaryOnEarnings = temporaryOnEarnings;
    }

    public CurrentYield() {

    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }
    /*------------------------------------------------*/
    public String getMemberCard() {
        if (StringUtil.isEmpty(memberCard)) {
            memberCard = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(memberCard),100d)+"";
    }

    public void setMemberCard(String memberCard) {
        this.memberCard = memberCard;
    }

    public String getTemporaryMoney() {
        if (StringUtil.isEmpty(temporaryMoney)) {
            temporaryMoney = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(temporaryMoney), 100d) + "";
    }

    public void setTemporaryMoney(String temporaryMoney) {
        this.temporaryMoney = temporaryMoney;
    }

    public String getMembersOnEarnings() {
        if (StringUtil.isEmpty(membersOnEarnings)) {
            membersOnEarnings = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(membersOnEarnings),100d)+"";
    }

    public void setMembersOnEarnings(String membersOnEarnings) {
        this.membersOnEarnings = membersOnEarnings;
    }

    public String getTemporaryOnEarnings() {
        if (StringUtil.isEmpty(temporaryOnEarnings)) {
            temporaryOnEarnings = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(temporaryOnEarnings),100d)+"";
    }

    public void setTemporaryOnEarnings(String temporaryOnEarnings) {
        this.temporaryOnEarnings = temporaryOnEarnings;
    }

    public String getOtherEarnings() {
        if (StringUtil.isEmpty(otherEarnings)) {
            otherEarnings = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(otherEarnings),100d)+"";
    }

    public void setOtherEarnings(String otherEarnings) {
        this.otherEarnings = otherEarnings;
    }

    public String getEarningsCount() {
        if (StringUtil.isEmpty(earningsCount)) {
            earningsCount = "0";
        }
        return DecimalCalculate.div(Double.parseDouble(earningsCount),100d)+"";
    }

    public void setEarningsCount(String earningsCount) {
        this.earningsCount = earningsCount;
    }
}
