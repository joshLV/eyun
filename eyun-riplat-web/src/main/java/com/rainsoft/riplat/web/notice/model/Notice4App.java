package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;

public class Notice4App implements Serializable {

    private static final long serialVersionUID = 6609370881358684159L;

    private Integer id;
    private String name;
    private String readStatus;
    private String sendTime;
    private String issueName;//发布人
    private String url;//提供给App端访问的url
    private String content;//内容
    private String attachName;//附件名
    private String attachPath;//附件的相对路径    
    private Integer noticeLevel = 0;//优先级
    
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getIssueName() {
		return issueName;
	}
	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getNoticeLevel() {
		return noticeLevel;
	}
	public void setNoticeLevel(Integer noticeLevel) {
		this.noticeLevel = noticeLevel;
	}
   
    
    
}
