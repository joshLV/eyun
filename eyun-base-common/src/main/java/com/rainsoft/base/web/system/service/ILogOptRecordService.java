package com.rainsoft.base.web.system.service;

import java.util.List;
import java.util.Map;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.LogOptRecord;

public interface ILogOptRecordService extends IMybatisBasePersitenceService<LogOptRecord, String> {
	/**
	 * 获取用户及其子用户列表
	 */
	public List<BaseCompany> getUserList(Map<String, Object> map);
}
