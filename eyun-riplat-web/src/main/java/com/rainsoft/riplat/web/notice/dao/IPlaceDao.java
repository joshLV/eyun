package com.rainsoft.riplat.web.notice.dao;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.riplat.web.notice.model.Place;

public interface IPlaceDao extends IMybatisPersitenceDao<Place, String> {
	/**
	 * 功能说明：获取的区域场所信息
	 * 
	 * @return Place Place实体对象
	 */
	Place getToPlaceDate(Place place);
	
}