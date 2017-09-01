package com.rainsoft.riplat.web.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.IPlaceDao;
import com.rainsoft.riplat.web.notice.model.Place;

@Repository
public class PlaceDaoImpl extends MybatisPersitenceDaoImpl<Place, String> implements IPlaceDao {

	
	/**
	 * 功能说明：获取区域场所的信息
	 * @param place 场所实体
	 * @return Place 场所实提对象
	 */
	public Place getToPlaceDate(Place place) {
		
		  return this.getSqlSession().selectOne(getSqlName("loadPlaceData"), place);
	}
}

