/**
 * @Description:
 * @author:王乾
 * @date:2016年4月18日下午12:58:54
 */
package com.rainsoft.union.web.account.service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.model.AccDetail;
import com.rainsoft.union.web.account.model.AccUseRecord;

public interface IAccDetailService extends IMybatisBasePersitenceService<AccDetail, String>{
	
	/**
	 * 账户支出记录
	 * @param wwb Entity
	 * @param page PageBean
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject getAccUseRecord(AccUseRecord wwb,PageBean page) throws Exception;

}