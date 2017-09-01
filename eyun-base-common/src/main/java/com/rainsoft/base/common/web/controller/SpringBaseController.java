package com.rainsoft.base.common.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.model.IdEntity;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.MessageSourceHelper;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 功能说明：springmvc的基类，处理异常，公共方法
 * 
 * @author sh_j_baoxu
 * @created 2014年6月27日 上午6:19:14
 * @updated
 * @param <T>
 */
public abstract class SpringBaseController<T extends IdEntity, ID extends Serializable> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	protected abstract IMybatisBasePersitenceService<T, String> getBaseService();
	
	protected abstract String getPrefix();

	protected final String ADD = getPrefix() + "Add";
	protected final String EDIT = getPrefix() + "Edit";
	protected final String VIEW = getPrefix() + "View";
	protected final String LIST = getPrefix() + "List";
	protected static final String rowList = Constants.ROW_LIST;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	private final MessageSourceHelper messageSource = new MessageSourceHelper();
	private static final ThreadLocal<HttpServletRequest> localReq = new ThreadLocal<HttpServletRequest>();
    private static final ThreadLocal<HttpServletResponse> localResp = new ThreadLocal<HttpServletResponse>();
    private static final ThreadLocal<HttpSession> localSess = new ThreadLocal<HttpSession>();
    
    public HttpServletRequest getRequest() {
        return localReq.get();
    }

    public HttpServletResponse getResponse() {
        return localResp.get();
    }
    
    public HttpSession getSession() {
        return localSess.get();
    }

	/**
	 * 
	 * 方法功能说明：List页面跳转（Jqgrid）
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月29日 下午11:52:08
	 * @param model
	 * @return
	 */
	@RequestMapping("/toList")
	protected String toList(Model model) {
		String placeCode = "-1";
		String strPlaceCode = SpringMvcUtil.getParameter("placeCode");
		if(StringUtil.isNotEmpty(strPlaceCode)) {
			placeCode = strPlaceCode;
		}
		SpringMvcUtil.setDefaultPalceCode(placeCode);
		model.addAttribute("rowList", rowList);
		return LIST;
	}

	/**
	 * 
	 * 方法功能说明：Jqgrid数据加载
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月29日 下午11:53:02
	 * @param item
	 */
	@RequestMapping("/loadGrid")
	protected void loadGrid(@ModelAttribute T item) {
		this.ReturnDataTableAjaxPost(item, null);
	}

	/**
	 * 
	 * 方法功能说明：Jqgrid数据加载
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月29日 下午11:53:02
	 * @param item
	 */
	protected void loadGrid(@ModelAttribute T item, String keyId) {
		this.ReturnDataTableAjaxPost(item, keyId);
	}

	/**
	 * 功能说明：通用跳转到新增页面
	 * 
	 * @author sh_j_baoxu
	 * @updated
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	protected String add() {
		return ADD;
	}

	/**
	 * 功能说明：通用跳转到编辑页面
	 * 
	 * @author sh_j_baoxu
	 * @updated
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam("id") String id) {
		model.addAttribute("item", getBaseService().findById(id));
		return EDIT;
	}

	@RequestMapping(value = "/view")
	public String view(Model model, @RequestParam("id") String id) {
		model.addAttribute("item", getBaseService().findById(id));
		return VIEW;
	}

	/**
	 * 功能说明：通用保存页面
	 * 
	 * @author sh_j_baoxu
	 * @updated
	 * @param response
	 * @param item
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@ModelAttribute T item) {
		Result result = new Result();
		try {
			if (null != item.getId() && 0 != item.getId()) {
				getBaseService().update(item);
			} else {
				getBaseService().save(item);
			}
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("保存成功！");
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("保存失败！");
		}

		this.responseAjaxData(result);
	}

    /**
     * 功能说明：通用删除功能
     * 
     * @author sh_j_baoxu
     * @updated
     * @param response
     *            HttpServletResponse
     * @param id
     *            删除的id
     */
	@RequestMapping(value = "/del")
	public void del(@RequestParam("id") String id) {
		Result result = new Result();
		try {
			if (StringUtil.isNotEmpty(id)) {
				getBaseService().deleteById(id);
				result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage("保存成功！");
			} else {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage("请不要恶意操作！");
			}
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage("删除失败！");
			logger.error("删除失败",e);
		}
		this.responseAjaxData(result);
	}

	/**
	 * 功能说明：获取datatable传过来的数据,普通的get方法
	 * 
	 * @author sh_j_baoxu
	 * @created 2014年6月29日 下午8:21:42
	 * @updated
	 * @param t
	 *            查询的实体，需要的查询的值，通过实体传过来，由mybatis解析
	 * @param prefix
	 *            排序字段的前缀
	 * @return 封装datatable 需要的map数据，由@ResponseBody 转成对应的json
	 */
	protected void ReturnDataTableGet(T t) {
		JSONObject jsonObj = new JSONObject();
		// String sEcho = getRequest().getParameter("sEcho"); // 点击的次数
		// String startNum = getRequest().getParameter("iDisplayStart"); //从第几条数据开始检索
		// String pageSizeStr = getRequest().getParameter("iDisplayLength");//显示的行数
		// String sortIndex=getRequest().getParameter("iSortCol_0");//排序索引
		// String search=getRequest().getParameter("sSearch");//搜索值

		String search = (String)getRequest().getAttribute("searchValue");// 搜索值
		t.setSearchValue(search);
		PageBean page = getGridData();

		PageHelper.startPage(page.getCurrPage(), page.getPageSize());
		List<T> list = getBaseService().findListBy(t, page.getSort(), page.getOrder());
		page.setTotal(((Page<T>) list).getTotal());
		// map.put("sEcho", sEcho);
		// 根据jqGrid对JSON的数据格式要求给jsonObj赋值
		jsonObj.put("curPage", page.getCurrPage()); // 当前页
		jsonObj.put("totalPages", page.getPageCount()); // 总页数
		jsonObj.put("totalRecords", page.getTotal()); // 总记录数
		jsonObj.put("rowList", rowList);
		// 页面显示记录数设置
		jsonObj.put("rowNum", page.getPageSize());
		jsonObj.put("dataRows", JSONArray.toJSONString(list));
		SpringMvcUtil.responseJSONWriter(getResponse(), jsonObj);
	}

	/**
	 * 功能说明：dataTable ajax post 请求数据的封装
	 * 
	 * @author sh_j_baoxu
	 * @created 2014年7月4日 上午6:35:22
	 * @updated
	 * @param t
	 *            查询的实体，需要的查询的值，通过实体传过来，由mybatis解析
	 * @param prefix
	 *            排序字段的前缀
	 * @return 封装datatable 需要的map数据，由@ResponseBody 转成对应的json
	 */
	protected void ReturnDataTableAjaxPost(T t, String keyId) {
		JSONObject jsonObj = new JSONObject();
		
        String search = getRequest().getParameter("searchValue");// 搜索值
        
        if (StringUtil.isEmpty(search)) {// 兼容interface方式获取搜索条件
            
            search = (String) getRequest().getAttribute("searchValue");
        }
		t.setSearchValue(search);
		t.setPlaceCode(SpringMvcUtil.getDefaultPalceCode());

		PageBean page = getGridData();

		PageHelper.startPage(page.getCurrPage(), page.getPageSize());
		List<T> list = null;
		if (StringUtil.isEmpty(keyId)) {
			list = getBaseService().findListBy(t, page.getSort(), page.getOrder());	
		} else {
			list = getBaseService().findListBy(keyId, t, page.getSort(), page.getOrder());	
		}
		page.setTotal(((Page<T>) list).getTotal());
		
		if(StringUtil.isEmpty(SpringMvcUtil.getPlatformId()) || "000000".equals(SpringMvcUtil.getPlatformId())){//当不存在平台ID时，即本系统的账号登录直接访问本系统，直接返回JqGrid
			// 根据jqGrid对JSON的数据格式要求给jsonObj赋值
			jsonObj.put("curPage", page.getCurrPage()); // 当前页
			jsonObj.put("totalPages", page.getPageCount()); // 总页数
			jsonObj.put("totalRecords", page.getTotal()); // 总记录数
			// 页面显示记录数设置
			jsonObj.put("rowNum", page.getPageSize());
			jsonObj.put("dataRows", JSONArray.toJSON(list));
			SpringMvcUtil.responseJSONWriter(getResponse(), jsonObj);
		}else{//当为其他系统通过HTTP协议请求返回表格数据时，则使用加密HTTP信息的返回方法
			Result result = new Result();
			result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
			result.setCount(Integer.parseInt(String.valueOf(page.getTotal())));
			result.setData(list);
			SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
		}
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@ExceptionHandler
	public String exception(Exception e) {
		logger.error(this.getClass() + " is errory, errorType=" + e.getClass(), e);
		// 如果是json格式的ajax请求
		if (getRequest().getHeader("accept").indexOf("application/json") > -1 || (getRequest().getHeader("X-Requested-With") != null && getRequest().getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
			getResponse().setStatus(500);
			getResponse().setContentType("application/json;charset=utf-8");
			SpringMvcUtil.responseWriter(getResponse(), e.getMessage());
			return null;
		} else {// 如果是普通请求
			getRequest().setAttribute("exceptionMsg", e.getMessage());
			// 根据不同的异常类型可以返回不同界面
			if (e instanceof SQLException)
				return "sqlerror";
			else
				return "error";
		}
	}

	/**
	 * 表示请求该类的每个Controller前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面
	 * 
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest _request, HttpServletResponse _response) {
	    localReq.set(_request);
        localResp.set(_response); 
        localSess.set(getRequest().getSession());
        messageSource.setLocale(_request.getLocale());
	}

	/**
	 * 
	 * 方法功能说明：Ajax返回处理
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月25日 上午12:13:52
	 * @param result
	 */
	public void responseAjaxData(Result result) {
		// 定义返回的数据类型：json
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("status", result.getStatus());
		jsonObj.put("msg", result.getMessage());
		jsonObj.put("data", result.getData());

		getResponse().setContentType("application/octet-stream");
		getResponse().setCharacterEncoding("UTF-8");
		try {
			getResponse().getWriter().print(jsonObj);
			getResponse().getWriter().close();
		} catch (Exception e) {
			logger.error("responseAjaxData失败",e);
		}
	}

	/**
	 * 
	 * 方法功能说明：取得grid数据
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月29日 下午6:53:26
	 * @return
	 */
	protected PageBean getGridData() {
        // 当前页
        String currPageObj = (String) getRequest().getAttribute("page");
        if (StringUtil.isEmpty(currPageObj)) {
            currPageObj = getRequest().getParameter("page");
        }
        
        // 当前页显示条数
        String pageSizeObj = (String) getRequest().getAttribute("rows");
        
        if (StringUtil.isEmpty(pageSizeObj)) {
            pageSizeObj = getRequest().getParameter("rows");
        }
        
        // 排序字段
        String sortObj = (String) getRequest().getAttribute("sidx");
        if (StringUtil.isEmpty(sortObj)) {
            sortObj = getRequest().getParameter("sidx");
        }
        
        // 排序顺序
        String sordObj = (String) getRequest().getAttribute("sord");
        if (StringUtil.isEmpty(sordObj)) {
            sordObj = getRequest().getParameter("sord");
        }
        
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