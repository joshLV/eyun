package com.rainsoft.base.web.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.service.IBaseResourceService;

@Controller
@RequestMapping("/system/resource")
@Log(clazzDesc = "资源管理")
public class BaseResourceController extends SpringBaseController<BaseResource, String> {
	private final static String PREFIX = "/system/resource";
	@Resource
	private IBaseResourceService baseResourceService;
	@Override
	protected IMybatisBasePersitenceService<BaseResource, String> getBaseService() {
		return baseResourceService;
	}

	@Override
	protected String getPrefix() {
		return PREFIX;
	}
	
	/**
	 * 
	 * @Description: 跳转到资源界面
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2015年12月16日上午11:56:20
	 */
	@RequestMapping("/toResource")
	public String toResource(Model model) {
//		List<ResourceManager> resourceList = resourceService.findListBy(null);
//		model.addAttribute("resourceList", resourceList);
//		model.addAttribute("creator", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	
	/**
	 * 
	 * @Description: 获取资源列表
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月3日下午3:04:14
	 */
	@RequestMapping(value = "/getGridList", method = RequestMethod.POST)
	public void getGridList(@ModelAttribute BaseResource resource) {
		resource.setCreator(SpringMvcUtil.getUserId().toString());
		super.loadGrid(resource);
	}
	
	/**
	 * 
	 * @Description: 保存资源
	 * @param @param resource   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月3日下午3:18:14
	 */
	@Log(methodDesc = "新增资源")
	@RequestMapping("/saveResource")
	public void saveResource(@ModelAttribute BaseResource resource) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		resource.setCreator(SpringMvcUtil.getUserId().toString());
		Result result = null;
		try {
			result = baseResourceService.addResource(resource, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("新增资源失败！" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param @param resource   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月3日下午3:21:55
	 */
	@Log(methodDesc = "修改资源")
	@RequestMapping("/updateResource")
	public void updateResource(@ModelAttribute BaseResource resource) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		resource.setUpdator(SpringMvcUtil.getUserId().toString());
		Result result = null;
		try {
			result = baseResourceService.updateResource(resource, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("修改资源失败！" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description: 获取资源列表（动态刷新资源下拉框）
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2015年12月17日上午11:13:41
	 */
	@RequestMapping(value = "/getResourceList", method = RequestMethod.POST)
	public void getResourceList() {
		List<BaseResource> resourceList = baseResourceService.selectList("findEffectResource", null);
		JSONArray jsonArr = new JSONArray();
		for(BaseResource res : resourceList) {
			JSONObject obj = (JSONObject) JSONObject.toJSON(res);
			jsonArr.add(obj);
		}
		getResponse().setContentType("application/octet-stream");
		getResponse().setCharacterEncoding("UTF-8");
		try {
			getResponse().getWriter().print(jsonArr);
			getResponse().getWriter().close();
		} catch (Exception e) {
			logger.error("getResourceList",e);
		}
	}
	
	/**
	 * 
	 * @Description: 删除资源
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2015年12月17日下午3:21:09
	 */
	@Log(methodDesc = "删除资源")
	@RequestMapping(value = "/delResource", method = RequestMethod.POST)
	public void delResource(@ModelAttribute BaseResource resource) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = null;
		try {
			result = baseResourceService.deleteResource(resource, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("删除资源失败！" + e);
		}
		this.responseAjaxData(result);
	}
}
