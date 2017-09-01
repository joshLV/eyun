package com.rainsoft.union.web.smsmanage.controller;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.smsmanage.model.SmsManage;
import com.rainsoft.union.web.smsmanage.service.ISmsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.sql.SQLException;
/**
 * 
 * @author huxiaolong
 * @created 2016年6月23日 下午3:32:31
 */
@Controller
@RequestMapping("/smsManage/sms")
@Log(clazzDesc = "短信管理")
public class SmsController extends SpringBaseController<SmsManage, String>{

	private final String PREFIX = "/smsmanage/sms";
	
	@Resource
	private ISmsService smsService;
	
	@Override
	protected IMybatisBasePersitenceService<SmsManage, String> getBaseService() {
		
		return smsService;
	}

	@Override
	protected String getPrefix() {
		
		return PREFIX;
	}
	
	/**
	 * @Description: 跳转到短信管理页面
	 *
	 */
	@RequestMapping("/toSms")
	public String toSms(Model model) {
		model.addAttribute("creator", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	
	/**
	 * 
	 * @Description:查询短信使用记录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSmsUseRecord", method = RequestMethod.POST)
	public void getSmsUseRecord(@ModelAttribute SmsManage smsURE,Model model) {
		
		this.ReturnDataTableAjaxPost(smsURE, "getSmsUseRecord");
		
	}
	
	
	
    /**
     * 查询短信使用记录汇总数据
     */
    @RequestMapping(value = "/getUseRecSta", method = RequestMethod.POST)
	public void getUseRecSta(@ModelAttribute SmsManage smsURE){
    	
		this.responseAjaxData(smsService.getUseRecSta(smsURE));
		
	}
	
    
    /**
	 * 短信使用明细
	 * 
	 * @return
	 */
    @RequestMapping(value = "/getSmsUseDetail", method = RequestMethod.POST)
	public void getSmsUseDetail(@ModelAttribute SmsManage smsUser) {
		Result result = new Result();
		result = smsService.smsUseDetail(smsUser);
		this.responseAjaxData(result);
	}
    
	/**
	 * 
	 * @Description:查询短信购买记录
	 * @param smsURE
	 */
	@RequestMapping(value = "/getSmsBuyRecord", method = RequestMethod.POST)
	public void getSmsBuyRecord(@ModelAttribute SmsManage smsURE) {
		
		this.ReturnDataTableAjaxPost(smsURE, "getSmsBuyRecord");
	}
	
	/**
	 * 
	 * @Description:查询场所列表
	 * @param smsURE
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/getPlaceList", method = RequestMethod.POST)
	public void getPlaceList(){
		PlaceData placeData = new PlaceData();
		Result result = new Result();
		placeData.setUserId(SpringMvcUtil.getUserId());
		try {
			result = smsService.getPlaceList(placeData, getGridData());
			result.setMessage("SUCC");
		} catch (Exception e) {
			result.setMessage("获取场所列表失败");
			logger.error("获取场所列表失败！" + e);
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), result);
	}
	
	/**
	 * 跳转到短信购买页面
	 */
	@RequestMapping("/toSmsAdd")
	public void toSmsAdd(@ModelAttribute SmsManage sms) {
		Result smsu = smsService.getAcctBal(sms);
		this.responseAjaxData(smsu);
	}
	
	/**
	 * 购买短信
	 */
	@Log(methodDesc = "购买短信")
    @RequestMapping("/buySms")
	public void buySms(@ModelAttribute SmsManage sms){
		sms.setUserId(SpringMvcUtil.getUserId());
		Result result = null;
    	try {
    		result = smsService.saveSms(sms);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("购买短信失败！" + e);
		}
    	SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }
}
