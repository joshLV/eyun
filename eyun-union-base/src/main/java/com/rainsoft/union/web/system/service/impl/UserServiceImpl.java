package com.rainsoft.union.web.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.HttpUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.union.web.subsystem.service.SubSystemService;
import com.rainsoft.union.web.system.dao.UserDao;
import com.rainsoft.union.web.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员相关操作
 * 1、找回密码功能
 * Created by sun on 2016/6/30.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private SubSystemService subSystemServiceImpl;

    /**
     * 检验手机号和易盟号是否正确
     *
     * @param mobile   手机号码
     * @param userName 易盟用户名
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateCheckMobile(String mobile, String userName) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("userName", userName);
        return userDao.checkMobile(map);
    }

    /**
     * 发送找回密码的短信验证码
     *
     * @param mobile 手机号码
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateSendCode(String mobile, String userName) throws Exception {
        // 检验手机发送次数是否超过上限（三次）
        String type = "3";
        Integer rs = userDao.sendCount(mobile, type);
        if (rs >= 3) {
            return -4;// 该手机号码已发送超过三次 请求频繁（一个号码一天最多三次）
        }
        String code = CommonUtil.getRandomNumberStr(6);
        //todo 发送短信
        sendMessage(mobile, code);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("userName", userName);
        // 查询该用户名的id
        Integer memberId = userDao.checkMobile(map);
        map.put("type", type);
        map.put("memberId", memberId);
        map.put("code", code);
        return userDao.saveCode(map);
    }

    /**
     * 检验验证码
     *
     * @param mobile 手机号码
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateCheckCode(String mobile, String code) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("type", "3");
        map.put("time", 15);
        Integer rs = -1;
        String verifyCode = userDao.checkCode(map);
        if (StringUtil.isNotEmpty(verifyCode)) {
            if (verifyCode.equals(code)) {
                rs = 0;
            }
        }
        return rs;
    }

    /**
     * 重置密码
     *
     * @param userName 用户名
     * @param mobile   手机
     * @param code     验证码
     * @param password 新密码
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateResetPwd(String userName, String mobile, String code, String password) throws Exception {
        // 修改验证码状态
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("type", "3");
        map.put("time", 15);
        String verifyCode = userDao.checkCode(map);
        if (StringUtil.isEmpty(verifyCode) || !code.equals(verifyCode)) {
            return -2;
        }
        map.put("code", code);
        userDao.updateCodeStatus(map);//验证码修改状态 表示已经使用
        // 修改密码
        map.put("userName", userName);
        String pwd_md5 = CommonUtil.getMd5(password);
        map.put("pwd", pwd_md5);
        userDao.resetPwd(map);//修改密码
        // 同步修改旺旺吧进销存密码
        return subSystemServiceImpl.updatePassword(userName, pwd_md5);
    }

    /**
     * 发送短信
     * @param mobile 手机号码
     * @param code   验证码
     * @throws Exception
     */
    public void sendMessage(String mobile, String code) throws Exception {
        //获取短信认证接口地址
        String url = SystemConfigUtil.getValue("SMS_SERVICE_URL");
        Map<String, String> dataMap = new HashMap<>();
        //短信类型  07：易韵联盟注册获取验证码，激活
        dataMap.put("smsType", "08");//08：易韵联盟找回密码获取验证码
        //手机号码
        dataMap.put("mobile", mobile);
        //短信内容
        dataMap.put("contents", "您的短信验证码是" + code + "。您正在通过手机号重置易盟的帐号密码，如非本人操作，请忽略此短信。本条短信免费。");
        dataMap.put("placeCode", "");
        String params = JSON.toJSONString(dataMap);
        String desParams = DesUtil.encrypt(params);
        HttpUtil.portConnect(url, desParams);
        //String signature = "【易韵网络】";
    }
}
