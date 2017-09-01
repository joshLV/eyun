package com.rainsoft.union.web.sysmanage.service;


import javax.servlet.http.HttpServletRequest;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.SystemParam;


/**
 * 
 * 系统参数
 *
 */
public interface ISystemParamService extends IMybatisBasePersitenceService<SystemParam, String> {
	
	/**
	 * 更新系统设置
	 * @param systemParam
	 * @param request
	 * @return  Result
	 */
	public Result updateSysParam(SystemParam systemParam, HttpServletRequest request) throws Exception;

}