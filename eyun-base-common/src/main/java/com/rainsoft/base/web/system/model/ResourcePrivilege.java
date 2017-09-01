package com.rainsoft.base.web.system.model;


public class ResourcePrivilege{
	private Integer id;				//资源id
	
	private String privilege;		//分配时该资源权限

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}	
}
