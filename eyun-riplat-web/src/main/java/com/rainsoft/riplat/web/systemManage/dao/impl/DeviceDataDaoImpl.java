package com.rainsoft.riplat.web.systemManage.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.IDeviceDataDao;
import com.rainsoft.riplat.web.systemManage.model.DeviceData;

import org.springframework.stereotype.Repository;

/**
 * 设备资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015年12月3日 12:46:30
 */
@Repository("deviceDataDao")
public class DeviceDataDaoImpl extends MybatisPersitenceDaoImpl<DeviceData, String> implements IDeviceDataDao {
}
