package com.rainsoft.riplat.web.index.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.index.model.Index;
import com.rainsoft.riplat.web.index.service.IIndexService;


@Controller
@RequestMapping("/index/index")
public class IndexController extends SpringBaseController<Index, String> {
	private final String PREXIF = "/index/index";
	
	@Resource
	private IIndexService indexService;
	
	@Override
	protected IMybatisBasePersitenceService<Index, String> getBaseService() {
		return indexService;
	}

	@Override
	protected String getPrefix() {
		return PREXIF;
	}
	
	/**
	 * 方法功能说明：根据会员ID查询新闻、公告列表
	 * @author sh_j_wangwen
	 * @data 2015年12月10号
	 */
	@RequestMapping(value = "/findNewsBy", method = RequestMethod.POST)
	public void findNewsBy() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("articleType", "02");
		map.put("contextPath", getRequest().getContextPath());
		Result result = new Result();
		result = indexService.findNewsBy(map);
		super.responseAjaxData(result);
	}
	
	/**
	 * 方法功能说明：根据id查询新闻、公告明细
	 * @author sh_j_wangwen
	 * @data 2015年12月09号
	 * @param articleID 新闻ID
	 * @return
	 */
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public ModelAndView findById(@RequestParam("articleID") String articleID) {
		Index news = new Index();
		news = indexService.findById(articleID);
		return new ModelAndView(PREXIF + "NewsDetails", "news", news);
	}

	/**
	 * 方法功能说明：根据id查询头像、旺旺币余额、短信余额
	 * @author sh_j_wangwen
	 * @data 2015年12月11号
	 * @param userId 当前用户id
	 * @return
	 */
	@RequestMapping(value = "/findAccountInfo", method = RequestMethod.POST)
	public void findAccountInfo() {
		Result result = new Result();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", SpringMvcUtil.getUserId());
		map.put("contextPath", getRequest().getContextPath());
		result = indexService.findAccountInfo(map);
		super.responseAjaxData(result);
	}
}