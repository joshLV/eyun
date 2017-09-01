package com.rainsoft.union.web.sysmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.sysmanage.dao.IAccountManageDao;
import com.rainsoft.union.web.sysmanage.model.AccountManage;

/**
 * 账号管理
 * */
@Repository("accountManageDao")
public class AccountManageDaoImpl extends MybatisPersitenceDaoImpl<AccountManage, String> implements IAccountManageDao {

}
