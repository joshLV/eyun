package com.rainsoft.union.web.sysmanage.controller;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.SurfWebSet;
import com.rainsoft.union.web.sysmanage.service.ISurfWebSetService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/system/surfWebSet")
@Log(clazzDesc = "上网设置")
public class SurfWebSetController extends SpringBaseController<SurfWebSet, String> {
	
	private final String PREXIF = "/system/surfWebSet";
	@Resource
	private ISurfWebSetService surfWebSetService;

	@Override
	protected String getPrefix() {
		return PREXIF;
	}

	@Override
	protected IMybatisBasePersitenceService<SurfWebSet, String> getBaseService() {
		return surfWebSetService;
	}

	/**
	 * 
	 * 跳转到上网设置页面
	 * @param model
	 * @return String  
	 */
	@RequestMapping("/toSurfWebSet")
	public String toSurfWebSet(Model model){
		String placeCode = "-1";
		String strPlaceCode = SpringMvcUtil.getParameter("placeCode");
		if (StringUtil.isNotEmpty(strPlaceCode)) {
			placeCode = strPlaceCode;
		}
		SpringMvcUtil.setDefaultPalceCode(placeCode);
		model.addAttribute("creator", SpringMvcUtil.getUserId().toString());
		return super.toList(model);
	}
	
	/**
	 * 
	 * 更新上网设置
	 * @param surfWebSet 上网设置实体   
	 * @return void  
	 */
	@Log(methodDesc = "更新上网设置")
	@RequestMapping(value="/updateSurfWebSet", method=RequestMethod.POST)
	public void updateSurfWebSet(@ModelAttribute SurfWebSet surfWebSet) {
		Result result = new Result();
		try {
			result = surfWebSetService.updateSurfWebSet(surfWebSet, getRequest());
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		this.responseAjaxData(result);
	}
}
