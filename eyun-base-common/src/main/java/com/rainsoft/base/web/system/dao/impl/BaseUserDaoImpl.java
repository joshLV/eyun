package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IBaseUserDao;
import com.rainsoft.base.web.system.model.BaseUser;

@Repository("baseUserDao")
public class BaseUserDaoImpl extends MybatisPersitenceDaoImpl<BaseUser, String> implements IBaseUserDao {

}