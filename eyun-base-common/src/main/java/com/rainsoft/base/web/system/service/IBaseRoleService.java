package com.rainsoft.base.web.system.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseRole;
import org.springframework.web.servlet.support.RequestContext;

import java.util.List;

public interface IBaseRoleService extends IMybatisBasePersitenceService<BaseRole, String> {
	/**
	 * 
	 * @Description: TODO
	 * @param @param baseRole
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月2日下午4:42:09
	 */
	public Result saveRole(BaseRole baseRole, RequestContext requestContext) throws Exception;

	/**
	 * 
	 * @Description: TODO
	 * @param @param baseRole
	 * @param @return
	 * @return Result
	 * @throws
	 * @author yty
	 * @date 2015年12月2日下午4:42:01
	 */
	public Result updateRole(BaseRole baseRole, RequestContext requestContext) throws Exception;

	/**
	 * 
	 * @Description: 删除角色
	 * @param @param baseRole
	 * @param @return
	 * @return Integer
	 * @throws
	 * @author yty
	 * @date 2015年12月3日下午4:24:06
	 */
	public Result delRole(BaseRole baseRole, RequestContext requestContext) throws Exception;
	
	/**
	 * 
	 * @Description: 获取资源
	 * @param @return   
	 * @return List<ResourceManager>  
	 * @throws
	 * @author yty
	 * @date 2015年12月17日下午4:26:06
	 */
	public List<BaseResource> getResource();
	
	/**
	 * 
	 * @Description: 更新权限
	 * @param @param baseRole
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author yty
	 * @date 2015年12月18日上午10:27:42
	 */
	public Result updateAuthority(BaseRole baseRole, RequestContext requestContext) throws Exception;
}
