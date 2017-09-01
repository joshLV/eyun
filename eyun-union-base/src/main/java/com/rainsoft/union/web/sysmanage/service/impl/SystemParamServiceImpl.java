package com.rainsoft.union.web.sysmanage.service.impl;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import ch.qos.logback.classic.Logger;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.dao.ISystemParamDao;
import com.rainsoft.union.web.sysmanage.model.SystemParam;
import com.rainsoft.union.web.sysmanage.service.ISystemParamService;

/**
 * 
 * 系统参数
 *
 */
@Service("systemParamService")
public class SystemParamServiceImpl extends MybatisBasePersitenceServiceImpl<SystemParam, String> implements ISystemParamService{
	private static Logger logger = (Logger)LoggerFactory.getLogger(SystemParamServiceImpl.class);
	@Resource
	private ISystemParamDao systemParamDao;
	
	@Override
	protected IMybatisPersitenceDao<SystemParam, String> getBaseDao() {

		return systemParamDao;
	}
	
	/**
	 * 更新系统设置
	 */
	public Result updateSysParam(SystemParam systemParam, HttpServletRequest request) throws Exception { 
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		Integer count = null;
		SystemParam sysParam = null;
		try {
			sysParam = systemParamDao.findBy("getPlaceById", systemParam);
			if(sysParam != null && sysParam.getPlaceId() != null){
				//如果placeId已经存在 修改场所会员加入协议
				count = systemParamDao.update("updateAgreement",systemParam);
				logger.info("修改场所会员加入协议返回结果:"+ count);
			} else {
				//添加场所加入会员协议
				count = systemParamDao.save(systemParam);
				logger.info("新增场所会员加入协议返回结果:"+ count);
			}
			//修改场所会员的认证的方式
			count = systemParamDao.update("updateApprove",systemParam);
			logger.info("修改场所会员认证方式返回结果:"+ count);
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("更新失败"));
			logger.error("更新失败 ：" + e);
			e.printStackTrace();
		}
	
		if (count != null && count > 0 ) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("更新成功"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("更新失败"));
		}
		return result;
	}
}
