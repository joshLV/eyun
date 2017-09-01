package com.rainsoft.riplat.web.systemManage.service;

import javax.servlet.http.HttpServletRequest;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.model.MemberInfo;

/**
 * 个人信息
 * 
 */
public interface IMemberInfoService extends IMybatisBasePersitenceService<MemberInfo, String>{
	
	/**
	 * 方法功能说明：修改密码
	 * @param MemberInfo 个人信息实体类
	 * @param userId 当前会员id
	 * @param userPassword 用户设置密码
	 * @param currentPassword 用户当前的密码
	 * @return Result  返回的结果
	 */
	public Result updateMemberPwd(MemberInfo memberInfo, HttpServletRequest request) throws Exception;
	
	/**
	 * 方法功能说明：会员信息
	 * @param userId 当前会员id
	 * @return MemberInfo 个人信息实体类
	 */
	public Result findMemberInfoById(String UserId, HttpServletRequest request) throws Exception;
	
	/**
	 * 方法功能说明：修改会员个人信息
	 * @param MemberInfo 个人信息实体类
	 * @return 返回的结果
	 */
	public Result updateMemberInfo(MemberInfo memberInfo, HttpServletRequest request) throws Exception;
	
	
}
