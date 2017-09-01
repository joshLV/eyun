package com.rainsoft.riplat.web.systemManage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.model.AccountManage;

public interface IAccountManageService extends IMybatisBasePersitenceService<AccountManage, String>{
	
	/**
	 * 方法功能说明：根据当前会员id解绑账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage userId会员id
	 * @return
	 */
	public String updateRelieveById(AccountManage accountManage);
	
	/**
	 * 方法功能说明：绑定账号
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 当前账号id userId
	 * @param 绑定账号id currentMemberId
	 * @param 绑定账号密码 currentPassword（MD5加密32位字符）
	 * @return
	 */
	public Result callBindingAccount(AccountManage accountManage);
	
	/**
	 * 方法功能说明：设置用户别名
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param accountManage 
	 * @param nickName 用户别名
	 * @param userId 当前会员Id
	 * @return
	 */
	public Result updateNickName(AccountManage accountManage);
	
	
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
	public Result updatePayPwdSet(AccountManage accountManage);
	
	/**
	 * 方法功能说明： 查询支付密码
	 * @author sh_j_wangwen
	 * @data 2015年12月8号
	 * @param payPassword 密码设置实体类
	 * @param userId 当前会员id
	 * @return
	 */
	public Result findPayPwd(AccountManage accountManage);
}
