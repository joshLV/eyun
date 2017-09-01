package com.rainsoft.union.web.wxpay.service;

import javax.servlet.http.HttpServletResponse;

import com.rainsoft.union.web.wxpay.model.WXReqEntity;
import com.rainsoft.union.web.wxpay.model.WXResEntity;

public interface IWxCodePayService {
	public WXResEntity wxCodePay(WXReqEntity wxReqEntity);
	
	/**
	 * 微信生成二维码
	 * @param response
	 * @param codeUrl 二维码URL
	 */
	public void create2code(HttpServletResponse response, String codeUrl);
}
