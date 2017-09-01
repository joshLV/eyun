package com.rainsoft.base.common.model;

/**
 * 
 *
 * 类功能说明： 系统模块枚举
 *
 * @author sh_j_baoxu
 *
 * @date 2015年12月8日 下午7:40:10
 */
public enum SystemModuleEnum {

	INDEX("index", "首页"),
	SYSTEM("system", "系统设置"),
	ACCOUNT("account", "账户管理"),
	WWBACCOUNT("wwaccount", "旺旺币账户管理"),
	SMSMANAGE("smsmanage","短信管理"),
	SYSTEM_MANAGE("sysmanage", "系统管理"),
	AUTHMANAGE("authManage", "认证管理");/*四川自贡菜单*/
	
	private String code;
	private String value;
	
	private SystemModuleEnum(String code, String value) {
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
        for (SystemModuleEnum ms : SystemModuleEnum.values()) {  
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
        for (SystemModuleEnum ms : SystemModuleEnum.values()) {  
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
