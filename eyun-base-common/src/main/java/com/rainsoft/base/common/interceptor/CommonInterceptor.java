package com.rainsoft.base.common.interceptor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.web.system.model.BaseResource;
import com.rainsoft.base.web.system.model.BaseRole;
import com.rainsoft.base.web.system.model.BaseUser;
import com.rainsoft.base.web.system.service.IBaseResourceService;
import com.rainsoft.base.web.system.service.IBaseRoleService;
import com.rainsoft.base.web.system.service.IBaseUserService;

public class CommonInterceptor implements HandlerInterceptor {

	@Resource(name = "baseUserService")
	private IBaseUserService baseUserService;
	@Resource(name = "baseResourceService")
	private IBaseResourceService baseResourceService;
	@Resource(name = "baseRoleService")
	private IBaseRoleService roleService;

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		/**
		 * 自动登录拦截实现 1),获取用户的session中的AuthenToken 存在：不做任何操作 不存在：
		 * 2),获取Cookie中的用户ID，存在，获取该用户的详细信息，保存到session Cookie不存在 3),获取当前访问url
		 * 4),获取web.xml中放行的地址 5),如果访问的url不是放行的地址，跳转到登录页面
		 */
		
		String parentPath = request.getContextPath();
		HttpSession session = request.getSession();
		AuthenToken authenToken = (AuthenToken) session.getAttribute("AuthenToken");
		if (null == authenToken) {

			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {

				for (Cookie cookie : cookies) {

					if ("userId".equals(cookie.getName())) {

						String userId = cookie.getValue();
						BaseUser user = baseUserService.findById(userId);
						if (user != null) {

							authenToken = new AuthenToken();
							authenToken.setAnotherName(user.getAnotherName());
							
							authenToken.setLoginTime(new Date());
							authenToken.setLoginName(user.getLoginName());
							authenToken.setUserId(Integer.valueOf(userId));
							session.setAttribute("AuthenToken", authenToken);

							// 用户角色取得
//							List<BaseRole> allRoles = roleService.selectList("getRoleByUserId", userId);
							BaseRole role = roleService.findBy("getRoleByUserId", Integer.valueOf(userId));
//							StringBuffer idStr = new StringBuffer();
//							for (BaseRole baseRole : allRoles) {
//								idStr.append(baseRole.getId());
//								idStr.append(",");
//							}
							
							// 角色资源取得
//							if (StringUtil.isNotEmpty(idStr.toString())) {
//								String roleIds = idStr.toString().substring(0, idStr.toString().lastIndexOf(","));
//								List<BaseResource> resourceList= baseResourceService.selectList("getResourceByRoleIds", roleIds);
//								BaseResource.setAllResource(resourceList);
//							}
							List<BaseResource> resourceList = baseResourceService.selectList("getResourceByRoleIds", role.getId());
							BaseResource.setAllResource(resourceList);
							
							session.setAttribute("allResource", BaseResource.allResource);

							return true;

						}

					}

				}
				String path = request.getRequestURI();
				path = path.substring(path.lastIndexOf("/"));
				String noLoginUrl = request.getSession().getServletContext().getInitParameter("noLoginUrl");
				if (StringUtil.isNotEmpty(noLoginUrl)) {

					String[] noLoginUrlArr = noLoginUrl.split(",");
					List<String> list = Arrays.asList(noLoginUrlArr);
					if (list != null && list.contains(path)) {

						return true;

					}

				}

			}
			response.sendRedirect(parentPath + "/tologin.do");
			return false;

		}
		return true;

	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}