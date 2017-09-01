package com.rainsoft.union.web.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.index.dao.IIndexDao;
import com.rainsoft.union.web.index.model.Index;

/**
 * 
 * 首页
 *
 */
@Repository("indexDao")
public class IndexDaoImpl extends MybatisPersitenceDaoImpl<Index, String> implements IIndexDao {

}

