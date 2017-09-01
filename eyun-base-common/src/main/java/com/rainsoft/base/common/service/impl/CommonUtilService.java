package com.rainsoft.base.common.service.impl;

import com.rainsoft.base.web.system.dao.IBaseAreaDao;
import com.rainsoft.base.web.system.model.BaseArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 该类主要是用来初始化启动加载的数据
 */
@Service(value = "commonUtilService")
public class CommonUtilService {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtilService.class);

	@Resource
	private IBaseAreaDao baseAreaDao;

	public static List<BaseArea> areaList;// 初始化区域单位列表

	/**
	 * 
	 * 方法功能说明： 系统参数初始化处理
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年12月3日 下午4:18:49
	 */
	@PostConstruct
	public void SystemInit() {
		logger.info(">>>>>>>>开始初始化数据<<<<<<<<");
		logger.info("=====初始化区域单位=====");
		areaList = baseAreaDao.findAllArea();

		logger.info(">>>>>>>>初始化数据结束<<<<<<<<");
	}
}