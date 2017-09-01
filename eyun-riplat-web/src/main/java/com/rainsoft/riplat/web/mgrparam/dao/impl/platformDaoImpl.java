package com.rainsoft.riplat.web.mgrparam.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.mgrparam.dao.IPlatformDao;
import com.rainsoft.riplat.web.mgrparam.model.Platform;

@Repository
public class platformDaoImpl extends MybatisPersitenceDaoImpl<Platform, String> implements IPlatformDao {

    @Override
    public List<Platform> getPlatformTypeList() {
        return this.getSqlSession().selectList(getSqlName("findPlatformTypeList"),null);
    }

    @Override
    public void activeLicense(String platformId) {
        this.getSqlSession().update(getSqlName("activeLicense"),platformId);
    }

	@Override
	public List<Platform> getPlatInfoByIP(String platformIP) {
		return this.getSqlSession().selectList("findByIP", platformIP);
	}

	@Override
	public List<Platform> getPlatInfoByID(String platformID) {
		return this.getSqlSession().selectList("findByPlatformID", platformID);
	}

	@Override
	public List<Platform> findPlatformIdList() {
		
		return this.getSqlSession().selectList(getSqlName("findPlatformIdList"),null);
	}
}

