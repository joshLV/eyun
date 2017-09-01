package com.rainsoft.union.web.sysmanage.service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.ParamFilter;


public interface IWhiteService extends IMybatisBasePersitenceService {
	/**
	 * 获取白名单列表
	 * @param param 参数
	 * @param gridData 分页对象
	 * @return List<WhiteEntity>
	 */
	public JSONObject getWhiteList(ParamFilter param, PageBean gridData) ;
	
	/**
	 * 保存白名单列表
	 * @param param {
	 * 		placeCode [场所编码]
	 * 		,mobile [手机号]
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * @return	新白名单ID: 成功，用大于0判断
	 *    		-1: 操作失败;
	 *    		-2: 该号码已经存在;
	 * }
	 */
	public Result saveWhiteList(ParamFilter param, Integer memberId) throws Exception;

	/**
	 * 保存白名单列表
	 * @param param {
	 * 		placeCode [场所编码]
	 * 		,mobile [手机号]
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * @return	新白名单ID: 成功，用大于0判断
	 *    		-1: 操作失败;
	 *    		-2: 该号码已经存在;
	 * }
	 */
	public Result updateWhiteInfo(ParamFilter param, Integer memberId) throws Exception;
	
	/**
	 * 删除白名单
	 * @param param{
	 * 		id [白名单主键]
	 * @return	0: 成功,
	 *    	   -1: 操作失败;
	 * }
	 */
	public Result delWhiteList(ParamFilter param, Integer memberId) throws Exception;


}
