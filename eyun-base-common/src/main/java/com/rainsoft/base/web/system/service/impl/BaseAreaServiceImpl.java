package com.rainsoft.base.web.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.web.system.dao.IBaseAreaDao;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.base.web.system.service.IBaseAreaService;

@Service("baseAreaService")
public class BaseAreaServiceImpl extends MybatisBasePersitenceServiceImpl<BaseArea, String> implements IBaseAreaService {

	@Resource
	private IBaseAreaDao baseAreaDao;

	@Override
	protected IMybatisPersitenceDao<BaseArea, String> getBaseDao() {
		return baseAreaDao;
	}
}