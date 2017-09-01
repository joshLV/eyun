package com.rainsoft.union.web.pubdata.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 设备基础资料
 *
 * @author 13646223842@163.com
 * @since 1.0.0 2016年5月30日 14:24:01
 */
public class DeviceData extends PersistenceCommon {
    private String hardwareID;          // 产品ID
    private int placeID;                // 场所ID
    private String placeName;           // 场所名称
    private Integer userId;             // 会员ID
    private String registerTime;        // 注册时间
    private String validTime;           // 有效时间
    private String serial_num;          // 设备序列号

    public String getSerial_num() {
        return serial_num;
    }

    public void setSerial_num(String serial_num) {
        this.serial_num = serial_num;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getHardwareID() {
        return hardwareID;
    }

    public void setHardwareID(String hardwareID) {
        this.hardwareID = hardwareID;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
