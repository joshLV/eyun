package com.rainsoft.union.web.businessmanage.model;

import java.io.Serializable;

public class DeviceStatus implements Serializable {

    /**
     * 计费中心状态
     */
    private static final long serialVersionUID = -6295966446788275314L;
    private String serialNumber;//设备序列号
    private String serviceCode;//场所Id
    private String captureTime;//上传时间
    private String diskCapacity;//硬盘空间
    private String diskUsedCapacity;//硬盘已用空间
    private String diskFreeCapacity;//硬盘空闲空间
    private String cpuUtilization;//CPU使用率
    private String totalMemory;//内存总空间
    private String usedMemory;//内存已用空间
    private String freeMemory;//内存空闲空间
    private String billingServiceStatus;//计费服务状态 0:异常 1:正常（目前始终传1）
    private String backupServiceStatus;//备份服务状态0:异常 1:正常
    private String probeCommunicationStatus;//探针通讯状态 0:异常 1:正常

    public DeviceStatus() {

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

    public String getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(String diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    public String getDiskUsedCapacity() {
        return diskUsedCapacity;
    }

    public void setDiskUsedCapacity(String diskUsedCapacity) {
        this.diskUsedCapacity = diskUsedCapacity;
    }

    public String getDiskFreeCapacity() {
        return diskFreeCapacity;
    }

    public void setDiskFreeCapacity(String diskFreeCapacity) {
        this.diskFreeCapacity = diskFreeCapacity;
    }

    public String getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(String cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(String usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory;
    }

    public String getBillingServiceStatus() {
        return billingServiceStatus;
    }

    public void setBillingServiceStatus(String billingServiceStatus) {
        this.billingServiceStatus = billingServiceStatus;
    }

    public String getBackupServiceStatus() {
        return backupServiceStatus;
    }

    public void setBackupServiceStatus(String backupServiceStatus) {
        this.backupServiceStatus = backupServiceStatus;
    }

    public String getProbeCommunicationStatus() {
        return probeCommunicationStatus;
    }

    public void setProbeCommunicationStatus(String probeCommunicationStatus) {
        this.probeCommunicationStatus = probeCommunicationStatus;
    }


}
