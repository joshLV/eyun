package com.rainsoft.union.web.account.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.account.model.WwbUseRecord;
import com.rainsoft.union.web.account.service.IWwbAccountService;
import com.rainsoft.union.web.account.service.IWwbDetailService;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;

@Controller
@Log(clazzDesc = "旺旺币账户管理")
@RequestMapping("/account/wwbDetail")
public class WwbDetailController extends SpringBaseController<WwbAccount, String>{
	
	@Resource
	private IWwbDetailService wwbDetailService;
	
	@Resource
	private IWwbAccountService wwbAccountService;
	
	@Resource
	private PubDataService pubDataServiceImpl;
	
	@Override
	protected IMybatisBasePersitenceService<WwbAccount, String> getBaseService() {
		return null;
	}

	@Override
	protected String getPrefix() {
		return "/account/wwb";
	}
	
	/**
	 * 跳转到旺旺币账户列表页面
	 * @return
	 */
	@RequestMapping("/toWwbDetail")
 	public String toWwbAccount(Model model){
		PlaceData placeData = new PlaceData();
		placeData.setUserId(SpringMvcUtil.getUserId());
		try {
			List <PlaceData> placeList = pubDataServiceImpl.getPlaceList(placeData, getGridData());
			if(placeList.size() < 1){
				placeList = new ArrayList<PlaceData>();
				placeData.setPlaceCode("-1");
				placeData.setPlaceName("暂无场所信息");
				placeList.add(placeData);
			}
			model.addAttribute("placeList", placeList);
		} catch (Exception e) {
			logger.error("获取场所列表失败！" + e);
		}
		return getPrefix()+"Detail";
	}
	/**
	 * 
	 * @Description: 获取旺旺币消费记录
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年4月13日下午1:29:41
	 */
	@RequestMapping("/getWwbUseRecord")
	public void getWwbUseRecord(@ModelAttribute WwbAccount wwbAccount){
		JSONObject jsonList = null;
		try {
			jsonList = wwbDetailService.getWwbUseRecord(wwbAccount, getGridData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), jsonList);
	}
	
	/**
	 * 
	 * @Description: 跳转到旺旺币充值记录页面
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2016年3月16日上午10:35:26
	 */
	@RequestMapping("/getWwbBuyRecord")
	public void getWwbBuyRecord(@ModelAttribute WwbAccount wwbAccount) {
		JSONObject jsonList = null;
		try {
			jsonList = wwbDetailService.getBuyWwbRecord(wwbAccount, getGridData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), jsonList);
	}
	
	/**
	 * 
	 * @Description: 跳转到旺旺币消费详细记录
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2016年4月19日下午6:35:33
	 */
	@RequestMapping("/getWwbRecordDetail")
	public void getWwbRecordDetail(@ModelAttribute WwbAccount wwbAccount) {
		List<WwbUseRecord> list=null;
		try {
			list= wwbDetailService.getWwbUseRecordDetail(wwbAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), list);
	}

}
