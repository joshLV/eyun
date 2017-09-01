package com.rainsoft.base.common.model;

import java.util.Date;

public enum FieldType {

    I(Integer.class.getSimpleName()),
    F(Float.class.getSimpleName()),
    N(Double.class.getSimpleName()),
    L(Long.class.getSimpleName()),
    S(String.class.getSimpleName()),
    D(Date.class.getSimpleName()),
    B(Boolean.class.getSimpleName());

	private String type;

	FieldType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}
