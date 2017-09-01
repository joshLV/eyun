package com.rainsoft.riplat.web.push.controller;

import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.web.system.model.BaseUser;
import com.rainsoft.base.web.system.service.IBaseUserService;
import com.rainsoft.riplat.web.push.model.AppToken;



public abstract class BaseMsgPushController extends SpringBaseController<AppToken, String> {
	


    private static final String AUTHDIGEST = "EYUN";// HTTP消息头请求的digest
   
    @Resource
    private IBaseUserService baseUserService;

    @Override
    protected abstract IMybatisBasePersitenceService<AppToken, String> getBaseService();

    @Override
    protected abstract String getPrefix();
    
    /**
     * 验证消息请求方是否为合法的请求方 Authorization：
     * 
     * @return
     */
    protected   boolean validateHttpHeader() {
        Enumeration<String> emuHeaderNames = getRequest().getHeaderNames();
        boolean flag = false;
        while (emuHeaderNames.hasMoreElements()) {
            String name = (String) emuHeaderNames.nextElement();
            if ("authorization".equals(name)) {
                String authHeader = null;
                try {
                    authHeader = DesUtil.decrypt(getRequest().getHeader(name));// 解密digest消息头
                    logger.info("authHeader: " + authHeader);
                } catch (Exception e) {
                    logger.warn("解密HTTP消息头信息失败");
                    e.printStackTrace();
                }
                if (AUTHDIGEST.equals(authHeader)) {// 与本地消息头进行对比
                    flag = true;
                    logger.info("HTTP消息头验证通过");
                    return flag;
                } else {
                    flag = false;
                    logger.info("HTTP消息头未验证通过");
                }
            }
        }
        return true;
    }
    /**
     * 解密
     *
     * @param params
     *            字符串
     * @return 字符串
     */
    protected   String desDecrypt(String params) {
        String desStr = "";
        try {
            logger.info("解密前Params：" + params);
            desStr = DesUtil.decrypt(params);
            logger.info("解密后Params：" + desStr);
        } catch (Exception e) {
            logger.error("解密失败");
        }
        return desStr;
    }
    /**
     * 验证平台ID是否存在切已经激活
     * @param platformId
     * @return{
     *     是否能被激活；
     *      0：有效的License；
     *      1：已被平台激活，不能再次激活';
     * }
     */
    public boolean validatePlatformId(String platformId){
        String mapValue = Constants.PLATFORM_MAP.get(platformId);
        if (StringUtil.isNotEmpty(mapValue) && Constants.DISABLE.equals(mapValue)) {
            return true;
        }
        return false;
    }
    
    /**
     * 验证平台用户是否和合法的登录用户 Authorization：
     * 
     * @return
     */
    public boolean validatePlatformUser(JSONObject jsonObj) {
        // 验证用户是否有效
        String loginName = jsonObj.getString("loginUsername");
        String password = jsonObj.getString("loginPassword");
        String platformId = jsonObj.getString("platformId");
        BaseUser user = new BaseUser();
        user.setLoginName(loginName);
        user.setPassword(password);// 其他平台传过来是已经加密后的
        user.setLoginWay(1); // 最后登录方式, 1：web网页; 2：wap网页; 3：手机客户端；4：未知
        user.setPlatformId(platformId);// 属于哪个平台
        user = baseUserService.loginWithPlatform(user);
        if (user != null && user.getId() != null) {
            setAuthenToken(user);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 设置认证token信息
     * 
     * @param user
     */
    public void setAuthenToken(BaseUser user) {
        // 用户信息保存到session
        AuthenToken authenToken = new AuthenToken();
        authenToken.setAnotherName(user.getAnotherName());
        authenToken.setLoginTime(new Date());
        authenToken.setLoginName(user.getLoginName());
        authenToken.setUserId(user.getId());
        authenToken.setPlatformId(user.getPlatformId());
        getRequest().getSession().setAttribute("AuthenToken", authenToken);
    }
}
