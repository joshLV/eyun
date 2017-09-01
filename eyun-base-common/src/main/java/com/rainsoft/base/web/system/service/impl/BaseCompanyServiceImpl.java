package com.rainsoft.base.web.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.web.system.dao.IBaseCompanyDao;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.service.IBaseCompanyService;
@Service("baseCompanyService")
public class BaseCompanyServiceImpl extends MybatisBasePersitenceServiceImpl<BaseCompany, String> implements IBaseCompanyService{
	@Resource
	private IBaseCompanyDao baseCompanyDao;
	@Override
	protected IMybatisPersitenceDao<BaseCompany, String> getBaseDao() {
		return baseCompanyDao;
	}

}
