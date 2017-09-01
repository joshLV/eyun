package com.rainsoft.union.web.subsystem.service.impl;

import com.rainsoft.union.web.subsystem.dao.SubSystemDao;
import com.rainsoft.union.web.subsystem.model.Coobar;
import com.rainsoft.union.web.subsystem.service.SubSystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 子系统服务
 * 旺旺吧进销存
 * Created by sun on 2016/7/5.
 */
@Service
public class SubSystemServiceImpl implements SubSystemService {
    @Resource
    private SubSystemDao subSystemDao;
    // 代表授权场所 代表coobar可以注册的场所数量
    private static final Integer PLACE_NUM = 3;
    /**
     * 注册旺旺吧会员 如果已注册 直接登录
     *
     * @param userID 易盟会员ID
     * @return 0 代表正常操作
     * @throws Exception
     */
    @Override
    public Integer saveCoobar(Integer userID) throws Exception {
        Coobar coobar = subSystemDao.getMemberInfo(userID);
        // 判断是否注册coobar会员 注册直接返回
        Integer flag = subSystemDao.isRegister(coobar.getUserName());
        if (flag <= 0) {
            // 插入账号信息
            subSystemDao.accountInfo(coobar);
            // 注册用户
            subSystemDao.registerCoobar(coobar);
            Integer sys_userID = coobar.getId();
            // 插入购买记录 配合原来的业务
            subSystemDao.saveBuyInfo(sys_userID, PLACE_NUM);
            // 预设角色
            //Integer sys_role_ID = subSystemDao.roleInit(sys_userID, 11);
            // 预设角色分配资源
            Integer id = 0;//临时变量 存放主键
            id = subSystemDao.roleInit(sys_userID, 11, id);
            subSystemDao.resourceInit(id, 11);
            id = subSystemDao.roleInit(sys_userID, 12, id);
            subSystemDao.resourceInit(id, 12);
            id = subSystemDao.roleInit(sys_userID, 13, id);
            subSystemDao.resourceInit(id, 13);
            id = subSystemDao.roleInit(sys_userID, 13, id);
            subSystemDao.resourceInit(id, 13);
        }
        return 0;
    }

    /**
     * 同步修改旺旺吧进销存账户密码
     * @param userName 用户名
     * @param userPwd  密码
     * @return 返回值大于0代表成功
     * @throws Exception
     */
    @Override
    public Integer updatePassword(String userName, String userPwd) throws Exception {
        Integer flag = subSystemDao.isRegister(userName);
        if (flag > 0) {
            subSystemDao.updateAccountInfo(userName, userPwd);
            flag =subSystemDao.updateSysUser(userName, userPwd);
        }
        return flag;
    }
    /**
     * 同步修改旺旺吧进销存账户密码
     * @param memberId 易盟用户会员ID
     * @param userPwd  密码
     * @return 返回值大于0代表成功
     * @throws Exception
     */
    @Override
    public Integer updatePassword(Integer memberId, String userPwd) throws Exception {
        Coobar coobar = subSystemDao.getMemberInfo(memberId);
        Integer flag = subSystemDao.isRegister(coobar.getUserName());
        if (flag > 0) {
            subSystemDao.updateAccountInfo(coobar.getUserName(), userPwd);
            flag =subSystemDao.updateSysUser(coobar.getUserName(), userPwd);
        }
        return flag;
    }
}
