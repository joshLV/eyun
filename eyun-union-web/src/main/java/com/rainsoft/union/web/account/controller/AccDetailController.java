package com.rainsoft.union.web.account.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.model.AccDetail;
import com.rainsoft.union.web.account.model.AccUseRecord;
import com.rainsoft.union.web.account.service.IAccDetailService;


/**
 * 
 * @Description:旺旺币controller类
 * @author:王乾
 * @date:2016年5月17日上午9:31:57
 * 
 */
@Controller
@Log(clazzDesc = "账户明细")
@RequestMapping("/account/accDetail")
@SessionAttributes({"creator"})
public class AccDetailController extends SpringBaseController<AccDetail, String>{

	private final String PREFIX = "/account/accDetail";
	
	@Resource
	private IAccDetailService accDetailService;
	
	@Override
	protected IMybatisBasePersitenceService<AccDetail, String> getBaseService() {
		
		return accDetailService;
	}

	@Override
	protected String getPrefix() {
		
		return PREFIX;
	}
	
	/**
	 * @Description: 跳转到账户明细列表页面
	 * @author sh_j_wangqian
	 * @created 2016年4月7日 下午4:09:45
	 *
	 */
	@RequestMapping("/toAccDetail")
	public String toSms(Model model) {
		model.addAttribute("creator", SpringMvcUtil.getUserId());
		return super.toList(model);
	}
	
	
	/**
	 * 
	 * @Description:获取账户充值记录列表
	 * @author:王乾
	 * @date:2016年5月18日下午2:37:45
	 * @param smsURE
	 * @param model
	 */
	@RequestMapping(value = "/getAccBuyRecord", method = RequestMethod.POST)
	public void getAccBuyRecord(@ModelAttribute AccDetail wwb,HttpServletRequest r) {
		wwb.setUserId(SpringMvcUtil.getUserId());
		this.ReturnDataTableAjaxPost(wwb, "getAccBuyRecord");
		
	}
	
	/**
	 * @Description:账户支出记录
	 * @author:huxiaolong
	 * @param useWwb
	 */
	@RequestMapping(value = "/getAccUseRecord", method = RequestMethod.POST)
	public void getAccUseRecord(@ModelAttribute AccUseRecord useWwb){
		useWwb.setUserId(SpringMvcUtil.getUserId());
		JSONObject data = null;
		PageBean page = this.getGridData();
		try {
			data = accDetailService.getAccUseRecord(useWwb, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringMvcUtil.responseJSONWriter(getResponse(), data);
	}
	
}
