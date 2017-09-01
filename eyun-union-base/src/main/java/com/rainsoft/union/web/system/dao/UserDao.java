package com.rainsoft.union.web.system.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 会员功能
 * Created by sun on 2016/6/29.
 */
public interface UserDao {
    /**
     * 检验手机号和易盟号是否正确
     * @param map 参数
     * @return 是否存在该手机和易盟号
     */
    Integer checkMobile(Map<String,Object> map);

    /**
     * 检验短信发送次数
     * @param map 参数
     * @return 该手机短信发送的次数
     */
    Integer sendCount(@Param("mobile")String mobile,@Param("type")String type);

    /**
     * 保存验证码
     * @param map 参数
     * @return 是否保存成功
     */
    Integer saveCode(Map<String, Object> map);

    /**
     * 获取最新的验证码
     * @param map 参数
     * @return
     */
    String checkCode(Map<String, Object> map);

    /**
     * 修改验证码状态
     * @param map
     * @return
     */
    Integer updateCodeStatus(Map<String, Object> map);

    /**
     * 重置密码
     * @param map
     * @return
     */
    Integer resetPwd(Map<String,Object> map);

}
