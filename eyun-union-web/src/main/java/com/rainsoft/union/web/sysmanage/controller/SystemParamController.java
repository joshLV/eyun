package com.rainsoft.union.web.sysmanage.controller;


import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.SystemParam;
import com.rainsoft.union.web.sysmanage.service.ISystemParamService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/systemParam")
@Log(clazzDesc = "系统参数")
public class SystemParamController extends SpringBaseController<SystemParam, String> {
	private final static String PREFIX = "/system/systemParam";
	@Resource
	private ISystemParamService systemParamService;
	@Override
	protected IMybatisBasePersitenceService<SystemParam, String> getBaseService() {
		return systemParamService;
	}

	@Override
	protected String getPrefix() {
		
		return PREFIX;
	}
	
	/**
	 * 跳转到系统设置页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSystemParam")
	public String toSystemSet(Model model) {
		String placeCode = "-1";
		String strPlaceCode = SpringMvcUtil.getParameter("placeCode");
		if (StringUtil.isNotEmpty(strPlaceCode)) {
			placeCode = strPlaceCode;
		}
		SpringMvcUtil.setDefaultPalceCode(placeCode);
		model.addAttribute("creator", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	
	/**
	 * @Description: 更新系统参数
	 * @param @param systemParam   
	 * @return void  
	 */
	@Log(methodDesc = "更新系统参数")
	@RequestMapping(value="/updSysParam", method=RequestMethod.POST)
	public void updSysParam(@ModelAttribute SystemParam systemParam){
		Result result = null;
		try {
			result = systemParamService.updateSysParam(systemParam, getRequest());
		} catch (Exception e) {
			result.setMessage("更新失败");
			result.setStatus(Constants.RETURN_ERROR);
			logger.error("更新失败：" + e);
			e.printStackTrace();
		}
		this.responseAjaxData(result);
	}
}
