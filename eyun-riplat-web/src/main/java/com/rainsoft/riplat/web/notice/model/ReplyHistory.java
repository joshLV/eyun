package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.Date;

import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DateUtils;

public class ReplyHistory extends IdEntity implements Serializable{
    
    private static final long serialVersionUID = 1782046536852373509L;
    
    private Date replyTime;//回复时间
    
    private String noticeId;//通知Id
    
    private String replyContent;//回复内容
    
    private String userId;//回复人信息
    
    private String userType;//回复人信息
    
    //vo 
    private String userName;//回复人的名称
    
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }    
    public Date getReplyTime() {
        return replyTime;
    }  
    public String getReplyTimeStr() {
       return  DateUtils.dateToString(replyTime, Constants.DATA_FORMAT_HHMMSS);
    }
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

}
