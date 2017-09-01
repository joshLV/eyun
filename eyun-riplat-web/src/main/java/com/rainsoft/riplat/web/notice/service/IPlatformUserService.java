package com.rainsoft.riplat.web.notice.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.riplat.web.notice.model.PlatformUser;

public interface IPlatformUserService extends IMybatisBasePersitenceService<PlatformUser, String> {

    /**
     * 其他平台导入新的Key文件时，更新DB中的platformId
     * @param oldPlatformId
     * @param platformId
     */
    void updatePlatformKey(String oldPlatformId, String platformId);

    /**
     * 功能说明：根据platformUser加载数据
     * @param platformUser 平台用户实体对象
     * @return PlatformUser 平台用户实体对象
     */
    PlatformUser loadPlatformUserData(PlatformUser platformUser);

    /**
     * 根据平台ID和用户登录账号获取登陆账号主键ID
     * @param platformId  平台ID
     * @param loginUserName 登录账号
     * @return {Integer id}
     */
    Integer getUserIdByParam(String platformId,String loginUserName);
}