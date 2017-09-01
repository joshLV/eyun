package com.rainsoft.riplat.web.systemManage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.systemManage.dao.IDeviceDataDao;
import com.rainsoft.riplat.web.systemManage.model.DeviceData;
import com.rainsoft.riplat.web.systemManage.service.IDeviceDataService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015年12月3日 12:46:59
 */
@Service("deviceDataService")
public class DeviceDataServiceImpl extends MybatisBasePersitenceServiceImpl<DeviceData, String> implements IDeviceDataService {
	@Resource
	private IDeviceDataDao deviceDataDao;

	@Override
	protected IMybatisPersitenceDao<DeviceData, String> getBaseDao() {
		return deviceDataDao;
	}

}
