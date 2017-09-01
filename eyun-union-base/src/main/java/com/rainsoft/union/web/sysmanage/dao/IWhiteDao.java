package com.rainsoft.union.web.sysmanage.dao;


import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.union.web.sysmanage.model.WhiteEntity;

import java.util.List;
import java.util.Map;

public interface IWhiteDao extends IMybatisPersitenceDao {
	/**
	 * 获取白名单列表
	 * @param map 参数{forCount:false(去数据)}
	 * @return List<WhiteEntity>
	 */
	public List<WhiteEntity> getWhiteList(Map<String, Object> map) throws Exception;

	
	/**
	 * 保存白名单列表
	 * @param entity {
	 * 		placeCode [场所编码]
	 * 		,mobile [手机号]
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * @return	新白名单ID: 成功，用大于0判断
	 *    -1: 操作失败;
	 *    -2:必须字段为空
	 *    -3: 数据已经存在(身份证号已经添加过了);
	 *    -4: 场所免认证设备数已达上限，不可以超过10个
	 *    -5: 场所手机号免认证设备数已达上限，不可以超过3个
	 * }
	 */
	public int saveWhiteList(WhiteEntity entity) throws Exception;

	/**
	 * 更新白名单信息
	 * @param entity
	 * 		id [主键]
	 * 	    ,placeCode [场所编码]
	 * 	    ,userName []
	 * 	    ,idCard []
	 * 		,mobile [手机号]
	 * 		,devMac  [设备mac]
	 *
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * 	}
	 * @return
	 *    0：成功
	 *    -1: 操作失败;
	 *    -2:必须字段为空
	 *    -3: 数据已经存在(身份证号已经添加过了);
	 *    -5: 当前场所该手机号免认证设备数已达上限，不可以超过3个
	 * @throws Exception
	 */
	public int updateWhiteInfo(WhiteEntity entity) throws Exception;
	
	/**
	 * 删除白名单
	 * @param entity{
	 * 		id [白名单主键]
	 * 
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * @return	0: 成功,
	 *    	 -1: 操作失败;
	 * }
	 */
	public int delWhiteList(WhiteEntity entity) throws Exception;

	/**
	 * 更新白名单与认证的同步状态
	 * @param entity{
	 *        id
	 *        ,sync2Auth [0:未同步/1:已同步]
	 * }
	 * @return int row
	 */
	public int updateSync2AuthState(WhiteEntity entity) throws Exception;
}
