package com.rainsoft.base.web.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import ch.qos.logback.classic.Logger;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.dao.IBaseResourceDao;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.service.IBaseResourceService;

@Service("baseResourceService")
public class BaseResourceServiceImpl extends MybatisBasePersitenceServiceImpl<BaseResource, String> implements IBaseResourceService {
	private static Logger logger = (Logger)LoggerFactory.getLogger(BaseResourceServiceImpl.class);
	@Resource
	private IBaseResourceDao baseResourceDao;
	@Override
	protected IMybatisPersitenceDao<BaseResource, String> getBaseDao() {
		return baseResourceDao;
	}
	@Override
	public Result addResource(BaseResource resource, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================保存资源开始====================================");
		logger.info("处理结果含义resultType: 1:资源保存成功； 	-1：资源名重复；		-2：该排序位置已存在资源；		-3：保存资源失败；");
		//是否存在名称相同资源
		Integer num = baseResourceDao.findCountByKeyId("checkResourceName", resource);
		logger.info("是否存在名称相同资源返回：" + num);
		//该位置是否存在资源
		if(num == null) {
			num = baseResourceDao.findCountByKeyId("checkResourceSort", resource);
			logger.info("该位置是否存在资源返回：" + num);
			if(num == null) {
				//保存资源
				num = baseResourceDao.save(resource);
				logger.info("保存资源返回：" + num);
				if(num != null && num > 0 && resource.getId() != null && resource.getId() > 0) {
					resultType = 1;
				} else {
					resultType = -3;
				}
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		logger.info("保存资源处理结果resultType：" + resultType);
		logger.info("====================================保存资源结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.system.resouceSave"));
		} else if (resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceName"));
		} else if (resultType == -2) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceseat"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceSave"));
		}
		return result;
	}
	
	@Override
	public Result updateResource(BaseResource resource, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================修改资源开始====================================");
		logger.info("处理结果含义resultType: 1:资源修改成功； 	-1：资源名重复；		-2：该排序位置已存在资源；		-3：修改资源失败；		-4：子资源启用");
		//是否存在名称相同资源
		Integer num = baseResourceDao.findCountByKeyId("checkResourceName", resource);
		logger.info("是否存在名称相同资源返回：" + num);
		if(num == null) {
			//该位置是否存在资源
			num = baseResourceDao.findCountByKeyId("checkResourceSort", resource);
			logger.info("该位置是否存在资源返回：" + num);
			if(num == null) {
				BaseResource oldResource = baseResourceDao.findBy("findById", resource);
				if(!oldResource.getStatus().equals(resource.getStatus())) {
					if("d".equals(resource.getStatus())) {
						List<BaseResource> resourceList = baseResourceDao.selectList("findEffectResource", null);
						boolean flag = checkChildrenResource(resourceList, resource.getId());
						if(flag) {
							resultType = -4;
						}
					}
				}
				if(resultType == null) {
					//修改资源
					num = baseResourceDao.update(resource);
					logger.info("修改资源返回：" + num);
					if(num != null && num > 0) {
						resultType = 1;
					} else {
						resultType = -3;
					}
				}
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		logger.info("修改资源处理结果resultType：" + resultType);
		logger.info("====================================修改资源结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.system.resouceEdit"));
		} else if (resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceName"));
		} else if (resultType == -2) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceseat"));
		} else if (resultType == -4) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouce.childrenResource"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceEdit"));
		}
		return result;
	}
	
	@Override
	public Result deleteResource(BaseResource resource, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType = null;
		logger.info("====================================删除资源开始====================================");
		logger.info("处理结果含义resultType: 1:资源删除成功； 	-1：资源已被使用；		-2：资源删除失败；");
		//资源是否被引用
		Integer num = baseResourceDao.findCountByKeyId("checkOnUse", resource);
		logger.info("资源是否被引用返回：" + num);
		if(num == null) {
			//删除资源
			num = baseResourceDao.deleteBy("deleteById", resource);
			logger.info("删除资源返回：" + num);
			if(num != null && num > 0) {
				resultType = 1;
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		logger.info("删除资源处理结果resultType：" + resultType);
		logger.info("====================================删除资源结束====================================");
		if(resultType == 1) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("info.del"));
		} else if(resultType == -1) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.system.resouceused"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("errors.del"));
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: TODO检查将要禁止使用的菜单的子菜单是否有启用的
	 * @param @param resourceList
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年8月1日下午7:39:30
	 */
	public boolean checkChildrenResource(List<BaseResource> resourceList, Integer id) {
		if(resourceList != null && resourceList.size() > 0) {
			for(BaseResource resource : resourceList) {
				if(resource.getPid() == id) {
					if("启用".equals(resource.getStatus())) {
						return true;
					} else {
						checkChildrenResource(resourceList, resource.getId());
					}
				}
			}
		}
		return false;
	}
}
