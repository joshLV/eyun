package com.rainsoft.union.web.sysmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 场所基础资料
 *
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Controller
@RequestMapping("/systemManage/place")
public class PlaceDataController {
	private static final Logger logger = LoggerFactory.getLogger(PlaceDataController.class);

	private static final String rowList = Constants.ROW_LIST;
	@Resource
	private PubDataService pubDataServiceImpl;
	/**
	 * 通过登录的会员ID获取该会员的场所资料 参数 placeDataModel
	 */
	@RequestMapping("/bySearch")
	protected void bySearch(@ModelAttribute PlaceData placeData, @RequestParam("placeCode") String placeCode, HttpServletResponse response, HttpServletRequest request) {
		/** 将会员ID赋值给场所资料实体，通过会员ID查询该会员所拥有的场所 **/
		placeData.setUserId(SpringMvcUtil.getUserId());
		if (!"-1".equals(placeCode)) {
			placeData.setPlaceCode(placeCode);
		}
		PageBean pageBean = getGridData(request);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = pubDataServiceImpl.getPlaceListJSON(placeData, pageBean);
		} catch (Exception e) {
			logger.error("请求场所数据失败",e);
		}
		//jqGrid 请求
		SpringMvcUtil.responseJSONWriter(response, jsonObject);
	}

	//获取场所选项
	@RequestMapping("/getPlacesOpt")
	public void getPlacesOpt(HttpServletResponse response){
		List<PlaceData> list = null;
		String msg = "";
		try {
			list = pubDataServiceImpl.getPlaceOptByUserId(SpringMvcUtil.getUserId());
			msg = "获取场所选项成功";
		} catch (Exception e) {
			logger.error("PlaceDataController.getPlacesOpt-->"+e.toString());
			msg = "获取场所选项失败";
		}
		Result result = new Result();
		result.setData(list);
		result.setMessage(msg);
		SpringMvcUtil.responseJSONWriter(response , result);
	}

	/**
	 * 方法功能说明：List页面跳转（Jqgrid）
	 *
	 * @param model
	 * @return
	 * @author sh_j_baoxu
	 * @data 2015年11月29日 下午11:52:08
	 */
	@RequestMapping("/toList")
	protected String toList(Model model) {
		String placeCode = "-1";
		String strPlaceCode = SpringMvcUtil.getParameter("placeCode");
		if (StringUtil.isNotEmpty(strPlaceCode)) {
			placeCode = strPlaceCode;
		}
		SpringMvcUtil.setDefaultPalceCode(placeCode);
		model.addAttribute("rowList", rowList);
		return "/sysmanage/placeList";
	}

	/**
	 * 页面有jqGrid 处理表格的数据
	 *
	 * @param request
	 * @return
	 */
	protected PageBean getGridData(HttpServletRequest request) {
		// 当前页
		String currPageObj = request.getParameter("page");

		// 当前页显示条数
		String pageSizeObj = request.getParameter("rows");

		// 排序字段
		String sortObj = request.getParameter("sidx");

		// 排序顺序
		String sordObj = request.getParameter("sord");


		PageBean page = new PageBean();

		if (StringUtil.isNotEmpty(currPageObj)) {
			page.setCurrPage(Integer.parseInt((currPageObj)));
		} else {
			page.setCurrPage(1);
		}

		if (StringUtil.isNotEmpty(pageSizeObj)) {
			page.setPageSize(Integer.parseInt(pageSizeObj));
		} else {
			page.setPageSize(10);
		}

		if (StringUtil.isNotEmpty(sortObj)) {
			page.setSort(sortObj.toString());
		} else {
			page.setSort("id");
		}

		if (StringUtil.isNotEmpty(sordObj)) {
			page.setOrder(sordObj.toString());
		} else {
			page.setOrder("asc");
		}
		return page;
	}
}
