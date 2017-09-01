package com.rainsoft.base.web.system.model;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 功能说明：关联资源
 * 
 * @author admin
 * @created 2014年6月16日 上午6:13:46
 * @updated
 */
public class BaseRelatedResource extends PersistenceCommon {

	/** 关联id，（用户，资源） */
	private String relatedId;
	/** 角色id */
	private String roleId;
	/** 关联类型（角色，用户） */
	private Integer relatedType;

	public String getRelatedId() {

		return relatedId;

	}

	public void setRelatedId(String relatedId) {

		this.relatedId = relatedId;

	}

	public String getRoleId() {

		return roleId;

	}

	public void setRoleId(String roleId) {

		this.roleId = roleId;

	}

	public Integer getRelatedType() {

		return relatedType;

	}

	public void setRelatedType(Integer relatedType) {

		this.relatedType = relatedType;

	}

}