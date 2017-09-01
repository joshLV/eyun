package com.rainsoft.base.common.model;

/**
 * 
 * @Description:付款状态枚举
 * @author:王乾
 * @date:2016年5月12日下午1:35:04
 */
public enum PayStatusEnum {

	
	UNPAID("0", "未付款"),
	INVALID("3", "作废"),
	ALREADYAID("8", "已付款"),
	PAYFAIL("9", "付款失败");
	
	
	private String code;
	private String value;
	
	private PayStatusEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}
	
  /**
   * 
   * @Description:方法功能说明：根据Code取得状态值
   * @author:王乾
   * @date:2016年5月12日下午1:34:45
   * @param code
   * @return
   */
    public static String getValue(String code) {
        for (PayStatusEnum ms : PayStatusEnum.values()) {  
            if (ms.getCode().equals(code)) {  
                return ms.value;  
            }  
        }  
        return null;  
    }
    
   /**
    * 
    * @Description:方法功能说明：根据状态取得Code
    * @author:王乾
    * @date:2016年5月12日下午1:34:38
    * @param value
    * @return
    */
    public static String getCode(String value) {
        for (PayStatusEnum ms : PayStatusEnum.values()) {  
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