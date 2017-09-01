package com.rainsoft.base.web.system.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.dao.IBaseDictDao;
import com.rainsoft.base.web.system.model.BaseDict;
import com.rainsoft.base.web.system.service.IBaseDictService;

/**
 * 
 * 数据字典
 *
 */
@Service("baseDictService")
public class BaseDictServiceImpl extends MybatisBasePersitenceServiceImpl<BaseDict, String> implements IBaseDictService {

	@Resource
	private IBaseDictDao baseDictDao;
	
	@Override
	protected IMybatisPersitenceDao<BaseDict, String> getBaseDao() {
		return baseDictDao;
	}

	
	/**
	 * 保存数据字典
	 */
	public Result saveDict(BaseDict dict,HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		BaseDict dDict = getBaseDao().findBy("findDict", dict);
		if(dDict != null){
			result.setStatus(Constants.RETURN_EXIST);
			result.setMessage(requestContext.getMessage("此数据已在被使用！"));
			return result;
		}
		Integer count = getBaseDao().save(dict);
		if (count != null && count > 0) {
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("保存成功!"));
		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("保存失败!"));
		}
		return result;
	}


	/**
	 * 修改数据字典
	 */
	public Result updateDict(BaseDict dict,HttpServletRequest request) throws Exception {
			RequestContext requestContext = new RequestContext(request);
			Result result = new Result();
			Integer count = getBaseDao().update(dict);
			if(count !=null && count > 0){
				result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage(requestContext.getMessage("修改成功!"));
			}else {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("修改失败!"));
			}
			return result;
	}


	/**
	 * 删除数据字典
	 */
	public Result deleteDict(BaseDict dict,HttpServletRequest request) throws Exception {
			RequestContext requestContext = new RequestContext(request);
			Result result = new Result();
			Integer count = getBaseDao().deleteBy("deleteByCode", dict);
			if(count != null && count > 0){
				result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage(requestContext.getMessage("删除成功!"));
			}else {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("删除失败!"));
			}
			return result;
	}
}