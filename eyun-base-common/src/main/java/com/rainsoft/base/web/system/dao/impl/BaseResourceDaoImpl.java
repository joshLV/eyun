package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IBaseResourceDao;
import com.rainsoft.base.web.system.model.BaseResource;
@Repository("baseResourceDao")
public class BaseResourceDaoImpl extends MybatisPersitenceDaoImpl<BaseResource, String> implements IBaseResourceDao{
	
}
