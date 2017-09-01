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
import org.springframework.web.servlet.support.RequestContext;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseCompany;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.model.OperateUser;
import com.rainsoft.base.web.system.service.IOperateUserService;

@Controller
@RequestMapping("/system/operateUser")
@Log(clazzDesc = "操作用户")
public class OperateUserController extends SpringBaseController<OperateUser, String> {
	private final String PREXIF = "/system/operateUser";

	@Resource
	private IOperateUserService operateUserService;

	@Override
	protected IMybatisBasePersitenceService<OperateUser, String> getBaseService() {
		return operateUserService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}

	/**
	 * 
	 * @Description: 跳转操作用户页面
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 * @author yty
	 * @date 2015年12月4日下午2:41:37
	 */
	@RequestMapping("/toOperateUserList")
	public String toOperateUserList(Model model) {
		Integer userId = SpringMvcUtil.getUserId();
		//角色列表
		List<BaseRole> roleList = operateUserService.getRoleListByUserId(userId);
		model.addAttribute("roleList", roleList);
		//用户所属角色
		BaseRole creatorRole = operateUserService.getRoleByUserId(userId);
		model.addAttribute("creatorRole", creatorRole);
		//场所列表
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<BaseCompany> places = operateUserService.getPlaceList(map);//riplat项目不需要使用
		model.addAttribute("places", places);
		return super.toList(model);
	}

	/**
	 * 
	 * @Description: 获取操作用户列表
	 * @param @param operateUser
	 * @return void
	 * @throws
	 * @author yty
	 * @date 2015年12月7日上午10:04:37
	 */
	@RequestMapping(value = "/getOperatorList", method = RequestMethod.POST)
	public void getOperatorList(@ModelAttribute OperateUser operateUser) {
		operateUser.setCreator(SpringMvcUtil.getUserId().toString());
		super.loadGrid(operateUser);
	}
	
	/**
	 * 
	 * @Description: 新增操作用户
	 * @param @param operateUser   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月4日上午11:52:08
	 */
	@Log(methodDesc = "新增操作用户")
	@RequestMapping(value = "/saveOperateUser", method = RequestMethod.POST)
	public void saveOperateUser(@ModelAttribute OperateUser operateUser) throws Exception {
		operateUser.setCreator(SpringMvcUtil.getUserId().toString());
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = null;
		try {
			result = operateUserService.saveOperateUser(operateUser, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("新增操作用户失败！" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description: 更新操作用户
	 * @param @param operateUser   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月4日上午11:52:20
	 */
	@Log(methodDesc = "更新操作用户")
	@RequestMapping(value = "/updateOperateUser", method = RequestMethod.POST)
	public void updateOperateUser(@ModelAttribute OperateUser operateUser) throws Exception {
		operateUser.setUpdator(SpringMvcUtil.getUserId().toString());
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = null;
		try {
			result = operateUserService.updateOperateUser(operateUser, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("更新操作用户失败！" + e);
		}
		this.responseAjaxData(result);
	}

	/**
	 * 
	 * @Description: 删除操作用户
	 * @param @param ids
	 * @return void
	 * @throws
	 * @author yty
	 * @date 2015年12月8日上午11:51:54
	 */
	@Log(methodDesc = "删除操作用户")
	@RequestMapping(value = "/deleteOperator", method = RequestMethod.POST)
	public void deleteOperator(@ModelAttribute OperateUser operateUser) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = new Result();
		try {
			result = operateUserService.deleteOperate(operateUser, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("删除操作用户失败！" + e);
		}
		this.responseAjaxData(result);
	}
}
