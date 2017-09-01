package com.rainsoft.union.web.sysmanage.controller;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.AccountManage;
import com.rainsoft.union.web.sysmanage.service.IAccountManageService;

@Controller
@Log(clazzDesc="账号管理")
@RequestMapping("/systemManage/accountManage")
public class AccountManageController extends SpringBaseController<AccountManage, String>{
	private final String PREXIF = "/sysmanage/account";
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
	 * 获取账户列表
	 * @param accountManage 账户管理实体
	 */
	@RequestMapping(value = "/getGridList", method = RequestMethod.POST)
	public void findListAccount(@ModelAttribute AccountManage accountManage) {
		accountManage.setUserId(SpringMvcUtil.getUserId());
		super.loadGrid(accountManage);
	}
	
	/**
	 * List页面跳转（Jqgrid）
	 * @param model 
	 * @return String
	 */
	@RequestMapping("/toAccount")
	public String toAccount(Model model) {
		model.addAttribute("memberId", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	/**
	 * 方法功能说明：根据当前会员id解绑账号
	 * @param accountManage userId 账户Id
	 * @return
	 */
//	@Log(methodDesc="解绑账号")
//	@RequestMapping(value = "/updRelieveById", method = RequestMethod.POST)
//	public void updateUnbindingAccount(@ModelAttribute AccountManage accountManage) {
//		Result result =  new Result();
//		accountManage.setId(SpringMvcUtil.getUserId());
//		try {
//			result	= accountManageService.updateUnbindingAccount(accountManage, getRequest());
//		} catch (Exception e) {
//			result.setMessage("解绑失败！");
//			result.setStatus(Constants.RETURN_ERROR);
//			logger.error("解绑失败：" + e);
//			e.printStackTrace();
//		}
//		
//		super.responseAjaxData(result);
//	}
	
	/**
	 * 方法功能说明：绑定账号
	 * @param accountManage 当前账号id userId
	 * @param 绑定账号id currentMemberId
	 * @param 绑定账号密码 currentPassword（MD5加密32位字符）
	 * @return
	 */
	@Log(methodDesc="绑定账号")
	@RequestMapping(value = "/bindingAccount", method = RequestMethod.POST)
	public void updateBindingAccount(@RequestParam("currentMemberId") String currentMemberId,@RequestParam("currentPassword") String currentPassword) {
		Result result = new Result();
		AccountManage accountManage = new AccountManage();
		accountManage.setCurrentMemberId(currentMemberId);
		accountManage.setUserId(SpringMvcUtil.getUserId());
		try {
			//密码解密
			 currentPassword = new String(Base64.decodeBase64(currentPassword.getBytes("utf-8")));
		} catch (Exception e) {
			result.setMessage("解密失败！");
			result.setStatus(Constants.RETURN_ERROR);
			logger.error("解密失败：" + e);
			e.printStackTrace();
		}
		//密码加密
		accountManage.setCurrentPassword(CommonUtil.getMd5(currentPassword));
		try {
			result = accountManageService.updateBindingAccount(accountManage, getRequest());
		} catch (Exception e) {
			result.setMessage("绑定失败！");
			result.setStatus(Constants.RETURN_ERROR);
			logger.error("绑定失败：" + e);
			e.printStackTrace();
		}
		super.responseAjaxData(result);
	}
	
}
