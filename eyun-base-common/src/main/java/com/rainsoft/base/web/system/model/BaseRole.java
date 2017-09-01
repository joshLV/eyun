package com.rainsoft.base.web.system.model;

import java.util.List;

import com.rainsoft.base.common.model.PersistenceCommon;

/**
 * 能说明：角色实体
 * 
 * @author admin
 * @created 2014年6月16日 上午5:59:47
 * @updated
 */
public class BaseRole extends PersistenceCommon {

	/** 角色名称 */
	private String name;

	/** 角色权限 */
	private String privilege;
	
	/**该角色下所有资源及资源权限list*/
	private List<ResourcePrivilege> list;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public List<ResourcePrivilege> getList() {
		return list;
	}

	public void setList(List<ResourcePrivilege> list) {
		this.list = list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseRole other = (BaseRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
}