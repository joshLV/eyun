package com.rainsoft.riplat.web.mgrparam.dao;

import java.util.List;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.riplat.web.mgrparam.model.Platform;

public interface IPlatformDao extends IMybatisPersitenceDao<Platform, String> {

    List<Platform> getPlatformTypeList();
    
    List<Platform> findPlatformIdList();
    
    void activeLicense(String platformId);
    
    List<Platform> getPlatInfoByIP(String platformIP);
    
    List<Platform> getPlatInfoByID(String platformID);
}