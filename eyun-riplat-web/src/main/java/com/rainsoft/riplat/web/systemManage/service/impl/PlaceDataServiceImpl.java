package com.rainsoft.riplat.web.systemManage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.systemManage.dao.IPlaceDataDao;
import com.rainsoft.riplat.web.systemManage.model.PlaceData;
import com.rainsoft.riplat.web.systemManage.service.IPlaceDataService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 场所资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Service("placeDataService")
public class PlaceDataServiceImpl extends MybatisBasePersitenceServiceImpl<PlaceData, String> implements IPlaceDataService {
	@Resource
	private IPlaceDataDao placeDataDao;

	@Override
	protected IMybatisPersitenceDao<PlaceData, String> getBaseDao() {
		return placeDataDao;
	}

}
