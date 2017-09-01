package com.rainsoft.base.common.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.utils.Util;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseUser;
import com.rainsoft.base.web.system.service.IBaseCompanyService;
import com.rainsoft.base.web.system.service.IBaseResourceService;
import com.rainsoft.base.web.system.service.IBaseUserService;

@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Resource
	private IBaseUserService baseUserService;
	@Resource
	private IBaseResourceService baseResourceService;
	@Resource
	private IBaseCompanyService baseCompanyService;
	
	/**
	 * 
	 * 方法功能说明：访问项目、页面初期跳转
	 * 
	 * @author sh_j_baoxu
	 * @throws ServletException 
	 * @throws IOException
	 * @data 2014年12月25日 上午10:54:54
	 */
	@RequestMapping("/login")
	public String Main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "forward:/tologin.do";
	}

	/**
	 * 
	 * 方法功能说明：登录成功后跳转
	 *
	 * @author sh_j_baoxu
	 * @data 2015年12月15日 下午9:58:33
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/main/riplat")
	public String toUnion(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		String allResourceList = JSONArray.toJSONString(BaseResource.getAllResource());
		model.addAttribute("allResourceList", allResourceList);
		return "main";
	}
	
	/**
	 * 
	 * 方法功能说明：未登录跳转
	 *
	 * @author sh_j_baoxu
	 * @data 2015年12月15日 下午9:58:57
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/tologin")
	public String toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "login";
	}

	@RequestMapping("/logout")
	public String loginout() {
		HttpSession session = SpringMvcUtil.getRequest().getSession();
		session.invalidate();
		return "forward:/tologin.do";
	}

	/**
	 * 功能说明：系统登录
	 * 
	 * @author admin
	 * @return
	 * @created 2014年7月7日 上午9:12:15
	 * @updated
	 * @return
	 */
	@RequestMapping("/dologin")
	public void login(HttpServletRequest request, HttpServletResponse response,@RequestParam("loginName") String loginName, @RequestParam("password") String password, @RequestParam(value = "rememberPwd", required = false) String rememberPwd,String platformId) {
		Result result = new Result();
		BaseUser user = new BaseUser();
		user.setLoginName(loginName);
		user.setPassword(CommonUtil.getMd5(password));//MD5加密密码
		user.setLoginWay(1); // 最后登录方式, 1：web网页; 2：wap网页; 3：手机客户端；4：未知
		user.setPlatformId(platformId);//属于哪个平台
		user = baseUserService.loginWithPlatform(user);
		if (Constants.RETURN_SUCCESS.equals(user.getRemark())) {
			// 用户资源设置
			request.getSession().setAttribute("allResource", BaseResource.allResource);
			// 用户信息保存到session
			AuthenToken authenToken = new AuthenToken();
			authenToken.setAnotherName(user.getAnotherName());
			authenToken.setLoginTime(new Date());
			authenToken.setLoginName(user.getLoginName());
			authenToken.setUserId(user.getId());
			authenToken.setPlatformId(user.getPlatformId());
			request.getSession().setAttribute("AuthenToken", authenToken);
			if (StringUtil.isNotEmpty(rememberPwd) && "yes".equals(rememberPwd)) {

				// 保存cookie一个月
				Cookie cookie = new Cookie("userId", user.getId().toString());
				cookie.setMaxAge(30 * 24 * 60 * 60);
				cookie.setPath("/");
				response.addCookie(cookie);

			}

			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage("登录成功！");

		} else {
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(user.getRemark());
		}
		responseAjaxData(response, result);
	}
	
	/**
	 * 
	 * 方法功能说明：Ajax返回处理
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年11月25日 上午12:13:52
	 * @param result
	 */
	private void responseAjaxData(HttpServletResponse response, Result result) {
		// 定义返回的数据类型：json
		JSONObject jsonObj = new JSONObject();

		String data = "";
		if (Util.isNotNull(result.getData())) {
			data = result.getData().toString();
		}
		jsonObj.put("status", result.getStatus());
		jsonObj.put("msg", result.getMessage());
		jsonObj.put("data", data);

		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		try {

			response.getWriter().print(jsonObj);
			response.getWriter().close();
		} catch (Exception e) {
			logger.error("数据返回失败",e);
		}
	}
}
