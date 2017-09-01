package com.rainsoft.base.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 功能说明：Http与Servlet工具类.
 * 
 * @author admin
 * @created 2014年6月12日 下午12:55:17
 * @updated
 */
public class ServletUtils {

	// -- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	// -- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	// -- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 能说明：设置客户端缓存过期时间 的Header
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:56:07
	 * @updated
	 * @param response
	 * @param expiresSeconds
	 *            过期时间单位（s）
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {

		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);

	}

	/**
	 * 功能说明：设置禁止客户端缓存的Header
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:56:21
	 * @updated
	 * @param response
	 */
	public static void setDisableCacheHeader(HttpServletResponse response) {

		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");

	}

	/**
	 * 功能说明：设置LastModified Header
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:57:18
	 * @updated
	 * @param response
	 * @param lastModifiedDate
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {

		response.setDateHeader("Last-Modified", lastModifiedDate);

	}

	/**
	 * 功能说明：设置Etag Header
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:57:32
	 * @updated
	 * @param response
	 * @param etag
	 */
	public static void setEtag(HttpServletResponse response, String etag) {

		response.setHeader("ETag", etag);

	}

	/**
	 * 功能说明：根据浏览器If-Modified-Since Header, 计算文件是否已被修改. 如果无修改,
	 * checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:57:47
	 * @updated
	 * @param request
	 * @param response
	 * @param lastModified
	 *            内容的最后修改时间
	 * @return
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {

		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {

			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;

		}
		return true;

	}

	/**
	 * 功能说明：根据浏览器 If-None-Match Header, 计算Etag是否已无效 如果Etag有效,
	 * checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:58:29
	 * @updated
	 * @param request
	 * @param response
	 * @param etag
	 *            内容的ETag
	 * @return
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {

		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {

			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {

				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {

					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {

						conditionSatisfied = true;

					}

				}

			} else {

				conditionSatisfied = true;

			}

			if (conditionSatisfied) {

				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;

			}

		}
		return true;

	}

	/**
	 * 功能说明：设置让浏览器弹出下载对话框的Header
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午12:59:25
	 * @updated
	 * @param response
	 * @param fileName
	 *            下载后的文件名
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {

		try {

			response.reset();
			// 中文文件名支持,在window服务器上这行代码就可以了
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			;
			// 获取请求request对象(需要在web.xml中配置RequestContextListener)
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			// //在linux服务器上 IE下载必须加上下面这段代码,window也行,
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {

				encodedfileName = URLEncoder.encode(fileName, "UTF-8");

			}
			// 解决中文文件名乱码问题
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {

				// firefox浏览器
				encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {

				// IE浏览器
				encodedfileName = URLEncoder.encode(fileName, "UTF-8");

			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {

				// 谷歌
				encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}

	}

	public static void setFileDownloadHeader(HttpServletResponse response, HttpServletRequest request, String fileName) {

		try {

			response.reset();
			// 中文文件名支持,在window服务器上这行代码就可以了
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			// 解决中文文件名乱码问题
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {

				// firefox浏览器
				encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {

				// IE浏览器
				encodedfileName = URLEncoder.encode(fileName, "UTF-8");

			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {

				// 谷歌
				encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}

	}

	/**
	 * 功能说明：取得带相同前缀的Request Parameters.
	 * 
	 * @author admin
	 * @created 2014年6月12日 下午1:57:05
	 * @updated
	 * @param request
	 * @param prefix
	 * @return 返回的结果的Parameter名已去除前缀.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {

		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {

			prefix = "";

		}
		while (paramNames != null && paramNames.hasMoreElements()) {

			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {

				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {

				} else if (values.length > 1) {

					params.put(unprefixed, values);

				} else {

					params.put(unprefixed, values[0]);

				}

			}

		}
		return params;

	}

}