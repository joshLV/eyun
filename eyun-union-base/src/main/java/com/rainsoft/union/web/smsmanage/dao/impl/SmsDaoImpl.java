package com.rainsoft.union.web.smsmanage.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.smsmanage.dao.ISmsDao;
import com.rainsoft.union.web.smsmanage.model.SmsManage;
import org.springframework.stereotype.Repository;


@Repository("smsDao")
public class SmsDaoImpl extends MybatisPersitenceDaoImpl<SmsManage, String> implements ISmsDao {
	
}
