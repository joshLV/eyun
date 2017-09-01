package com.rainsoft.base.common.model;

public enum FilterType {
	
	F("F"), //Filter
	E("E"),	//Extre
	S("S");	//SORT
	

	private String filType;

	FilterType(String filType) {
		this.filType = filType;
	}

	public String getFilType() {
		return filType;
	}

}
