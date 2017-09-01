/**
 * @Description:
 * @author:王乾
 * @date:2016年4月18日下午12:58:54
 */
package com.rainsoft.union.web.account.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.union.web.account.model.AccAccount;
import com.rainsoft.union.web.wxpay.model.WXResEntity;


public interface IAccAccountService extends IMybatisBasePersitenceService<AccAccount, String>{
  
	
	/**
	 * 
	 * @Description:易宝支付
	 * @author:王乾
	 * @date:2016年5月27日上午11:34:45
	 * @param userkey
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String yfbPay(String userkey, Model model) throws Exception;

	
	/**
	 * 
	 * @Description:支付宝支付
	 * @author:王乾
	 * @date:2016年5月27日上午11:35:11
	 * @param userkey
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String zfbPay(String userkey, Model model) throws Exception;

	
	/**
	 * 
	 * @Description:易宝重新支付
	 * @author:王乾
	 * @date:2016年5月27日上午11:35:24
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String againYfbPay(HttpServletRequest request, Model model) throws Exception;

	
	/**
	 * 
	 * @Description:支付宝重新支付
	 * @author:王乾
	 * @date:2016年5月27日上午11:35:46
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String againZfbPay(HttpServletRequest request, Model model) throws Exception;

	
	/**
	 * 
	 * @Description:易宝回调
	 * @author:王乾
	 * @date:2016年5月27日上午11:36:02
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String yfbCallBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	
	/**
	 * 
	 * @Description:支付宝回调
	 * @author:王乾
	 * @date:2016年5月27日上午11:36:16
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String zfbCallBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 根據userid查询会员账户余额
	 * @param acc
	 * @return
	 */
	public AccAccount getAccountBal(AccAccount acc);
	
	/**
	 * 微信支付
	 */
	public String wxPay(String userkey, Model model) throws Exception;
	
	/**
	 * 微信重新支付
	 */
	public String againWxPay(HttpServletRequest request, Model model) throws Exception;
	
	/**
	 * 微信回调
	 */
	public void wxCallBack(Map<String, String> map,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 微信统一下单
	 */
	public WXResEntity wxSend(HttpServletRequest request, Model model);
	

}