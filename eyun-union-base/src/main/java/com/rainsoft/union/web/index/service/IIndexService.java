package com.rainsoft.union.web.index.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.index.model.Index;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * 首页
 *
 */
public interface IIndexService extends IMybatisBasePersitenceService<Index, String> {

	/**
	 * 方法功能说明：查询新闻、公告列表
	 * @author sh_j_wangwen
	 * @data 2015年12月09号
	 * @param articleType 新闻类型
	 * @return
	 */
	public Result findNewsBy(Map<String, Object> map, HttpServletRequest request);
	
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
	public Result findAccountInfo(Map<String, Object> map, HttpServletRequest request);
	
	/**
	 * 
	 * @Description: 访客动态饼图数据
	 * @param @param memberId
	 * @param @return   
	 * @return Result  
	 * @throws
	 * @author yty
	 * @date 2016年1月11日上午11:30:18
	 */
//	public Result findVisitorNumByMemberid(Integer memberId);
}