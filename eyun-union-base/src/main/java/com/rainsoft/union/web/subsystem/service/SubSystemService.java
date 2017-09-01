package com.rainsoft.union.web.subsystem.service;

/**
 * 子系统服务
 * 旺旺吧进销存
 * Created by sun on 2016/7/5.
 */
public interface SubSystemService {
    /**
     * 注册旺旺吧会员
     *
     * @param userID 易盟会员ID
     * @return
     */
    Integer saveCoobar(Integer userID) throws Exception;

    /**
     * 同步修改旺旺吧帐号密码
     * @param userName 用户名
     * @param userPwd  密码
     * @return
     * @throws Exception
     */
    Integer updatePassword(String userName, String userPwd) throws Exception;

    /**
     * 同步修改旺旺吧帐号密码
     * @param userName 用户名
     * @param userPwd  密码
     * @return
     * @throws Exception
     */
    Integer updatePassword(Integer memberId, String userPwd) throws Exception;
}
