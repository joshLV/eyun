package com.rainsoft.union.web.account.dao;


import com.rainsoft.union.web.account.model.WwbAccount;
import org.apache.ibatis.annotations.Param;

public interface IWwbAccountDao {
	
	/**
	 * 获取旺旺币账户信息
	 * @param wwbAccount
	 * @return
	 */
	public WwbAccount getWwbAccount(@Param("wwbAccount") WwbAccount wwbAccount);
	/**
	 * 更新账户总使用金额 
	 * @param wwbAccount
	 * @return
	 */
	public Integer updateAccAccount(@Param("wwbAccount") WwbAccount wwbAccount);
	
	/**
	 * 更新旺旺币总充值金额加此次充值金额
	 * @param wwbAccount
	 * @return
	 */
	public Integer updateWwbAccount(@Param("wwbAccount") WwbAccount wwbAccount);
	
	/**插入旺旺币总充值金额加此次充值金额
	 * @param wwbAccount
	 * @return
	 */
	public Integer insertWwbAccount(@Param("wwbAccount") WwbAccount wwbAccount);
	
	/**
	 * 插入账户消费记录  
	 * @param wwbAccount
	 * @return
	 */
	public Integer insertBllUseWWBiRecords(@Param("wwbAccount") WwbAccount wwbAccount);
	
	/**
	 * 插入旺旺币充值记录
	 * @param wwbAccount
	 * @return
	 */
	public Integer insertRechargewwbrecord(@Param("wwbAccount") WwbAccount wwbAccount);
	

	
	/** 
	 * 根据userID,和 场所设备placeDeviceId查询用户账户信息
	 */
	public WwbAccount getWwbAccoutInfo(@Param("wwbAccount") WwbAccount wwbAccount);

	/**
	 *  根据账号获取旺旺币余额，只是当前账号下面的
	 * @author qianna
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Double getBalanceByUserId(@Param("memberId") Integer memberId) throws Exception;
	
}
