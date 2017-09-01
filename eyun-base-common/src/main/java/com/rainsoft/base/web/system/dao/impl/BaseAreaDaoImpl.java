package com.rainsoft.base.web.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IBaseAreaDao;
import com.rainsoft.base.web.system.model.BaseArea;

@Repository("baseAreaDao")
public class BaseAreaDaoImpl extends MybatisPersitenceDaoImpl<BaseArea, String> implements IBaseAreaDao {

	@Override
	public List<BaseArea> findAllArea() {
		return super.selectList("findAllArea");
	}

}