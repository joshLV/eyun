package com.rainsoft.base.web.system.controller;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.qos.logback.classic.Logger;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseDict;
import com.rainsoft.base.web.system.service.IBaseDictService;
import com.rainsoft.base.web.system.service.impl.BaseResourceServiceImpl;



@Controller
@RequestMapping("/system/dict")
@Log(clazzDesc = "数据字典")
public class BaseDictController extends SpringBaseController<BaseDict, String>{
	private static Logger logger = (Logger)LoggerFactory.getLogger(BaseResourceServiceImpl.class);
	
	private final String PREFIX = "/system/dict";
	
	@Resource
	private IBaseDictService baseDictService;
	
	@Override
	protected IMybatisBasePersitenceService<BaseDict, String> getBaseService() {
		
		return baseDictService;
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}
	
	
	/**
	 * 跳转到数据字典页面加载jqgrid
	 *
	 */
	@RequestMapping("/toDict")
	public String toDictionary(Model model) {
		model.addAttribute("creator", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	
	/**
	 * @Description:新增数据字典
	 * @param dict
	 * 
	 */
	@Log(methodDesc = "新增数据字典")
	@RequestMapping(value = "/saveDict", method = RequestMethod.POST)
	public void saveDict(@ModelAttribute BaseDict dict) {
		Result result = new Result();
		try {
			result = baseDictService.saveDict(dict,getRequest());
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("保存失败!");
			logger.error("新增数据字典失败：" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description:修改数据字典
	 * @param dict
	 */
	@Log(methodDesc = "修改数据字典")
	@RequestMapping(value = "/updateDict", method = RequestMethod.POST)
	public void updateDict(@ModelAttribute BaseDict dict) {
		Result result = new Result();
		try {
			result = baseDictService.updateDict(dict,getRequest());
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("修改失败!");
			logger.error("修改数据字典失败：" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description:删除数据字典
	 * @param dict
	 */
	@Log(methodDesc = "删除数据字典")
	@RequestMapping(value = "/deleteDict", method = RequestMethod.POST)
	public void deleteDict(@ModelAttribute BaseDict dict) {
		Result result = new Result();
		try {
			result = baseDictService.deleteDict(dict,getRequest());
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("删除失败!");
			logger.error("删除数据字典失败：" + e);
		}
		this.responseAjaxData(result);
	}
}
