package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IBaseCompanyDao;
import com.rainsoft.base.web.system.model.BaseCompany;
@Repository("baseCompanyDao")
public class BaseCompanyDaoImpl extends MybatisPersitenceDaoImpl<BaseCompany, String> implements IBaseCompanyDao {
	
}
