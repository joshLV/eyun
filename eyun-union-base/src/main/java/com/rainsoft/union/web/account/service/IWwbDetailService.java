package com.rainsoft.union.web.account.service;


import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.account.model.WwbUseRecord;

/**
 * 旺旺币账户信息查询
 * @author huxiaolong
 */
public interface IWwbDetailService {
	
	/**
	 * 旺旺币购买记录
	 */
	public JSONObject getBuyWwbRecord(WwbAccount wwbAccount ,PageBean page) throws Exception;
	/**
	 * 旺旺币消费记录
	 */
	public JSONObject getWwbUseRecord(WwbAccount wwbAccount,PageBean page) throws Exception;
	
	/**
	 * 旺旺币消费详细记录
	 */
	public List<WwbUseRecord> getWwbUseRecordDetail(WwbAccount wwbAccount) throws Exception;
	
}
