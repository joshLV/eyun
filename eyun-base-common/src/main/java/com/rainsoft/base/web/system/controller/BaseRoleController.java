package com.rainsoft.base.web.system.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.service.IBaseRoleService;

@Controller
@RequestMapping("/system/baserole")
@Log(clazzDesc = "角色管理")
public class BaseRoleController extends SpringBaseController<BaseRole, String> {

	private final String PREXIF = "/system/role";
	@Resource
	private IBaseRoleService baseRoleService;

	@Override
	protected IMybatisBasePersitenceService<BaseRole, String> getBaseService() {
		return baseRoleService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}

	/**
	 * List页面跳转（Jqgrid）
	 * 
	 * @Description: TODO
	 * @param @param model
	 * @param @return
	 * @return String
	 * @throws
	 * @author yty
	 * @date 2015年12月2日下午13:20:21
	 */
	@RequestMapping("/toRoleList")
	protected String toRoleList(Model model) {
		List<BaseResource> resourceList = baseRoleService.getResource();
		List<BaseResource> resource = new ArrayList<BaseResource>();
		if(resourceList != null && resourceList.size() > 0) {
			//拿到1级菜单
			for(BaseResource res : resourceList) {
				if(res.getPid() == -1) {
					resource.add(res);
				}
			}
			//把2级菜单设入对应菜单的子菜单
			for(BaseResource re : resource) {
				re.setSubResource(new ArrayList<BaseResource>());
				for(BaseResource res : resourceList){
					if(res.getPid() == re.getId()) {
						re.getSubResource().add(res);
					}
				}
			}
			//把3级菜单设入对应菜单的子菜单
			for(BaseResource re : resource) {
				for(BaseResource res : re.getSubResource()) {
					res.setSubResource(new ArrayList<BaseResource>());
					for(BaseResource reso : resourceList) {
						if(reso.getPid() == res.getId()) {
							res.getSubResource().add(reso);
						}
					}
				}
			}
		}
		model.addAttribute("resource", resource);
		return super.toList(model);
	}
	
	/**
	 * 
	 * @Description: 获取角色列表
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月3日下午3:04:14
	 */
	@RequestMapping(value = "/getGridList", method = RequestMethod.POST)
	public void getGridList(@ModelAttribute BaseRole baseRole) {
		baseRole.setCreator(SpringMvcUtil.getUserId().toString());
		super.loadGrid(baseRole);
	}
	
	/**
	 * 
	 * @Description: 新增角色
	 * @param @param baseRole   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月5日下午3:10:56
	 */
	@Log(methodDesc = "新增角色")
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public void saveRole(@ModelAttribute BaseRole baseRole) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		baseRole.setCreator(SpringMvcUtil.getUserId().toString());
		Result result = null;
		try {
			result = baseRoleService.saveRole(baseRole, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("新增角色失败！" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description: 修改角色
	 * @param @param baseRole   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年5月5日下午3:11:04
	 */
	@Log(methodDesc = "修改角色")
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public void updateRole(@ModelAttribute BaseRole baseRole) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		baseRole.setUpdator(SpringMvcUtil.getUserId().toString());
		Result result = null;
		try {
			result = baseRoleService.updateRole(baseRole, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("修改角色失败！" + e);
		}
		this.responseAjaxData(result);
	}

	/**
	 * 
	 * @Description: 删除角色
	 * @param
	 * @return void
	 * @throws
	 * @author yty
	 * @date 2015年12月3日下午4:31:20
	 */
	@Log(methodDesc = "删除角色")
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	public void delRole(@ModelAttribute BaseRole baseRole) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = null;
		try {
			result = baseRoleService.delRole(baseRole, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("删除角色失败！" + e);
		}
		this.responseAjaxData(result);
	}
	
	/**
	 * 
	 * @Description: 更新权限
	 * @param @param baseRole   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2015年12月18日上午10:31:59
	 */
	@Log(methodDesc = "分配权限")
	@RequestMapping(value = "/uptAuthority", method = RequestMethod.POST)
	public void uptAuthority(@ModelAttribute BaseRole baseRole) throws Exception {
		RequestContext requestContext = new RequestContext(getRequest());
		Result result = null;
		try {
			result = baseRoleService.updateAuthority(baseRole, requestContext);
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.opt.fail"));
			logger.error("分配权限失败！" + e);
		}
		this.responseAjaxData(result);
	}
}
