package com.rainsoft.union.web.sysmanage.dao.impl;


import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.sysmanage.dao.ISystemParamDao;
import com.rainsoft.union.web.sysmanage.model.SystemParam;


/**
 * 系统参数
 *
 */
@Repository("systemParamDao")
public class SystemParamDaoImpl  extends MybatisPersitenceDaoImpl<SystemParam, String> implements ISystemParamDao{

	
}
