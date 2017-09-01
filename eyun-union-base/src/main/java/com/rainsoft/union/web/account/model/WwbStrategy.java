package com.rainsoft.union.web.account.model;

import java.io.Serializable;

/**
 * 旺旺币优惠策略
 * Created by sun on 2016/9/29.
 */
public class WwbStrategy implements Serializable {
    private static final long serialVersionUID = -1000878264399473264L;

    private Integer id;      // 主键
    private String refCode;  // 区域编号
    private String refCodeName;//区域名称
    private int buyWwb;      // 购买的旺旺币
    private int presentWwb;  // 赠送的旺旺币
    private int useTimes;    // 购买限制次数
    private String startTime;// 优惠开始时间
    private String endTime;  // 优惠开始时间
    private int creator;     // 创建人
    private String status;   // 状态
    private String validStatus;//策略是否有效状态
    private int userId;     // 用户iD

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefCodeName() {
        return refCodeName;
    }

    public void setRefCodeName(String refCodeName) {
        this.refCodeName = refCodeName;
    }

    public int getBuyWwb() {
        return buyWwb;
    }

    public void setBuyWwb(int buyWwb) {
        this.buyWwb = buyWwb;
    }

    public int getPresentWwb() {
        return presentWwb;
    }

    public void setPresentWwb(int presentWwb) {
        this.presentWwb = presentWwb;
    }

    public int getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(int useTimes) {
        this.useTimes = useTimes;
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

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
