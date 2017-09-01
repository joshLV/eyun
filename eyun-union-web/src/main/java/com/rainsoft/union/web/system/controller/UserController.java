package com.rainsoft.union.web.system.controller;

import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.union.web.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 会员相关功能
 * 1、密码找回功能
 */
@Controller
@RequestMapping("/system/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userServiceImpl;
    /**
     * 跳转找回密码页面
     * @return
     */
    @RequestMapping("/find_pwd_login")
    public String find_pwd() {
        return "system/find_Pwd";
    }

    /**
     * 验证手机号码和易盟号码
     * @param response response
     * @param mobile   发送验证码的手机号码
     * @param userName 易盟会员
     */
    @RequestMapping("/pwd_login")
    public void user_checkPasswordCode(HttpServletResponse response,@RequestParam String mobile,@RequestParam String userName) {
        Integer rs = -1;
        try {
            rs = userServiceImpl.updateCheckMobile(mobile, userName);
        } catch (Exception e) {
            logger.error(" 检验手机号码和易盟号失败",e);
        }
        SpringMvcUtil.responseWriter(response, rs==null?"-1":rs+"");
    }

    /**
     * 发送验证码
     * @param response response
     */
    @RequestMapping("/sendCode_login")
    public void sendCode(HttpServletResponse response,@RequestParam String mobile,@RequestParam String userName) {
        Integer rs = -1;
        try {
            rs = userServiceImpl.updateSendCode(mobile, userName);//1代表发送成功
        } catch (Exception e) {
            logger.error(" 检验手机号码和易盟号失败",e);
        }
        SpringMvcUtil.responseWriter(response, rs==null?"-1":rs+"");
    }

    /**
     * 验证发送的验证码
     * @param response response
     * @param mobile   手机号码
     * @param code     验证码
     */
    @RequestMapping("/verifyCode_login")
    public void verifyCode(HttpServletResponse response,@RequestParam String mobile,@RequestParam String code) {
        Integer rs = -1;
        try {
            rs = userServiceImpl.updateCheckCode(mobile, code);
        } catch (Exception e) {
            logger.error(" 检验手机号码和易盟号失败",e);
        }
        SpringMvcUtil.responseWriter(response, rs+"");
    }

    /**
     * 重新设置密码页面
     * @param mobile   手机号码
     * @param userName 用户名
     * @param code     验证码
     * @param model    页面传值对象
     * @return
     */
    @RequestMapping("/resetPage_login")
    public String resetPage_login(@RequestParam String mobile,@RequestParam String userName,@RequestParam String code,Model model) {
        model.addAttribute("mobile",mobile );
        model.addAttribute("userName",userName );
        model.addAttribute("code",code );
        return "system/resetPassword";
    }

    /**
     * 设置密码
     * @param response
     * @param mobile   手机号码
     * @param userName 用户名
     * @param password 密码
     * @param code     验证码
     */
    @RequestMapping("/resetPassword_login")
    public void resetPassword(HttpServletResponse response,@RequestParam String mobile,@RequestParam String userName,
                                @RequestParam String password,@RequestParam String code) {
        Integer rs = -1;
        try {
            rs = userServiceImpl.updateResetPwd(userName, mobile, code, password);
        } catch (Exception e) {
            logger.error("重置密码失败", e);
        }
        SpringMvcUtil.responseWriter(response, rs + "");

    }

}