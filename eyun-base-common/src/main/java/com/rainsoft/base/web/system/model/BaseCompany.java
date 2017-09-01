package com.rainsoft.base.web.system.model;

import com.rainsoft.base.common.model.IdEntity;

public class BaseCompany extends IdEntity {
	private String name;
	
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
