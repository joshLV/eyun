package com.rainsoft.riplat.web.systemManage.controller;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.model.MemberInfo;
import com.rainsoft.riplat.web.systemManage.service.IMemberInfoService;

/**
 * 个人信息
 * 
 */
@Controller 
@RequestMapping("/systemManage/member")
public class MemberInfoController extends SpringBaseController<MemberInfo, String>{
	private final String PREXIF = "/systemManage/member";
	@Resource
	private IMemberInfoService memberInfoService;
	
	@Override
	protected IMybatisBasePersitenceService<MemberInfo, String> getBaseService() {
		return memberInfoService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}
	/**
	 * 方法功能说明：修改密码
	 * @param MemberInfo 会员信息实体类
	 * @param userId 当前会员id
	 * @param newUserPassword 用户设置密码
	 * @param currentPassword 用户当前的密码
	 * @return
	 */
	@RequestMapping(value = "/userPwdSet", method = RequestMethod.POST)
	public void userPwdSet(@RequestParam("newUserPassword") String newUserPassword,@RequestParam("currentPassword") String currentPassword) {
		Result result = new Result();
		MemberInfo memberInfo = new MemberInfo();
		try {
			//用户设置密码解密
			newUserPassword = new String(Base64.decodeBase64(newUserPassword.getBytes("utf-8")));
			//用户当前密码解密
			currentPassword = new String(Base64.decodeBase64(currentPassword.getBytes("utf-8")));
		} catch (Exception e) {
			result.setMessage("解密失败！");
			result.setStatus(Constants.RETURN_ERROR);
			e.printStackTrace();
		}
		//用户设置密码，加密
		memberInfo.setNewUserPassword(CommonUtil.getMd5(newUserPassword));
		//用户当前密码，加密
		memberInfo.setCurrentPassword(CommonUtil.getMd5(currentPassword));
		memberInfo.setUserId(SpringMvcUtil.getUserId());
		memberInfo.setRemark("用户修改密码操作");
		try {
			result = memberInfoService.updateMemberPwd(memberInfo, getRequest());
		} catch (Exception e) {
			result.setMessage("修改失败！");
			result.setStatus(Constants.RETURN_ERROR);
			e.printStackTrace();
		}
		super.responseAjaxData(result);
	}
	
	/**
	 * 方法功能说明：跳转会员信息页面
	 * @return 
	 */
	@RequestMapping("/toUserInfo")
	public String toUserInfo() {
		return PREXIF + "Info";
	}
	/**
	 * 方法功能说明：会员信息
	 * @param userId 当前会员id
	 * @return MemberInfo 会员信息实体类
	 */
	@RequestMapping(value = "/getMemberInfoById", method = RequestMethod.POST)
	public void findMemberInfoById(){
		Result result = null;
		try {
			result = memberInfoService.findMemberInfoById(SpringMvcUtil.getUserId().toString(), getRequest());
		} catch (Exception e) {
			result.setMessage("获取数据失败！");
			result.setStatus(Constants.RETURN_ERROR);
			e.printStackTrace();
		}
		super.responseAjaxData(result);
	}
	
	
	/**
	 * 修改个人信息
	 * @param memberInfo 个人信息实体
	 */
    @RequestMapping("/updateMemberInfo")
    public void updMemberInfo(@ModelAttribute MemberInfo memberInfo) {
        Result result = new Result();
        memberInfo.setUserId(SpringMvcUtil.getUserId());
        try {
        	result = memberInfoService.updateMemberInfo(memberInfo, getRequest());
		} catch (Exception e) {
			result.setMessage("修改失败！");
			result.setStatus(Constants.RETURN_ERROR);
			e.printStackTrace();
		}
        super.responseAjaxData(result);
    }
}