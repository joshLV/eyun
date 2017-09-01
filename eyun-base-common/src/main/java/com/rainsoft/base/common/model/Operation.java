package com.rainsoft.base.common.model;

/**
 * 字段选择条件
 * @author admin
 *
 */
public enum Operation {

	ALIKE("LIKE"),  //%xx%
	SLIKE("LIKE"),	//xx%
	ELIKE("LIKE"),	//%xx
	LT("<"),
	GT(">"),
	LE("<="),
	GE(">="),
	EQ("="),
	IEQ("="),
	NE("<>");

	private String op;

	Operation(String oper) {
		this.op = oper;
	}

	public String getOp() {
		return op;
	}
	
}