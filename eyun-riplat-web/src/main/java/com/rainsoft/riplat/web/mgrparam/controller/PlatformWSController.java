package com.rainsoft.riplat.web.mgrparam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.mgrparam.model.Platform;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Controller
@RequestMapping("/Mgr_param")
public class PlatformWSController extends SpringBaseController<IdEntity, Serializable>{

	@Resource
	IPlatformService platformServiceImpl;
	
	@Override
	protected IMybatisBasePersitenceService<IdEntity, String> getBaseService() {
		return null;
	}

	@Override
	protected String getPrefix() {
		return null;
	}
	
	/**
	 * 根据平台id查询平台信息
	 * @param platformId
	 */
	@RequestMapping(value="/getPlatInfoByID", method=RequestMethod.POST)
	public void getPlatInfoByID(@ModelAttribute("params") String params){
		Result result = new Result();
		if(StringUtil.isEmpty(params)){
			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
			result.setMessage("参数不能为空！");
		} else {
			try {
				String platformid = DesUtil.decrypt(params);
				JSONObject json = JSON.parseObject(platformid);
				List<Platform> list = platformServiceImpl.getPlatInfoByID(json.getString("platformId"));
				if(list.size() < 1 ){
					result.setMessage("平台绑定码不存在！");
					result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				}else if(list.size() > 1){
					result.setMessage("平台绑定码存在重复！");//查询参数错误
					result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				}else{
					result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
					result.setData(list);
				}
			} catch (IOException e) {
				result.setMessage("参数错误解密失败！");
				result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				e.printStackTrace();
			} catch (Exception e) {
				result.setMessage("参数错误！");
				result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
			}
		}
		SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
	}
	
	/**
	 * 根据平台ip查询平台信息
	 * @param platformIp
	 */
	/*@RequestMapping(value="/getPlatInfoByIP", method=RequestMethod.POST)
	public void getPlatInfoByIP(@ModelAttribute("params") String params){
		Result result = new Result();
		if(StringUtil.isEmpty(params)){
			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
			result.setMessage("参数不能为空！");
		} else {
			try {
				String platformip = DesUtil.decrypt(params);
				JSONObject json = JSON.parseObject(platformip);
				List<Platform> list = platformServiceImpl.getPlatInfoByIP(json.getString("platformIP"));
				if(list.size() > 1){
					result.setMessage("结果返回多余记录！");
					result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				}else{
					result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
					result.setData(list);
				}
				result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
				result.setData(list);
			} catch (IOException e) {
				result.setMessage("参数错误解密失败！");
				result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				e.printStackTrace();
			} catch (Exception e) {
				result.setMessage("参数错误！");
				result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				e.printStackTrace();
			}
		}
		SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
	}*/
	
}
