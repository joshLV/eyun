package com.rainsoft.riplat.web.systemManage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.systemManage.dao.IPortalDao;
import com.rainsoft.riplat.web.systemManage.model.Portal;
import com.rainsoft.riplat.web.systemManage.service.IPortalService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证素材
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Service("portalService")
public class PortalServiceImpl extends MybatisBasePersitenceServiceImpl<Portal, String> implements IPortalService {
    @Resource
    private IPortalDao portalDao;

    @Override
    protected IMybatisPersitenceDao<Portal, String> getBaseDao() {
        return portalDao;
    }

}
