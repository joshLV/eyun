package com.rainsoft.base.web.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.web.system.dao.ILogOptRecordDao;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.LogOptRecord;
import com.rainsoft.base.web.system.service.IBaseCompanyService;
import com.rainsoft.base.web.system.service.ILogOptRecordService;
@Service("logOptRecordService")
public class LogOptRecordServiceImpl extends MybatisBasePersitenceServiceImpl<LogOptRecord, String> implements ILogOptRecordService {
	@Resource
	private ILogOptRecordDao logOptRecordDao;
	@Resource
	private IBaseCompanyService baseCompanyService;
	@Override
	protected IMybatisPersitenceDao<LogOptRecord, String> getBaseDao() {
		return logOptRecordDao;
	}

	@Override
	public List<BaseCompany> getUserList(Map<String, Object> map) {
		return baseCompanyService.selectList("getUserList", map);
	}

}
