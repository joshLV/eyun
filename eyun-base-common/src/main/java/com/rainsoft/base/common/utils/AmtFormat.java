package com.rainsoft.base.common.utils;

import java.math.BigDecimal;

public class AmtFormat {
	
	/**
	 * 针对一般币种的金额格式化
	 * */
	public static BigDecimal  commAmtFormat(BigDecimal amt){
		//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
		return amt.setScale(2, BigDecimal.ROUND_HALF_UP);

	}
	
	
    public static BigDecimal  commAmtFormat(String amt){
    	
    	BigDecimal amtBig = new BigDecimal(amt);
    	//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
		return amtBig.setScale(2, BigDecimal.ROUND_HALF_UP);

	}
    
}
