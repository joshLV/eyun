package com.rainsoft.union.web.sysmanage.controller;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.MemberInfo;
import com.rainsoft.union.web.sysmanage.service.IMemberInfoService;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 个人信息
 */
@Controller 
@RequestMapping("/systemManage/member")
@Log(clazzDesc = "个人信息")
public class MemberInfoController extends SpringBaseController<MemberInfo, String>{
	private final String PREXIF = "/sysmanage/member";
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
	 * @param
	 * @return
	 */
	@Log(methodDesc="修改密码")
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
	 * @param materialType 文件类型
	 * @param file 上传文件
	 */
	@Log(methodDesc = "修改个人信息")
    @RequestMapping("/updateMemberInfo")
    public void updMemberInfo(@ModelAttribute MemberInfo memberInfo, @RequestParam(value = "imgFile", required = false) MultipartFile file) {
        Result result = new Result();
        memberInfo.setId(SpringMvcUtil.getUserId());
        memberInfo.setUserId(SpringMvcUtil.getUserId());
        Integer i = -1;//修改失败
        try {
            i = memberInfoService.updateMemberInfo(memberInfo, file);
        } catch (Exception e) {
            logger.error("修改个人信息失败：" + e);
        }
        result.setMessage(i.toString() + "");
        SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }
	/**
	 * 方法功能说明：设置用户别名
	 * @param memberInfo 会员信息实体
	 * @param nickName 用户别名
	 * @param userId 当前账户Id
	 * @return
	 */
	@Log(methodDesc="设置别名")
	@RequestMapping(value = "/updateNickName", method = RequestMethod.POST)
	public void updateNickName(@RequestParam("nickName") String nickName) {
		Result result = new Result();
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setUserId(SpringMvcUtil.getUserId());
		memberInfo.setNickName(nickName);
		memberInfo.setRemark("设置用户别名操作");
		try {
			result = memberInfoService.updateNickName(memberInfo, getRequest());
		} catch (Exception e) {
			result.setMessage("设置失败！");
			result.setStatus(Constants.RETURN_ERROR);
			e.printStackTrace();
		}
		if (result.getStatus().equals(Constants.RETURN_SUCCESS)) {
			AuthenToken authenToken = (AuthenToken) getSession().getAttribute("AuthenToken");
			authenToken.setAnotherName(nickName);
			getSession().setAttribute("AuthenToken", authenToken);
		}
		super.responseAjaxData(result);
	}
}