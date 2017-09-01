package com.rainsoft.riplat.web.notice.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.riplat.web.notice.dao.IPlatformUserDao;
import com.rainsoft.riplat.web.notice.model.PlatformUser;
import com.rainsoft.riplat.web.notice.service.IPlatformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PlatformUserServiceImpl extends MybatisBasePersitenceServiceImpl<PlatformUser, String> implements IPlatformUserService {

    private static final Logger logger = LoggerFactory.getLogger(PlatformUserServiceImpl.class);
    @Resource
    private IPlatformUserDao platformUserDaoImpl;
    
    @Override
    protected IMybatisPersitenceDao<PlatformUser, String> getBaseDao() {
        return platformUserDaoImpl;
    }

    @Override
    public void updatePlatformKey(String oldPlatformId, String platformId) {
        platformUserDaoImpl.updatePlatformKey(oldPlatformId,platformId);
        
    }

	@Override
	public PlatformUser loadPlatformUserData(PlatformUser platformUser) {
		return platformUserDaoImpl.loadPlatformUserData(platformUser);
	}

    @Override
    public Integer getUserIdByParam(String platformId,String loginUserName) {
        Integer userId = 0;
        if(!(CommonUtil.isEmpty(platformId) || CommonUtil.isEmpty(loginUserName))){
            PlatformUser platformUser = new PlatformUser();
            platformUser.setPlatformId(platformId);
            platformUser.setLoginUsername(loginUserName);
            try {
                userId =  platformUserDaoImpl.getUserIdByParam(platformUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logger.error("PlatformUserServiceImpl.getUserIdByParam-->参数不合法，platformId:"+platformId+",loginUseName:"+loginUserName);
        }

        return userId;
    }

}