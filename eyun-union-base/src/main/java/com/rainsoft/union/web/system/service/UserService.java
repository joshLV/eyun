package com.rainsoft.union.web.system.service;

/**
 * 会员相关操作
 * 1、找回密码功能
 * Created by sun on 2016/6/29.
 */
public interface UserService {
    /**
     * 检验手机号和易盟号是否正确
     * @param mobile  手机号码
     * @param userName 易盟用户名
     * @return null代表不存在
     * @throws Exception
     */
    Integer updateCheckMobile(String mobile, String userName) throws Exception;

    /**
     * 发送验证码
     * @param mobile 手机号码
     * @return
     * @throws Exception
     */
    Integer updateSendCode(String mobile, String userName) throws Exception;

    /**
     * 检查验证码
     * @param mobile 手机号码
     * @return
     * @throws Exception
     */
    Integer updateCheckCode(String mobile, String code) throws Exception;

    /**
     * 重置密码功能
     * @param userName 用户名
     * @param mobile   手机
     * @param code     验证码
     * @param password 新密码
     * @return
     * @throws Exception
     */
    Integer updateResetPwd(String userName, String mobile, String code, String password) throws Exception;
}
