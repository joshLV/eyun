package com.rainsoft.union.web.index.controller;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.AmtFormat;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.account.model.AccAccount;
import com.rainsoft.union.web.account.service.IAccAccountService;
import com.rainsoft.union.web.account.service.IWwbAccountService;
import com.rainsoft.union.web.index.model.Index;
import com.rainsoft.union.web.index.service.IIndexService;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import com.rainsoft.union.web.smsmanage.service.SmsAssignaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/index/index")
public class IndexController extends SpringBaseController<Index, String> {
	private final String PREXIF = "/index/index";
	@Resource
	private IIndexService indexService;
	
	@Resource
	private IAccAccountService accAccountService;

	@Resource
	private IWwbAccountService wwbAccountService;
	
	@Resource
	private SmsAssignaService smsAssignaServiceImpl;
	
	@Override
	protected IMybatisBasePersitenceService<Index, String> getBaseService() {
		return indexService;
	}

	@Resource
	private PubDataService pubDataServiceImpl;

	@Override
	protected String getPrefix() {
		return PREXIF;
	}
	
	/**
	 * 跳转到IndexList页面跳转（Jqgrid）
	 * @param model 
	 * @return String
	 */
	@RequestMapping("/toIndex")
	public String toList(Model model,@ModelAttribute AccAccount accAccount) {
		Integer smsBlance = null;
		Double wwbBalance = null;
		BigDecimal accBalance = null;
		AccAccount account = null;
		Integer memberId = SpringMvcUtil.getUserId();
		String placeType = "";
		try {
			placeType = pubDataServiceImpl.getPlaceTypeByMemberId(memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		accAccount.setUserId(SpringMvcUtil.getUserId());
		try {
			smsBlance = smsAssignaServiceImpl.getSMSbalance(memberId);
			account = accAccountService.getAccountBal(accAccount);
			wwbBalance = wwbAccountService.getBalanceByUserId(memberId);
			if(smsBlance == null || "".equals(smsBlance)){
				smsBlance = 0;
			}
			if(wwbBalance == null || "".equals(wwbBalance)){
				wwbBalance = 0.0;
			}
			if(account == null){
				accBalance = new BigDecimal("0");
			}else{
				accBalance = AmtFormat.commAmtFormat(account.getAccountBal());
			}
			if(StringUtil.isEmpty(placeType)){
				placeType = "";
			}
			model.addAttribute("smsBlance", smsBlance);
			model.addAttribute("wwbBalance", wwbBalance);
			model.addAttribute("accountBal", accBalance);
			model.addAttribute("placeType", placeType.trim());
		} catch (Exception e) {
			logger.error("获取数据失败：" + e);
			e.printStackTrace();
		}
		return super.toList(model);
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
		result = indexService.findNewsBy(map, getRequest());
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
	 * @return
	 */
	@RequestMapping(value = "/findAccountInfo", method = RequestMethod.POST)
	public void findAccountInfo() {
		Result result = new Result();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", SpringMvcUtil.getUserId());
		map.put("contextPath", getRequest().getContextPath());
		result = indexService.findAccountInfo(map, getRequest());
		super.responseAjaxData(result);
	}
	
	
	/**
	 * 
	 * @Description: 访客饼图
	 * @param    
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年1月11日上午11:36:45
	 */
	/*@RequestMapping(value = "/findVisitorNumByMemberid", method = RequestMethod.POST)
	public void findVisitorNumByMemberid() {
		//不断报错待重构，暂注释掉
		Result result = indexService.findVisitorNumByMemberid(SpringMvcUtil.getUserId());
		super.responseAjaxData(result);
	}*/
}