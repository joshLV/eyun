package com.rainsoft.base.web.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.dao.ILogOptRecordDao;
import com.rainsoft.base.web.system.model.LogOptRecord;
@Repository("logOptRecordDao")
public class LogOptRecordDaoImpl extends MybatisPersitenceDaoImpl<LogOptRecord, String> implements ILogOptRecordDao {

}
