package com.rainsoft.riplat.web.systemManage.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.IPortalDao;
import com.rainsoft.riplat.web.systemManage.model.Portal;

import org.springframework.stereotype.Repository;

/**
 * 认证模板
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Repository("portalDao")
public class PortalDaoImpl extends MybatisPersitenceDaoImpl<Portal, String> implements IPortalDao {
}
