package com.rainsoft.union.web.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.account.model.WwbBuyRecord;
import com.rainsoft.union.web.account.model.WwbUseRecord;

/**
 * @author huxiaolong
 * 旺旺币账户信息查询
 */
public interface IWwbDetailDao {
	
	/**
	 * 查询旺旺币购买记录
	 * @param map
	 * @return
	 */
	public List<WwbBuyRecord> getBuyWwbRecord(@Param("map") Map<String, Object> map);

	/**
	 * 查询旺旺币消费记录
	 * @param map
	 * @return
	 */
	public List<WwbUseRecord> getWwbUseRecord(@Param("map") Map<String, Object> map);

	/**
	 * 查询旺旺币消费详细记录
	 * @param wwbAccount
	 * @return
	 */
	public List<WwbUseRecord> getWwbUseRecordDetail(@Param("wwbAccount") WwbAccount wwbAccount);
	
}
