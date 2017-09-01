package com.rainsoft.base.common.model;

public enum Direction {

	DESC("DESC"),  //%xx%
	ASC("ASC");	//xx%

	private String op;

	Direction(String oper) {
		this.op = oper;
	}

	public String getOp() {
		return op;
	}
	
}
