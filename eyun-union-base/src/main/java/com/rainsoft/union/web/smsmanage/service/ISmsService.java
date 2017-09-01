/**
 * 短信管理
 */
package com.rainsoft.union.web.smsmanage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.smsmanage.model.SmsManage;

public interface ISmsService extends IMybatisBasePersitenceService<SmsManage, String>{

	/**
	 * 查询短信发送条数
	 */
   public SmsManage getSentSmsNum(SmsManage smsURE);

   
   /**
    * 购买短信
    */
   public Result saveSms(SmsManage sms) throws Exception;

   /**
    * 查询用户短信使用总和
    */
   public Result getUseRecSta(SmsManage smsURE);
   
   /**
	 * 获取会员短信余额
	 */
	public SmsManage getMemberSmsRemain(SmsManage sms);
   
   /**
    * 获取会员场所短信余额
    */
   public SmsManage getPlacebllSmsRemain(SmsManage sms);

   
   /**
    * 获取人民币账户余额
    */
   public Result getAcctBal(SmsManage sms);


   /**
	* 获取短信使用详细信息
	*/
	public Result smsUseDetail(SmsManage sms);
	   
   /**
    * 获取场所已分配未使用短信余额
    */
   public SmsManage getAssignedUnusedSms(SmsManage sms);


   public Result getPlaceList(PlaceData placeData, PageBean gridData) throws Exception;
   
}