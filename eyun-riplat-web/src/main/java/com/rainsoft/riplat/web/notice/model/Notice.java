package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.List;

import com.rainsoft.base.common.model.IdEntity;

public class Notice extends IdEntity implements Serializable {

    private static final long serialVersionUID = 6609370881358684159L;

    private String name;
    private String content;
    private Integer type = 0;
    private Integer status = 0;
    private String statusName;
    private String sendTime;
    private String quartzSendTime;
    private Boolean isDelete = false;
    private String attachment;
    private String createBy;//对应user.id
    private String createTime;
    private String updateBy;
    private String updateTime;
    private Integer noticeLevel = 0;
    private String levelName;
    private Integer accpeterType = 0;
    private List<Unit> units;
    private List<String> unitCodes;
    private String attachPath;//文件保存在服务器的路径
    //vo
    private String item;//0：发送 1：保存
    
    private String issueName;//发布人
    private String url;//提供给App端访问的url
    
    private Integer sendTarget = 0;
    
    
    public Integer getSendTarget() {
		return sendTarget;
	}

	public void setSendTarget(Integer sendTarget) {
		this.sendTarget = sendTarget;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getQuartzSendTime() {
        return quartzSendTime;
    }

    public void setQuartzSendTime(String quartzSendTime) {
        this.quartzSendTime = quartzSendTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(Integer noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getAccpeterType() {
        return accpeterType;
    }

    public void setAccpeterType(Integer accpeterType) {
        this.accpeterType = accpeterType;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<String> getUnitCodes() {
        return unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }
    
}
