package com.rainsoft.base.web.system.service;

import org.springframework.web.servlet.support.RequestContext;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseResource;

public interface IBaseResourceService extends IMybatisBasePersitenceService<BaseResource, String> {
	/**
	 * 
	 * @Description: 添加资源
	 * @param @param resource
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author yty
	 * @date 2015年12月16日下午7:06:08
	 */
	public Result addResource(BaseResource resource, RequestContext requestContext) throws Exception;
	
	/**
	 * 
	 * @Description: 修改资源
	 * @param @param resource
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author yty
	 * @date 2015年12月16日下午7:06:24
	 */
	public Result updateResource(BaseResource resource, RequestContext requestContext) throws Exception;
	
	/**
	 * 
	 * @Description: 删除资源
	 * @param @param resource
	 * @param @param request
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author yty
	 * @date 2016年5月3日下午6:22:57
	 */
	public Result deleteResource(BaseResource resource, RequestContext requestContext) throws Exception;
}
