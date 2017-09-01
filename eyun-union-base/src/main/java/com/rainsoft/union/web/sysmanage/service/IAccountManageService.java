package com.rainsoft.union.web.sysmanage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.AccountManage;

import javax.servlet.http.HttpServletRequest;

/**
 * 账号管理
 * 
 */
public interface IAccountManageService extends IMybatisBasePersitenceService<AccountManage, String>{
	
	/**
	 * 方法功能说明：根据当前会员id解绑账号
	 * @param accountManage userId 账户Id
	 * @return Result
	 */
//	public Result updateUnbindingAccount(AccountManage accountManage, HttpServletRequest request) throws Exception;
	
	/**
	 * 方法功能说明：绑定账号
	 * @param accountManage 当前账号id userId
	 * @param 绑定账号id currentMemberId
	 * @param 绑定账号密码 currentPassword（MD5加密32位字符）
	 * @return
	 */
	public Result updateBindingAccount(AccountManage accountManage, HttpServletRequest request) throws Exception;
	
}
