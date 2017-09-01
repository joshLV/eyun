package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;

import com.rainsoft.base.common.model.IdEntity;

public class Member extends IdEntity implements Serializable{    
    
    private static final long serialVersionUID = -5801621576937147015L;

    private String memberId;
    
    private String memberName;
    
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    
}
