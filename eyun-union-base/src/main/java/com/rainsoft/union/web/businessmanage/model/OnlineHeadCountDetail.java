package com.rainsoft.union.web.businessmanage.model;

import java.io.Serializable;

public class OnlineHeadCountDetail implements Serializable {

    /**
     * 当前在线人数
     */
    private static final long serialVersionUID = 1725136906615038981L;
    private String areaName;//区域名称
    private Integer clientCounts;//终端总数
    private Integer vipOnlineNumbers;//会员在线人数
    private Integer tempOnlineNumbers;//临时卡在线人数
    private Integer countNumbers;//在线总人数
    private String captureTime;//上传时间

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getClientCounts() {
        return clientCounts;
    }

    public void setClientCounts(Integer clientCounts) {
        this.clientCounts = clientCounts;
    }

    public Integer getVipOnlineNumbers() {
        return vipOnlineNumbers;
    }

    public void setVipOnlineNumbers(Integer vipOnlineNumbers) {
        this.vipOnlineNumbers = vipOnlineNumbers;
    }

    public Integer getTempOnlineNumbers() {
        return tempOnlineNumbers;
    }

    public void setTempOnlineNumbers(Integer tempOnlineNumbers) {
        this.tempOnlineNumbers = tempOnlineNumbers;
    }

    public Integer getCountNumbers() {
        return countNumbers;
    }

    public void setCountNumbers(Integer countNumbers) {
        this.countNumbers = countNumbers;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

}
