package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;
import java.util.List;

import com.rainsoft.base.common.model.IdEntity;

public class Group extends IdEntity implements Serializable {

    private static final long serialVersionUID = 7030783108666892636L;

    private String name;//组名称

    private Integer type;//组类型 0：用户组；1：场所组
    
    private String platUserId;//平台用户Id

    private Integer status;//组状态 0：正常；1：禁用

    private String remarks;//备注
    
    private Integer item;
    
    //vo
    
    private List<Member> members;//成员
    
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    private String selectedIds;//选择多个  

	public String getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
    	
    	
        this.type = type;
    }

    public String getPlatUserId() {
		return platUserId;
	}

	public void setPlatUserId(String platUserId) {
		this.platUserId = platUserId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

}
