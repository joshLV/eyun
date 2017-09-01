package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.IOperateUserDao;
import com.rainsoft.base.web.system.model.OperateUser;

@Repository("operateUserDao")
public class OperateUserDaoImpl extends MybatisPersitenceDaoImpl<OperateUser, String> implements IOperateUserDao {

}
