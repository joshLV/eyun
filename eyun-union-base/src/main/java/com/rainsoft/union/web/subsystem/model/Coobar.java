package com.rainsoft.union.web.subsystem.model;


import com.rainsoft.base.common.utils.StringUtil;

/**
 * 旺旺吧实体
 * Created by sun on 2016/7/5.
 */
public class Coobar {
    private String userName;   // 用户名
    private String userPwd;    // 用户密码
    private String realName;   // 真实姓名
    private String areaID;     // 区域ID 格式 例如：310000，310100，310101
    private String email;      // 邮箱
    private String mobile;     // 手机号码
    private String addr;       // 地址
    private Integer id;        // 用于存放主键的字段

    public Coobar() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAreaID() {
        if (StringUtil.isNotEmpty(areaID)) {
            areaID = areaID.substring(0,2)+"0000,"+areaID.substring(0,4)+"00,"+areaID;
        }
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
