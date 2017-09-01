package com.rainsoft.riplat.web.systemManage.controller;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.riplat.web.systemManage.model.PlaceData;
import com.rainsoft.riplat.web.systemManage.service.IPlaceDataService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 场所基础资料
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Controller
@RequestMapping("/systemManage/place")
public class PlaceDataController extends SpringBaseController<PlaceData, String> {

	private final String PREXIF = "/systemManage/place";

	@Resource
	private IPlaceDataService placeDataService;

	@Override
	protected IMybatisBasePersitenceService<PlaceData, String> getBaseService() {
		return placeDataService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}

	/**
	 * 通过登录的会员ID获取该会员的场所资料 参数 placeDataModel
	 */
	@RequestMapping("/bySearch")
	protected void bySearch(@ModelAttribute PlaceData placeData) {
		/** 将会员ID赋值给场所资料实体，通过会员ID查询该会员所拥有的场所 **/
		placeData.setUserId(SpringMvcUtil.getUserId());
		/** 调用父类封装的方法，完成分页，同时将数据传到页面 **/
		super.loadGrid(placeData);
	}
}
