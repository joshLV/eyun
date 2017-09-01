package com.rainsoft.union.web.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.account.model.WwbAccount;
import com.rainsoft.union.web.account.service.IWwbAccountService;
import com.rainsoft.union.web.pubdata.model.DeviceData;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log(clazzDesc = "旺旺币账户管理")
@RequestMapping("/account/wwbAccount")
public class WwbAccountController extends SpringBaseController<WwbAccount, String>{
	
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
	 * 跳转到旺旺币购买页面
	 * @param model 初始化场所和设备信息
	 * @return page
	 */
	@RequestMapping("/toWwbAccount")
	public String toWwbAccount(Model model){
		PlaceData placeData = new PlaceData();
		DeviceData deviceData = new DeviceData();
		placeData.setUserId(SpringMvcUtil.getUserId());
		try {
			List <PlaceData> placeList = pubDataServiceImpl.getPlaceList(placeData, getGridData());
			if(placeList.size() < 1){
				placeList = new ArrayList<>();
				placeData.setPlaceCode("-1");
				placeData.setPlaceName("无场所数据");
				placeList.add(placeData);
			} else {
				List <DeviceData> dList = pubDataServiceImpl.getBillDeviceList(placeList.get(0).getPlaceCode());
				if(dList.size()<1){
					dList = new ArrayList<>();
					deviceData.setSerial_num("没有场所设备");
					deviceData.setId(-1);
					dList.add(deviceData);
				}
				model.addAttribute("deviceList", dList);
			}
			model.addAttribute("placeList", placeList);
		} catch (Exception e) {
			logger.error("获取场所列表失败！" + e);
		}
		return getPrefix()+"Account";
	}
	/**
	 * 
	 * @Description: 获取场所计费设备信息
	 * @param placeCode  
	 * @return void  
	 * @throws 
	 * @author yty
	 * @date 2016年4月13日下午1:29:41
	 */
	@RequestMapping("/getDeviceList")
	public void getDeviceList(@RequestParam("placeCode") String placeCode) {
		JSONObject datas = new JSONObject();
		DeviceData deviceData = new DeviceData();
		try {
			List< DeviceData> devList = pubDataServiceImpl.getBillDeviceList(placeCode);
			if(devList==null || devList.size() < 1) {
				devList = new ArrayList<>();
				deviceData.setId(-1);
				deviceData.setSerial_num("无当前场所设备信息");
				devList.add(deviceData);
			}
			datas.put("deviceList", devList);
		} catch (Exception e) {
			logger.error("获取场所设备列表失败！" + e);
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), datas);
	}
	
	/**
	 * 
	 * @Description: 跳转到旺旺币充值页
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2016年3月16日上午10:34:06
	 */
	@RequestMapping("/toWwbBuy")
	public String toWwbBuy(@ModelAttribute WwbAccount wwbAccount, Model model) {
		wwbAccount.setTotalGiveWwb(wwbAccount.getTotalUseGiveWwb());
		wwbAccount.setUseType("7");
		model.addAttribute("wwbAccount", wwbAccount);
		return getPrefix() + "AffirmPay";
	}
	
	/**
	 * 
	 * @Description: 旺旺币充值
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年3月16日上午10:34:39
	 */
	@RequestMapping("/wwbBuy")
	@Log(methodDesc = "购买旺旺币")
	public void wwbBuy(@RequestParam("enCode") String enCode) {
		Result result = new Result();
		try {
			String str = baseDecode(enCode);
			if(null != str){
				DeviceData deviceData = new DeviceData();
				String [] buyInfo = str.split(",");
				deviceData.setPlaceCode(buyInfo[3]);
				deviceData.setUserId(SpringMvcUtil.getUserId());
				deviceData.setSerial_num(buyInfo[5]);
				if(wwbAccountService.checkUserBuyInfo(deviceData, getGridData())){
					wwbAccountService.saveWwbBuy(buyInfo, result);
				} else {
					result.setMessage("未找到设备，购买失败！");
					SpringMvcUtil.responseJSONWriter(getResponse(), result);
				}
			} else {
				result.setMessage("加密异常，购买失败！");
			}
		} catch (Exception e) {
			SpringMvcUtil.responseJSONWriter(getResponse(), result);
			logger.error("旺旺币购买失败:"+e);
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), result);
	}
	
	/**
	 * 
	 * @Description: 获取旺旺币账户信息
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2016年3月16日上午10:35:26
	 */
	@RequestMapping("/getWwbAccount")
	public void getWwbAccount(@ModelAttribute WwbAccount wwbAccount) {
		wwbAccount.setUserId(SpringMvcUtil.getUserId());
		wwbAccount = wwbAccountService.getWwbAccountBal(wwbAccount);
		SpringMvcUtil.responseJSONWriter(getResponse(), wwbAccount);
	}
	/**
	 * 
	 * @Description: 检查人民币账户余额
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2016年3月16日上午10:35:26
	 */
	@RequestMapping("/checkAccBalance")
	public void checkAccBalance(@RequestParam("balance") BigDecimal balance){
		if(wwbAccountService.checkAccBalance(balance)){
			SpringMvcUtil.responseWriter(getResponse(), "1");
		} else {
			SpringMvcUtil.responseWriter(getResponse(), "0");
		}
	}
	
	
	
	/**
	 * Base64解密
	 * @param encode Base64加密字符串
	 * @return 解密后的字符串
	 */
	private String baseDecode(String encode){
		String userKeyStr = null;
		if(StringUtil.isEmpty(encode)){
			return userKeyStr;
		}
		try {
			byte[] userKeyCode = Base64.decodeBase64(encode.getBytes("UTF-8"));
			userKeyStr = new String(userKeyCode);
		} catch (UnsupportedEncodingException e) {
			logger.error("旺旺币充值解密失败:"+e);
		}
		return userKeyStr;
	}
	
	
	/**
	 * Base64加密
	 * @param str
	 * @return 加密字符串
	 */
	@SuppressWarnings("unused")
	private String baseEncode(String str){
		if(StringUtil.isEmpty(str)){
			return null;
		}
		String encode = new String(Base64.encodeBase64Chunked(str.getBytes()));
		return encode;
	}
}
