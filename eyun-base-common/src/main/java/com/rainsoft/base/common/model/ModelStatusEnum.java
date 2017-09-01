package com.rainsoft.base.common.model;

/**
 * 
 *
 * 类功能说明： 实体状态枚举
 * NOT_SUBMIT：保存未提交
 * CHECK_PENDING：待审核
 * AUDITNOTPASSED：审核未通过
 * AUDITPASSED：审核通过
 * DISABLED：禁用
 * ENABLED：启用
 *
 * @author sh_j_baoxu
 *
 * @date 2015年12月8日 下午7:40:10
 */
public enum ModelStatusEnum {

	NOT_SUBMIT("0", "保存未提交"),
	CHECK_PENDING("1", "待审核"),
	AUDIT_NOT_PASSED("7", "审核未通过"),
	AUDIT_PASSED("8", "审核通过"),
	RELEASE("9", "已发布"),
	DISABLED("d", "禁用"),
	ENABLED("e", "启用"),
	SUCCESS("s", "成功"),
	FAIL("f", "失败"),
	DELETE("a", "删除"),
	NATURE("n", "正常"),
	CANCEL("c", "注销");
	
	
	private String code;
	private String value;
	
	private ModelStatusEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}
	
	/**
	 * 
	 * 方法功能说明：根据Code取得状态值
	 *
	 * @author sh_j_baoxu
	 * @data 2015年12月8日 下午7:55:38
	 * @param code
	 * @return
	 */
    public static String getValue(String code) {
        for (ModelStatusEnum ms : ModelStatusEnum.values()) {  
            if (ms.getCode().equals(code)) {  
                return ms.value;  
            }  
        }  
        return null;  
    }
    
	/**
	 * 
	 * 方法功能说明：根据状态取得Code
	 *
	 * @author sh_j_baoxu
	 * @data 2015年12月8日 下午7:55:38
	 * @param value
	 * @return
	 */
    public static String getCode(String value) {
        for (ModelStatusEnum ms : ModelStatusEnum.values()) {  
            if (ms.getValue().equals(value)) {  
                return ms.code;  
            }  
        }  
        return null;  
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
