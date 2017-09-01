package com.rainsoft.riplat.web.systemManage.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.IPlaceDataDao;
import com.rainsoft.riplat.web.systemManage.model.PlaceData;

import org.springframework.stereotype.Repository;

/**
 * 场所资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Repository("placeDataDao")
public class PlaceDataDaoImpl extends MybatisPersitenceDaoImpl<PlaceData, String> implements IPlaceDataDao {
}
