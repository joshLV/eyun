package com.rainsoft.riplat.web.index.service;

import java.util.Map;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.index.model.Index;

public interface IIndexService extends IMybatisBasePersitenceService<Index, String> {

	/**
	 * 方法功能说明：查询新闻、公告列表
	 * @author sh_j_wangwen
	 * @data 2015年12月09号
	 * @param articleType 新闻类型
	 * @return
	 */
	public Result findNewsBy(Map<String,Object> map);
	
	/**
	 * 方法功能说明：根据id查询新闻、公告明细
	 * @author sh_j_wangwen
	 * @data 2015年12月09号
	 * @param articleID 新闻ID
	 * @return
	 */
	public Index findById(String articleID);
	
	
	/**
	 * 方法功能说明：根据id查询头像、旺旺币余额、短信余额
	 * @author sh_j_wangwen
	 * @data 2015年12月11号
	 * @param userId 当前用户id
	 * @return
	 */
	public Result findAccountInfo(Map<String,Object> map);
}