package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.Date;

import com.rainsoft.base.common.model.IdEntity;

public class AccpeteruserFeedback extends IdEntity implements Serializable {

    private static final long serialVersionUID = 2818223961718650897L;

    private String noticeId;
    
    private String noticeUnitId;
    
    private int status;
    
    private String appuserId;
    
    private Date accpeterTime;
    
    private Date readTime;
    
    private Date feedbackTime;
    
    private String feedbackContent;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppuserId() {
        return appuserId;
    }

    public void setAppuserId(String appuserId) {
        this.appuserId = appuserId;
    }

    public Date getAccpeterTime() {
        return accpeterTime;
    }

    public void setAccpeterTime(Date accpeterTime) {
        this.accpeterTime = accpeterTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
    
}
