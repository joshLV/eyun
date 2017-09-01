package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.Date;

import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.base.common.utils.DateUtils;

public class AccpeterUser extends IdEntity implements Serializable {

    private static final long serialVersionUID = 6222363225298271715L;

    private String appuserId;
    private String appuserName;
    private String noticeId;
    private String noticeUnitId;
    private String status;
    private String statusName;
    private Date readTime;
    
    public String getReadTimeSDF(){
        return DateUtils.dateToString(readTime, "yyyy-MM-dd HH:mm:ss");
    }    

    public String getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(String appuserId) {
        this.appuserId = appuserId;
    }

    public String getAppuserName() {
        return appuserName;
    }

    public void setAppuserName(String appuserName) {
        this.appuserName = appuserName;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeUnitId() {
        return noticeUnitId;
    }

    public void setNoticeUnitId(String noticeUnitId) {
        this.noticeUnitId = noticeUnitId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }


}
