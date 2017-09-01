package com.rainsoft.union.web.account.service;

import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.pubdata.model.DeviceData;

import java.math.BigDecimal;

public interface IWwbAccountService {

	/**
	 * 查询旺旺币账户信息
	 */
	public WwbAccount getWwbAccountBal (WwbAccount wwbAccount);
	
	/**
	 * 旺旺币购买相关数据持久化操作
	 */
	public void saveWwbBuy (String[] buyInfo ,Result result) throws Exception;
	
	/**
	 * 根据userid和placeDievceid查询旺旺币账户信息
	 */
	public WwbAccount getWwbAccoutInfo(WwbAccount wwbAccount);
	
	/**
	 * 检查旺旺币购买信息是否正确
	 */
	public Boolean checkUserBuyInfo(DeviceData dev,PageBean page);
	
	/**
	 * 检查人民币账户余额是否充足
	 */
	public Boolean checkAccBalance(BigDecimal balance);

	/**
	 *  根据账号获取旺旺币余额，只是当前账号下面的
	 * @author qianna
	 * @param memberId
	 * @return double 旺旺币余额
	 * @throws Exception
	 */
	public Double getBalanceByUserId(Integer memberId);
}
