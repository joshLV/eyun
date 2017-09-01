package com.rainsoft.base.web.system.service;

import javax.servlet.http.HttpServletRequest;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseDict;

/**
 * 
 * 数据字典
 *
 */
public interface IBaseDictService extends IMybatisBasePersitenceService<BaseDict, String>{

	/**
	 * @Description:保存数据字典
	 * @param dataDictionary 数据字典实体
	 * @param request
	 * @return Result 返回结果
	 * @throws Exception
	 */
	public Result saveDict(BaseDict dataDictionary,HttpServletRequest request) throws Exception;
	
	/**
	 * @Description:修改数据字典
	 * @param dataDictionary  数据字典实体
	 * @param request
	 * @return Result 返回结果
	 * @throws Exception
	 */
	public Result updateDict(BaseDict dataDictionary, HttpServletRequest request) throws Exception;
	
	/**
	 * @Description:删除数据字典
	 * @param dataDictionary 数据字典实体
	 * @param request 
	 * @return  Result  返回结果
	 * @throws Exception
	 */
	public Result deleteDict(BaseDict dataDictionary, HttpServletRequest request) throws Exception;
}
