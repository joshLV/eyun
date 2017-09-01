package com.rainsoft.union.web.sysmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.sysmanage.dao.ISurfWebSetDao;
import com.rainsoft.union.web.sysmanage.model.SurfWebSet;

/**
 * 
 *上网设置
 */
@Repository("surfWebSetDao")
public class SurfWebSetDaoImpl extends MybatisPersitenceDaoImpl<SurfWebSet, String> implements ISurfWebSetDao {

}
