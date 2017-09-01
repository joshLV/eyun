package com.rainsoft.union.web.account.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.account.dao.IAccAccountDao;
import com.rainsoft.union.web.account.model.AccAccount;

@Repository("accAccountDao")
public class AccAccountDaoImpl extends MybatisPersitenceDaoImpl<AccAccount, String> implements IAccAccountDao {

	
}
