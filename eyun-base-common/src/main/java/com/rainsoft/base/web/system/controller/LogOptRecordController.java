package com.rainsoft.base.web.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.LogOptRecord;
import com.rainsoft.base.web.system.service.ILogOptRecordService;
@Controller
@RequestMapping("/system/log")
public class LogOptRecordController extends SpringBaseController<LogOptRecord, String> {
	private final static String PREXIF = "/system/log";
	@Resource
	private ILogOptRecordService logOptRecordService;
	@Override
	protected IMybatisBasePersitenceService<LogOptRecord, String> getBaseService() {
		return logOptRecordService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}
	
	/**
	 * 
	 * @Description: 跳转到日志页面
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2015年12月14日下午4:48:06
	 */
	@RequestMapping("/toLog")
	public String toLog(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", SpringMvcUtil.getUserId());
		List<BaseCompany> userList = logOptRecordService.getUserList(map);
		model.addAttribute("userList", userList);
		return super.toList(model);
	}
	
	/**
	 * 
	 * @Description: 获取日志列表
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月3日下午3:04:14
	 */
	@RequestMapping(value = "/getGridList", method = RequestMethod.POST)
	public void getGridList(@ModelAttribute LogOptRecord logOptRecord) {
		logOptRecord.setCreator(SpringMvcUtil.getUserId().toString());
		super.loadGrid(logOptRecord);
	}
}
