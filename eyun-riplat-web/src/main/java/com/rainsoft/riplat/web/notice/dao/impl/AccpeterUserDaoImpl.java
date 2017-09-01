package com.rainsoft.riplat.web.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.IAccpeterUserDao;
import com.rainsoft.riplat.web.notice.model.AccpeterUser;

@Repository
public class AccpeterUserDaoImpl extends MybatisPersitenceDaoImpl<AccpeterUser, String> implements IAccpeterUserDao {

}

