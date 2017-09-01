/**短信管理实现类
 * @Description:
 * @author:王乾
 * @date:2016年4月19日下午4:10:38
 */
package com.rainsoft.union.web.smsmanage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.account.dao.IAccAccountDao;
import com.rainsoft.union.web.account.model.AccAccount;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import com.rainsoft.union.web.smsmanage.dao.ISmsDao;
import com.rainsoft.union.web.smsmanage.model.SmsManage;
import com.rainsoft.union.web.smsmanage.service.ISmsService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("smsService")
public class SmsServiceImpl extends MybatisBasePersitenceServiceImpl<SmsManage, String> implements ISmsService {
    
	@Resource
	private ISmsDao smsDao;
	
	@Resource
	private IAccAccountDao accAccountDao;
	
	@Resource
	private PubDataService pubDataServiceImpl;
	
	@Override
	protected IMybatisPersitenceDao<SmsManage, String> getBaseDao() {
		return smsDao;
	}
	
	/**
	 * 查询短信发送条数
	 */
	@Override
	public SmsManage getSentSmsNum(SmsManage smsURE) {
		smsURE = smsDao.findBy("getSmsUseRecord", smsURE);
		return smsURE;
	}

	/**
	 * 短信购买
	 */
	@Override
	public Result saveSms(SmsManage sms) throws Exception{
		Result result = new Result();
		if(sms.getUseMoney().compareTo(new BigDecimal(0)) <= 0){
			throw new Exception("购买金额不能为负值");
		}
		AccAccount acc = accAccountDao.findBy("getAccountBal", sms.getUserId());
		SmsManage smsu = getMemberSmsRemain(sms);
		sms.setUseType("1");
		sms.setSmsType("01");
		Integer is = null;
		if(null != acc){
			acc.setTotalusewwbifee(acc.getTotalusewwbifee().add(sms.getUseMoney()));
			acc.setUserId(sms.getUserId());
			acc.setId(sms.getPlaceId());
			acc.setRmbFee(sms.getUseMoney());
			acc.setUseType("1");
			sms.setAccountBal(acc.getTotalusewwbifee().add(sms.getUseMoney()));
			is = accAccountDao.update("uptAccInfoUesFee", acc);
		}
		if(null != is){
			is = accAccountDao.save("saveAccUseRecord", acc);
			if(null != is){
				sms.setId(acc.getUseWWBiRecordID());
				is = getBaseDao().save("insertPlaceSmsBuyRecords", sms);
				if(null != is){
					if(-1 == sms.getPlaceId()){
						if(null != smsu){
							sms.setSmsNum(smsu.getSmsNum().add(sms.getSmsNum()));
							is = getBaseDao().update("updateMemberSmsRemain", sms);
						} else {
							is = getBaseDao().save("insertMemberSmsRemain", sms);
						}
					} else {
						smsu = getPlacebllSmsRemain(sms);
						if(null != smsu){
							sms.setUseMoney(smsu.getUseMoney().add(sms.getUseMoney()));
							sms.setSmsNum(smsu.getSmsNum().add(sms.getSmsNum()));
							is = getBaseDao().update("updateBllSmsRemain", sms);
						}else{
							is = getBaseDao().save("insertBllSmsRemain", sms);
						}
					}
					if(null != is){
						result.setStatus("SUCC");
						return result;
					} else {
						throw new Exception("充值失败");
					}
				}
			}
		} else {
			result.setMessage("充值失败");
			throw new Exception("充值失败");
		}
		return result;
	}
	
	/**
	 * 查询用户所有场所短信使用总和
	 */
	@Override
	public Result getUseRecSta(SmsManage smsURE) {
		smsURE.setUserId(SpringMvcUtil.getUserId());
		smsURE.setForCount("smsNum");
		if(smsURE.getSearchValue().equals("")){
			smsURE.setSearchValue(null);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<SmsManage> smsList = smsDao.findListBy("getSmsUseRecord",smsURE,"","");
		SmsManage surplus = getAssignedUnusedSms(smsURE);
		if(null == smsList.get(0)){
			map.put("placeSmsNum","");
		}else{
			map.put("placeSmsNum",smsList.get(0).getTotal());
		}
		smsURE.setForCount("dateRange");
		smsList = smsDao.findListBy("getSmsUseRecord", smsURE,"","");
		if(null == smsList.get(0)){
			map.put("dateRange","");
		}else{
			map.put("dateRange",smsList.get(0).getStartTime()+"一"+smsList.get(0).getEndTime());		
		}
		if(null == surplus){
			map.put("SurplusSms","0 条");
		}else{
			map.put("SurplusSms",surplus.getSmsNum()+" 条");
		}
		Result r = new Result();
		r.setData(map);
		return r;
	}

	/**
	 * 查询当前用户下的场所信息
	 */
	@Override
	public Result getPlaceList(PlaceData place, PageBean bean) throws Exception {
		Result result = new Result();
		List <PlaceData> placeList = pubDataServiceImpl.getPlaceList(place, bean);
		place.setId(-1);
		place.setPlaceCode("-1");
		place.setPlaceName("全部");
		placeList.add(0, place);
		result.setData(placeList);
		return result;
	}

	/**
	 * 会员短信情况表记录
	 */
	public SmsManage getMemberSmsRemain(SmsManage sms){

		return getBaseDao().findBy("getMemberSmsRemain", sms);
	}
	/**
	 * 会员场所短信余额情况表记录
	 */
	public SmsManage getPlacebllSmsRemain(SmsManage sms){
		
		return getBaseDao().findBy("getbllSmsRemain", sms);
	}
	
	/**
	 * 查询当前会员账户余额
	 */
	@Override
	public Result getAcctBal(SmsManage sms) {
		Result result = new Result();
		AccAccount acc = accAccountDao.findBy("getAccountBal", sms.getUserId());
		if(null != acc){
			sms.setAccountBal(acc.getAccountBal());
		}
		result.setData(sms);
		return result;
	}

	/**
	 * 获取短信使用详细信息
	 */
	@Override
	public Result smsUseDetail(SmsManage sms){
		Result result = new Result();
		List <SmsManage> smslist = getBaseDao().selectList("smsUseDetail", sms);
		if(smslist.size() > 0){
			result.setMessage("SUCC");
			result.setData(smslist);
		}else{
			result.setMessage("暂无数据");
		}
		return result;
	}
	
	/**
	 * 获取当前用户的短信余额
	 */
	@Override
	public SmsManage getAssignedUnusedSms(SmsManage sms) {
		
		return getBaseDao().findBy("getSurplusSms", sms);
	}
}