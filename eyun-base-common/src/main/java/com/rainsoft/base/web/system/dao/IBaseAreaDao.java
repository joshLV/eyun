package com.rainsoft.base.web.system.dao;

import java.util.List;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.web.system.model.BaseArea;

public interface IBaseAreaDao extends IMybatisPersitenceDao<BaseArea, String> {
	// area
	public List<BaseArea> findAllArea();
}
