package com.rainsoft.riplat.web.systemManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.IAccountManageDao;
import com.rainsoft.riplat.web.systemManage.model.AccountManage;

/**
 * 账户管理
 * */
@Repository("accountManageDao")
public class AccountManageDaoImpl extends MybatisPersitenceDaoImpl<AccountManage, String> implements IAccountManageDao {

}
