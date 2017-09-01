package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IBaseDictDao;
import com.rainsoft.base.web.system.model.BaseDict;

/**
 * 
 * 数据字典
 *
 */
@Repository("baseDictDao")
public class BaseDictDaoImpl extends MybatisPersitenceDaoImpl<BaseDict, String> implements IBaseDictDao {
	
}
