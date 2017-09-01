package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IBaseRoleDao;
import com.rainsoft.base.web.system.model.BaseRole;

@Repository("baseRoleDao")
public class BaseRoleDaoImpl extends MybatisPersitenceDaoImpl<BaseRole, String> implements IBaseRoleDao {

}