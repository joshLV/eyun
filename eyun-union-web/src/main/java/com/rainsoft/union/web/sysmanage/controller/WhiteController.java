package com.rainsoft.union.web.sysmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.ParamFilter;
import com.rainsoft.union.web.sysmanage.model.WhiteEntity;
import com.rainsoft.union.web.sysmanage.service.IWhiteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * “姓名”，“身份证号”，“手机号”，这三项是作为实名认证的条件
 *	“易韵号”才是作为设置白名单的条件，手机号不一定就是注册易韵号的手机号
 */
@Controller
@RequestMapping("/account/white")
public class WhiteController extends SpringBaseController{

	private final String PREFIX = "/sysmanage/white/white";
	public List<WhiteEntity> entityList ;
	@Resource
	private IWhiteService whiteServiceImpl;

	/*
	 * 白名单列表页面
	 */
	@RequestMapping("/whitePage")
	public String whitePage(Model model){
		model.addAttribute("creator", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	/*
	 * 白名单列表
	 */
	@RequestMapping( value = "/whiteList" , method = RequestMethod.POST)
	public void whiteList(@ModelAttribute("param")ParamFilter param){
		JSONObject jsonObj = new JSONObject();
		Integer memberId = SpringMvcUtil.getUserId();
		param.setMemberID(memberId);
		try {
			jsonObj = whiteServiceImpl.getWhiteList(param, getGridData());
		} catch (Exception e) {
			logger.error("WhiteController.whiteList-->" + e.toString());
		}

		SpringMvcUtil.responseJSONWriter(getResponse(), jsonObj);
	}
	/*
	 * 新增白名单页面
	 */
	@RequestMapping("/addPage")
	public String addPage(){
		return PREFIX+"addPage";
	}

	/**
	 * 保存白名单
	 */
	@RequestMapping( value = "/saveWhite" , method = RequestMethod.POST)
	public void saveWhite(@ModelAttribute("param")ParamFilter param){
		Integer memberId = SpringMvcUtil.getUserId() ;
		Integer id = param.getId();
		Result result = null;
		if(id != null && id > 0){
			try {
				result = whiteServiceImpl.updateWhiteInfo(param,memberId);
			} catch (Exception e) {
				result = new Result(Constants.RETURN_ERROR,"更新失败",null);
				logger.error("WhiteController.saveWhite-->" + e.toString(),e);
			}
		}else{
			try {
				result = whiteServiceImpl.saveWhiteList(param, memberId);
			} catch (Exception e) {
				result = new Result(Constants.RETURN_ERROR,"保存失败",null);
				logger.error("WhiteController.saveWhite-->" + e.toString(),e);
			}
		}

		responseAjaxData(result);
	}
	/**
	 * 删除白名单
	 */
	@RequestMapping( value = "/delWhite" , method = RequestMethod.POST)
	public void delWhite(@ModelAttribute("param")ParamFilter param){
		Integer memberId = SpringMvcUtil.getUserId() ;
		Result result = null;
		try {
			result = whiteServiceImpl.delWhiteList(param, memberId);
		} catch (Exception e) {
			result = new Result(Constants.RETURN_ERROR,"删除失败",null);
			e.printStackTrace();
		}
		responseAjaxData(result);
	}


	@Override
	protected IMybatisBasePersitenceService getBaseService() {
		return whiteServiceImpl;
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}



	
}
