package com.rainsoft.riplat.web.notice.dao;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.riplat.web.notice.model.PlatformUser;

public interface IPlatformUserDao extends IMybatisPersitenceDao<PlatformUser, String> {

    void updatePlatformKey(String oldPlatformId, String platformId);
    
    /**
     * 功能说明：根据platformuser加载数据
     * @param platformuser 平台用户实体对象
     * @return PlatformUser 平台用户实体对象
     */
    PlatformUser loadPlatformUserData(PlatformUser platformuser);

    /**
     * 根据平台ID和用户登录账号获取登陆账号主键ID
     * @param platformuser { loginUserName,platformId }
     * @return {Integer id}
     */
    Integer getUserIdByParam(PlatformUser platformUser) throws  Exception;

}