package com.rainsoft.riplat.web.notice.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.IPlatformUserDao;
import com.rainsoft.riplat.web.notice.model.PlatformUser;
import org.springframework.stereotype.Repository;

@Repository
public class PlatformUserDaoImpl extends MybatisPersitenceDaoImpl<PlatformUser, String> implements IPlatformUserDao {

    @Override
    public void updatePlatformKey(String oldPlatformId, String platformId) {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPlatformId(platformId);
        platformUser.setOldPlatformId(oldPlatformId);
        this.getSqlSession().update(getSqlName("updatePlatformKey"), platformUser);
    }

	@Override
	public PlatformUser loadPlatformUserData(PlatformUser platformUser) {
	    return this.getSqlSession().selectOne(getSqlName("loadplatformuserData"), platformUser);
	}

    @Override
    public Integer getUserIdByParam(PlatformUser platformUser) throws Exception{
        return this.getSqlSession().selectOne(getSqlName("getUserIdByParam") , platformUser);
    }

}

