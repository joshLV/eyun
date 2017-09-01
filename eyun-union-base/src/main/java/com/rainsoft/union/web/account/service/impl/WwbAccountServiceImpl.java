package com.rainsoft.union.web.account.service.impl;

import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.account.dao.IWwbAccountDao;
import com.rainsoft.union.web.account.model.AccAccount;
import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.account.service.IAccAccountService;
import com.rainsoft.union.web.account.service.IWwbAccountService;
import com.rainsoft.union.web.pubdata.model.DeviceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
@Service("wwbAccountService")
public class WwbAccountServiceImpl implements IWwbAccountService{
	
	@Resource
	private IWwbAccountDao iWwbAccountDao;
	
	@Resource
	private PubDataService pubDataServiceImpl;
	
	@Resource
	private IAccAccountService accAccountService;
	
	
	/**
	 * 查询旺旺币账户信息
	 */
	public WwbAccount getWwbAccountBal(WwbAccount wwbAccount) {
		
		return iWwbAccountDao.getWwbAccount(wwbAccount);
	}

	/**
	 * 旺旺币购买相关数据持久化操作
	 */
	@Override
	public void saveWwbBuy(String[] buyInfo,Result result) throws Exception {
		boolean pass = false; 
		Integer userid = SpringMvcUtil.getUserId();
		AccAccount accAccount = accAccountService.findBy("getAccountBal",userid);
		WwbAccount wwbAccount = new WwbAccount();
		if(null != buyInfo){
			BigDecimal useRmb = new BigDecimal(buyInfo[1]);
			BigDecimal wwbBalance = new BigDecimal(buyInfo[7]);
			wwbAccount.setId(Integer.valueOf(buyInfo[2]));
			//当前用户id menberid
			wwbAccount.setUserId(userid);
			//当前需要充值的场所设备id
			wwbAccount.setPlaceDeviceId(Integer.valueOf(buyInfo[4]));
			//当前充值的场所Code
			wwbAccount.setPlaceCode(buyInfo[3]);
			//本次购买旺旺币数量
			wwbAccount.setTotalUseGiveWwb(new BigDecimal(buyInfo[0]));
			//本次充值使用账户金额
			wwbAccount.setTotalGiveWwb(useRmb);
			//用户账户总支出金额，需查出用户账户表总支出金额相加本次的消费额度 属AccAcconut #{wwbAccount.totalUseWwb}<!--账户总支出数  -->
			wwbAccount.setTotalUseWwb(accAccount.getTotalusewwbifee().add(useRmb));
			//旺旺币总余额（加上本次充值之后）
			wwbAccount.setBalance(wwbBalance.add(useRmb));
			//此次账户消费类型
			wwbAccount.setUseType(buyInfo[6]);
			WwbAccount wwbAcc = getWwbAccoutInfo(wwbAccount);
			if(null != wwbAcc){
				//此处标记若不为null说明该该设备旺旺币账户已经存在，直接update
				wwbAccount.setStatus("Y");
				//总充值旺旺币数额
				wwbAccount.setTotalBuyWwb(wwbAcc.getTotalBuyWwb().add(useRmb));
			} else {
				//总充值旺旺币数额
				wwbAccount.setTotalBuyWwb(useRmb);
			}
			Integer acc = iWwbAccountDao.updateAccAccount(wwbAccount);
			if(null != acc){
				if("Y".equals(wwbAccount.getStatus())){
					acc = iWwbAccountDao.updateWwbAccount(wwbAccount);
				}else{
					acc = iWwbAccountDao.insertWwbAccount(wwbAccount);
				}
				if(null != acc){
					acc = iWwbAccountDao.insertBllUseWWBiRecords(wwbAccount);
					if(null != acc){
						acc = iWwbAccountDao.insertRechargewwbrecord( wwbAccount);
						if(null != acc){
							pass = true;
							result.setStatus("SUCC");
							result.setMessage("购买成功");
						}
					}
				}
			}
			if(pass == false){
				result.setMessage("购买失败，请重新操作！");
				throw new Exception("购买失败！");
			}
		} else {
			result.setMessage("系统发生异常，请重新操作！");
			throw new Exception("购买失败！");
		}
	}

	/** 
	 * 根据userID,和 场所设备placeDeviceId查询用户账户信息
	 */
	@Override
	public WwbAccount getWwbAccoutInfo(WwbAccount wwbAccount) {
		
		return iWwbAccountDao.getWwbAccoutInfo(wwbAccount);
	}
	
	/**
	 * 验证用户充值信息是否正确
	 */
	@Override
	public Boolean checkUserBuyInfo(DeviceData deviceData ,PageBean page){
		List <DeviceData> devlist = null; 
		try {
			devlist = pubDataServiceImpl.getDeviceList(deviceData, page);
		} catch (Exception e) {
			return false;
		}
		if(devlist.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @Description: 检查人民币账户余额
	 * @date 2016年6月16日上午10:35:26
	 */
	@Override
	public Boolean checkAccBalance(BigDecimal balance) {
		AccAccount acc = accAccountService.findBy("getAccountBal",SpringMvcUtil.getUserId());
		int num = 0;
		if(null != acc){
			num = acc.getAccountBal().compareTo(balance);
			return num >= 0 ? true : false; 
		}
		return false;
	}

	@Override
	public Double getBalanceByUserId(Integer memberId) {
		if(memberId != null && memberId > 0){
			try {
				return iWwbAccountDao.getBalanceByUserId(memberId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
