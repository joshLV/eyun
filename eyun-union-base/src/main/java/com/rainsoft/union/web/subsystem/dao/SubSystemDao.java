package com.rainsoft.union.web.subsystem.dao;

import com.rainsoft.union.web.subsystem.model.Coobar;
import org.apache.ibatis.annotations.Param;

/**
 * 子系统服务
 * 旺旺吧进销存
 * Created by sun on 2016/7/5.
 */
public interface SubSystemDao {
    /**
     * 判断用户名是否注册
     * @param userName 用户名
     * @return 存在条数
     */
    Integer isRegister(String userName);

    /**
     * 插入帐号信息
     * @param coobar 实体
     * @return 修改行数
     */
    Integer accountInfo(Coobar coobar);

    /**
     * 注册Coobar信息
     * @param coobar 实体
     * @return 会员主键id
     */
    Integer registerCoobar(Coobar coobar);

    /**
     * 预设角色
     * @param sys_userID  coobar会员主键，alert 不是我们易盟的
     * @param roleID 预设角色ID 11:运营经理 12:领班  13:收银员  14:店长
     * @return coobar会员分配角色表的主键
     */
    Integer roleInit(@Param("sys_userID")Integer sys_userID,@Param("roleID")Integer roleID,@Param("id")Integer id);

    /**
     * 给预设的角色分配对应的资源
     * @param sys_role_ID 预设角色对应的主键ID
     * @param roleID      预设角色ID 11:运营经理 12:领班  13:收银员  14:店长
     * @return 修改行数
     */
    Integer resourceInit(@Param("sys_role_ID")Integer sys_role_ID,@Param("roleID")Integer roleID);

    /**
     * 获取易盟会员信息
     * @param userID 易盟会员ID
     * @return 实体
     */
    Coobar getMemberInfo(Integer userID);

    /**
     * 插入购买信息
     * @param sys_userID coobar会员ID
     * @param placeNum   授权场所数量
     * @return
     */
    Integer saveBuyInfo(@Param("sys_userID") Integer sys_userID, @Param("placeNum") Integer placeNum);

    /**
     * 修改帐号信息表（修改密码）
     * @param userName 用户名
     * @param userPwd  密码
     * @return
     */
    Integer updateAccountInfo(@Param("userName")String userName,@Param("userPwd")String userPwd);

    /**
     * 修改系统用户表（修改密码）
     * @param userName 用户名
     * @param userPwd  密码
     * @return
     */
    Integer updateSysUser(@Param("userName")String userName,@Param("userPwd")String userPwd);
}
