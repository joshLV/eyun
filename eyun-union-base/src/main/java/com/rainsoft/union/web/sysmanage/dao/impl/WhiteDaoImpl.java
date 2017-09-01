package com.rainsoft.union.web.sysmanage.dao.impl;


import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.sysmanage.dao.IWhiteDao;
import com.rainsoft.union.web.sysmanage.model.WhiteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WhiteDaoImpl extends MybatisPersitenceDaoImpl implements IWhiteDao{

	/**
	 * 获取白名单列表
	 * @param map 参数{forCount:false(去数据)}
	 * @return List<WhiteEntity>
	 */
	@Override
	public List<WhiteEntity> getWhiteList(Map<String, Object> map) {
		return getSqlSession().selectList("getWhiteList", map);
	}

	/**
	 * 保存白名单列表
	 * @param entity {
	 * 		placeCode [场所编码]
	 * 		,mobile [手机号]
	 * 		,optorType [操作人类型]
	 * 		,optorId [操作人Id]
	 * 		,outVal [返回值]
	 * @return	新白名单ID: 成功，用大于0判断
	 *    		-1: 操作失败;
	 *    		-2: 数据已经存在(身份证号已经添加过了);
	 *    		-3: 场所免认证设备数已达上限，不可以超过10个
	 *    		-4: 场所手机号免认证设备数已达上限，不可以超过3个
	 * }
	 */
	@Override
	public int saveWhiteList(WhiteEntity entity) {
		getSqlSession().insert("saveWhiteList", entity);
		return entity.getOutVal();
	}

	@Override
	public int updateWhiteInfo(WhiteEntity entity) throws Exception {
		getSqlSession().insert("updateWhiteInfo", entity);
		return entity.getOutVal();
	}

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
	@Override
	public int delWhiteList(WhiteEntity entity) {
		getSqlSession().update("delWhiteList",entity);
		return entity.getOutVal();
	}

	@Override
	public int updateSync2AuthState(WhiteEntity entity) {
		return getSqlSession().update("updateSync2AuthState",entity);
	}
}
