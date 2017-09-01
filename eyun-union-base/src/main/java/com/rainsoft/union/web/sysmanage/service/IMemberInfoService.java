package com.rainsoft.union.web.sysmanage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.MemberInfo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
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
	 * @return Result
	 */
	public Result updateMemberPwd(MemberInfo memberInfo, HttpServletRequest request) throws Exception;
	
	/**
	 * 方法功能说明：会员信息
	 * @param userId 当前会员id
	 * @return MemberInfo 个人信息实体类
	 */
	public Result findMemberInfoById(String UserId, HttpServletRequest request) throws Exception;
	
	
	
	/**
	 * 方法功能说明：设置用户别名
	 * @param memberInfo 个人信息实体类
	 * @param nickName 用户别名
	 * @param userId 账户Id
	 * @return
	 */
	public Result updateNickName(MemberInfo memberInfo, HttpServletRequest request) throws Exception;
	
	/**
	 * 修改会员的信息
	 * @param memberInfo
	 * @param file
	 * @return Integer 返回值
	 * @throws Exception
	 */
	public Integer updateMemberInfo(MemberInfo memberInfo, MultipartFile file) throws Exception;
}
