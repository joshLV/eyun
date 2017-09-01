package com.rainsoft.riplat.web.systemManage.controller;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.model.AccountManage;
import com.rainsoft.riplat.web.systemManage.service.IAccountManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller 
@RequestMapping("/systemManage/accountManage")
public class AccountManageController extends SpringBaseController<AccountManage, String>{
	private final String PREXIF = "/systemManage/account";
	@Resource
	private IAccountManageService accountManageService;
	
	@Override
	protected IMybatisBasePersitenceService<AccountManage, String> getBaseService() {
		return accountManageService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}
	/**
	 * 方法功能说明：根据会员ID查询管理的所属账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 账户管理实体类
	 */
	@RequestMapping(value = "/findListAccount", method = RequestMethod.POST)
	public void findListAccount(@ModelAttribute AccountManage accountManage) {
		accountManage.setUserId(SpringMvcUtil.getUserId());
		super.loadGrid(accountManage);
	}
	
	/**
	 * 方法功能说明：根据当前会员id解绑账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage userId会员id
	 * @return
	 */
	@RequestMapping(value = "/updRelieveById", method = RequestMethod.POST)
	public void updRelieveById(@ModelAttribute AccountManage accountManage) {
		Result result = new Result();
		accountManage.setId(SpringMvcUtil.getUserId());
		String msg = accountManageService.updateRelieveById(accountManage);
		if(Constants.RETURN_SUCCESS.equals(msg)){
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("解绑成功！");
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("解绑失败！");
		}
		super.responseAjaxData(result);
	}
	
	/**
	 * 方法功能说明：绑定账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 当前账号id userId
	 * @param 绑定账号id currentMemberId
	 * @param 绑定账号密码 currentPassword（MD5加密32位字符）
	 * @return
	 */
	@RequestMapping(value = "/callBindingAccount", method = RequestMethod.POST)
	public void callBindingAccount(@RequestParam("currentMemberId") String currentMemberId,@RequestParam("currentPassword") String currentPassword) {
		Result result = new Result();
		AccountManage accountManage = new AccountManage();
		accountManage.setUserId(SpringMvcUtil.getUserId());
		accountManage.setCurrentMemberId(currentMemberId);
		accountManage.setCurrentPassword(CommonUtil.getMd5(currentPassword));
		accountManage.setResult(-1);
		result = accountManageService.callBindingAccount(accountManage);
		super.responseAjaxData(result);
	}
	
	
	/**
	 * 方法功能说明：跳转设置用户别名页面
	 * @author sh_j_wangwen
	 * @data 2015年12月10号
	 * @return 
	 */
	@RequestMapping("/toSetNickNamePage")
	public String toSetNickNamePage() {
		return PREXIF + "SetNickName";
	}
	
	/**
	 * 方法功能说明：设置用户别名
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 
	 * @param nickName 用户别名
	 * @param userId 当前会员Id
	 * @return
	 */
	@RequestMapping(value = "/updateNickName", method = RequestMethod.POST)
	public void updateNickName(@RequestParam("nickName") String nickName) {
		Result result = new Result();
		AccountManage accountManage = new AccountManage();
		accountManage.setId(SpringMvcUtil.getUserId());
		accountManage.setUserId(SpringMvcUtil.getUserId());
		accountManage.setNickName(nickName);
		accountManage.setResult(-1);
		accountManage.setRemark("设置用户别名操作");
		result = accountManageService.updateNickName(accountManage);
		if (result.getStatus().equals(Constants.RETURN_SUCCESS)) {
			AuthenToken authenToken = (AuthenToken) getSession().getAttribute("AuthenToken");
			authenToken.setAnotherName(nickName);
			getSession().setAttribute("AuthenToken", authenToken);
		}
		super.responseAjaxData(result);
	}
	
	
	/**
	 * 方法功能说明：跳转密码设置页面
	 * @author sh_j_wangwen
	 * @data 2015年12月10号
	 * @return 
	 */
	@RequestMapping("/toSetpayPwd")
	public String toSetPayPwd() {
		return PREXIF + "SetpayPwd";
	}
	/**
	 * 方法功能说明：密码设置
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param payPassword 密码设置实体类
	 * @param userId 当前会员id
	 * @param payPassword 用户设置的支付密码
	 * @param currentPwd 用户当前的支付密码
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/payPwdSet", method = RequestMethod.POST)
	public void payPwdSet(@RequestParam("setBuyPassword") String setBuyPassword,@RequestParam("currentPwd") String currentPwd) {
		Result result = new Result();
		AccountManage accountManage = new AccountManage();
		accountManage.setUserId(SpringMvcUtil.getUserId());
		//当前用户支付密码
		accountManage.setCurrentPwd(currentPwd);
		//设置新的支付密码
		accountManage.setPayPassword(setBuyPassword);
		result = accountManageService.updatePayPwdSet(accountManage);
		super.responseAjaxData(result);
	}
	
	/**
	 * 方法功能说明： 查询支付密码
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param payPassword 密码设置实体类
	 * @param userId 当前会员id
	 * @return
	 */
	@RequestMapping(value = "/judgePayPwdSet", method = RequestMethod.POST)
	public void judgePayPwdSet() {
		Result result = new Result();
		AccountManage accountManage = new AccountManage();
		accountManage.setUserId(SpringMvcUtil.getUserId());
		result = accountManageService.findPayPwd(accountManage);
		super.responseAjaxData(result);
	}
}
