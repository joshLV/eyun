package com.rainsoft.base.common.interceptor;

import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.web.system.model.BaseResource;

public class WebCommonInterceptor implements WebRequestInterceptor {

	private static String[] powers = new String[] {"readPower", "writePower", "examinePower", "releasePower", "delegatePower", "lockPower", "recommendPower", "otherPower1", "otherPower2", "otherPower3"};
	/**
	 * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中
	 */
	@Override
	public void preHandle(WebRequest request) throws Exception {
		
	}

	/**
	 * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在
	 * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		String resourceId = request.getParameter("resourceId");
		//10个权限变量名称
		String privilege = null;
		if(resourceId != null && resourceId != "") {
			List<BaseResource> resourceList = null;
			Object obj = SpringMvcUtil.getSessionAttribute(SpringMvcUtil.getRequest(), "allResource");
			if(obj instanceof List) {
				resourceList = (List<BaseResource>) obj;
			}
			if(resourceList != null) {
				for(BaseResource res : resourceList) {
					if(resourceId.equals(res.getId().toString())) {
						privilege = res.getPrivilege();
						break;
					}
				}
				if(StringUtil.isNotEmpty(privilege)) {
					for(int i = 0; i < privilege.length(); i++) {
						String code = privilege.substring(i, i + 1);
						if("Y".equals(code)) {
							model.addAttribute(powers[i], true);
						} else {
							model.addAttribute(powers[i], false);
						}
					}
				} else {
					for(int j = 0; j < powers.length; j++) {
						model.addAttribute(powers[j], false);
					}
				}
			}
		}
	}

	/**
	 * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放
	 */
	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {

	}

}