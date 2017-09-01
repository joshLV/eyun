package com.rainsoft.riplat.web.index.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.index.dao.IIndexDao;
import com.rainsoft.riplat.web.index.model.Index;

@Repository("indexDao")
public class IndexDaoImpl extends MybatisPersitenceDaoImpl<Index, String> implements IIndexDao {

}

