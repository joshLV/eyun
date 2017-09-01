package com.rainsoft.riplat.web.notice.model;

import java.io.Serializable;

import com.rainsoft.base.common.model.IdEntity;

public class MemberGroup extends IdEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4178685959561269975L;
	

	private String  memberId;//组名称

    private Integer memberType;//组类型 0：用户组；1：场所组
    
    private String groupId;//组id
    
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

  

}
