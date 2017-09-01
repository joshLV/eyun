package com.rainsoft.riplat.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.Result;

/**
 * Created by qianna on 2016/9/6.
 */
@WebServlet(name = "/iServlet", urlPatterns = { "/iServlet.action" })
public class IServlet extends HttpServlet {

	private static Logger logger = LoggerFactory.getLogger(IServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		String params = request.getParameter("params");
		Result result = new Result();
		if (StringUtil.isNotEmpty(method) && StringUtil.isNotEmpty(params)) {
			// 判断参数是否为空
			if (StringUtil.isEmpty(params)) {
				result.setMessage("参数不足或者参数非法");
				result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
			} else {
				// ****************** 判断params是否可以解密成功 *******************
				String desParam = null;
				try {
					desParam = DesUtil.decrypt(params);
				} catch (Exception e) {
					result.setStatus(Constants.RETURN_ERROR_INTERFACE);
					logger.error("params 解密失败！" + e);
				}
				logger.info("请求解密后的参数：" + desParam);
				if (null != desParam) {
					// ****************** 判断params是不是json格式 *******************
					JSONObject json = null;
					try {
						json = JSON.parseObject(desParam);
						logger.info("=======================接口参数json:" + json + "=============================");
					} catch (Exception e) {
						result.setStatus(Constants.RETURN_ERROR_INTERFACE);
						logger.error("params不是正确的json格式！" + e);
					}
					if (null != json) {
						try {
							dispatcherRequest(request, response);
						} catch (Exception e) {
							result.setStatus(Constants.RETURN_ERROR_INTERFACE);
							logger.error("接口调用失败！" + e);
						} 
					}
				} else {
					result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
				}
			}
		} else {
			logger.error("IServlet.doPost-->参数不合法，method:" + method + ",params:" + params);
		}
		SpringMvcUtil.responseAjaxDataWithAuth(response, result);
	}

	/**
	 * 根据method转发请求
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatcherRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getParameter("method");
		logger.info("================================接口方法method：" + method + "=======================================");
		String url = IUrlType.getUrlByMethod(method);// 根据方法名获取访问链接
		logger.info("================================访问isec平台接口：" + url + "====================================");
		if (StringUtil.isNotEmpty(url)) {// 访问链接不为空
			request.getRequestDispatcher(url).forward(request, response);// 转发请求
		} else {
			logger.info("============================该方法不存在method：" + method + "=================================");
			throw new RuntimeException("该方法不存在method：" + method);
		}
	}

}
