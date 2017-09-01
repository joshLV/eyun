package com.rainsoft.base.web.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.service.impl.CommonUtilService;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.base.web.system.service.IBaseAreaService;

@Controller
@RequestMapping("/pubArea")
public class BaseAreaController extends SpringBaseController<BaseArea, String> {

	private final String PREXIF = "/";
	@Resource
	private IBaseAreaService baseAreaService;

	@Override
	protected String getPrefix() {
		return PREXIF;
	}

	@Override
	protected IMybatisBasePersitenceService<BaseArea, String> getBaseService() {
		return baseAreaService;
	}

	/**
	 * 
	 * 方法功能说明：加载省
	 * 
	 * @author sh_j_baoxu
	 * @param Id 默认选中项
	 * @data 2014年12月25日 上午10:54:54
	 */
	@RequestMapping("/toLoadProvince")
	public void toLoadProvince() {
		Result result = new Result();
		List<BaseArea> areaList = new ArrayList<BaseArea>();
		for (int i = 0; i < CommonUtilService.areaList.size(); i++) {
			String areaId = String.valueOf(CommonUtilService.areaList.get(i).getId());
			if (areaId.length() < 6) {
				continue;
			}
			if ("0000".equals(areaId.substring(2, 6))) {
				areaList.add(CommonUtilService.areaList.get(i));
			}
		}
		result.addSucc("获取省级菜单成功！");
		result.setData(JSONObject.toJSONString(areaList));
		super.responseAjaxData(result);
	}

	/**
	 * 
	 * 方法功能说明：加载市
	 * 
	 * @author sh_j_baoxu
	 * @data 2014年12月25日 上午10:54:54
	 */
	@RequestMapping("/toLoadCity")
	public void toLoadCity(@RequestParam("parentCode") String parentCode) {
		Result result = new Result();
		List<BaseArea> areaList = new ArrayList<BaseArea>();
		if ("0".equals(parentCode)) {
			result.addError("获取市级菜单失败!");
			super.responseAjaxData(result);
		}
		for (int i = 0; i < CommonUtilService.areaList.size(); i++) {
			String areaId = String.valueOf(CommonUtilService.areaList.get(i).getId());
			String treeLevel = String.valueOf(CommonUtilService.areaList.get(i).getTreeLevel());
			if (areaId.length() < 6) {
				continue;
			}
			/*begin*/
			if (areaId.substring(0, 2).equals(parentCode.substring(0, 2)) && treeLevel.equals("2")) {
			areaList.add(CommonUtilService.areaList.get(i));
		    }
//			if (areaId.substring(0, 2).equals(parentCode.substring(0, 2)) && !areaId.substring(2, 6).equals("0000") && areaId.substring(4, 6).equals("00")) {
//				areaList.add(CommonUtilService.areaList.get(i));
//			}
			/*end*/
		}
		result.addSucc("获取市级菜单成功！");
		result.setData(JSONObject.toJSONString(areaList));
		super.responseAjaxData(result);
	}

	/**
	 * 
	 * 方法功能说明：加载区
	 * 
	 * @author sh_j_baoxu
	 * @data 2014年12月25日 上午10:57:06
	 */
	@RequestMapping("/toLoadTown")
	public void toLoadTown(@RequestParam("parentCode") String parentCode) {
		Result result = new Result();
		List<BaseArea> areaList = new ArrayList<BaseArea>();
		if ("0".equals(parentCode)) {
			result.addError("获取区级菜单失败!");
			super.responseAjaxData(result);
		}
		for (int i = 0; i < CommonUtilService.areaList.size(); i++) {
			String areaId = String.valueOf(CommonUtilService.areaList.get(i).getId());
			String treeLevel = String.valueOf(CommonUtilService.areaList.get(i).getTreeLevel());
			if (areaId.length() < 6) {
				continue;
			}
			/*begin*/
			if (areaId.substring(0, 4).equals(parentCode.substring(0, 4)) && treeLevel.equals("3")) {
			areaList.add(CommonUtilService.areaList.get(i));
		}
//			if (areaId.substring(0, 4).equals(parentCode.substring(0, 4)) && !areaId.substring(4, 6).equals("00")) {
//				areaList.add(CommonUtilService.areaList.get(i));
//			}
			/*end*/
		}
		result.addSucc("获取区级菜单成功！");
		result.setData(JSONObject.toJSONString(areaList));
		super.responseAjaxData(result);
	}

}
