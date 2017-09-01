package com.rainsoft.riplat.web.notice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.model.AppuserServiceCode;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.model.PlatformUser;
import com.rainsoft.riplat.web.notice.service.IPlatformUserService;
import com.rainsoft.riplat.web.notice.service.ISyncDataService;
import com.rainsoft.riplat.web.notice.service.impl.NoticeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping("/SyncDataService")
public class SyncDataController extends SpringBaseController {

	private static final Logger logger = LoggerFactory.getLogger(SyncDataController.class);
	private static final String AUTHDIGEST = "EYUN";//HTTP消息头请求的digest
	private static final String RESDIGEST= "3uUGXPEC844=";//返回消息头加密信息：EYUN

	@Autowired
	private ISyncDataService syncDataServiceImpl;
	
	@Autowired
	private IPlatformUserService platformUserServiceImpl;
	
	@Resource
    private NoticeServiceImpl noticeServiceImpl;
	
	/** 
	 * 同步易达号与场所的绑定关系
	 * 
	 * @param params
	 */
	@RequestMapping(value = "/edaBindServiceCode", method = RequestMethod.POST)
	public void saveEdaBindServiceCode(@ModelAttribute("params") String params) {

		String json = null;
		Result result = null;
		try {
			if (validateHttpHeader()) {// 验证消息头
				json = desDecrypt(params);// 统一解密params
				if (json != null) {
					List<AppuserServiceCode> jsonArr = JSONArray.parseArray(json,AppuserServiceCode.class);
					if (jsonArr != null) {
						result = syncDataServiceImpl.syncAppuserServiceCode(jsonArr);
					}
				}
			} else {// HTTP消息头错误
				result = new Result();
				result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
			}
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR_INTERFACE);
			e.printStackTrace();
		}
		if (result == null) {// 当参数不正确时
			result = new Result();
			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
		}
		this.responseAjaxData(result, "appuserServiceCode");
	}
	
	/** 
	 * 易达账号信息同步
	 * 
	 * @param params
	 */
	@RequestMapping(value = "/edaAccount", method = RequestMethod.POST)
	public void saveEdaAccount(@ModelAttribute("params") String params) {

		String json = null;
		Result result = null;
		try {
			if (validateHttpHeader()) {// 验证消息头
				json = desDecrypt(params);// 统一解密params
				if (json != null) {
					EdaAppMembers edaObj = JSONObject.parseObject(json, EdaAppMembers.class);
					if (edaObj != null) {
						result = syncDataServiceImpl.syncEdaAcccount(edaObj);
					}
				}
			} else {// HTTP消息头错误
				result = new Result();
				result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
			}
		} catch (Exception e) {
			result = new Result();
			result.setStatus(Constants.RETURN_ERROR_INTERFACE);
			e.printStackTrace();
		}
		if (result == null) {// 当参数不正确时
			result = new Result();
			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
		}
		this.responseAjaxData(result, "noticeList");
	}	
			
	/**
	 * 验证消息请求方是否为合法的请求方
	 * Authorization：
	 * @return
	 */
	public boolean validateHttpHeader() {
		Enumeration<String> emuHeaderNames = getRequest().getHeaderNames();
		boolean flag = false;
		while (emuHeaderNames.hasMoreElements()) {
			String name = (String) emuHeaderNames.nextElement();
			if ("authorization".equals(name)) {//authorization
				String authHeader = null;
				try {
					authHeader = DesUtil.decrypt(getRequest().getHeader(name));//解密digest消息头
					logger.info("authHeader: "+authHeader);
				} catch (Exception e) {
					logger.warn("解密HTTP消息头信息失败,authHeader:"+authHeader);
					e.printStackTrace();
				}
				if(AUTHDIGEST.equals(authHeader)){//与本地消息头进行对比
					flag = true;
					logger.info("HTTP消息头验证通过");
					return flag;
				}else{
					flag = false;
					logger.info("HTTP消息头未验证通过");
				}
			}
			logger.info("Http 元素名称：name:"+name);
		}
		return flag;
	}
	
	/** 
	 * 同步其他平台的用户账号信息
	 * 
	 * @param params
	 */
    @RequestMapping(value = "/platformUser", method = RequestMethod.POST)
    public void savePlatformUser(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    String platformId = jsonObj.getString("platformId");//获取平台Id
                    if (noticeServiceImpl.validatePlatformId(platformId)) {//验证是否为已激活的platformId
                        String userString = jsonObj.getString("users");//获取多个用户的json
                        List<PlatformUser> platformUserList = JSONArray.parseArray(userString, PlatformUser.class);
                        for(PlatformUser platformUser : platformUserList){
                            platformUser.setPlatformId(platformId);
                            result = syncDataServiceImpl.syncPlatformUser(platformUser);
							logger.info("SyncDataController.platformUser-->同步用户结果：result status:"+result.getStatus());
                            if(!Constants.RETURN_SUCCESS_INTERFACE.equals(result.getStatus())){//当存在失败的则返回FALSE
                                result.setStatus(Constants.RETURN_ERROR_INTERFACE);
                                return;
                            }
                        }
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                    }else{
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        this.responseAjaxData(result, "platformUser");
    }

	/**
	 * 
	 * 加密的Ajax返回
	 * 
	 * @param result
	 * @param method
	 */
	public void responseAjaxData(Result result, String method) {
		// 定义返回的数据类型：json
		getResponse().setContentType("application/octet-stream");
		getResponse().setCharacterEncoding("UTF-8");
		String resDigest = null;
		resDigest = RESDIGEST;
		getResponse().addHeader("authorization",resDigest);//消增加息头信息由信息发布平台返回的HTTP信息  WWW-Authorization
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			if (pw != null) {
				String returnJson = JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
				logger.info("调用webservice " + method + " returnJSon: " + returnJson);
				String returnDesJson = DesUtil.encrypt(returnJson);
				logger.info("调用webservice " + method + " returnDesJson: " + returnDesJson);
				pw.print(returnDesJson);
			}
		} catch (Exception e) {
			logger.error("返回接口调用返回值失败");
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	

	/**
     * 解密
     *
     * @param params 字符串
     * @return 字符串
     */
    private String desDecrypt(String params) {
        String desStr = "";
        try {
            logger.info("解密前Params：" + params);
            desStr = DesUtil.decrypt(params);
            logger.info("解密后Params：" + desStr);
        } catch (Exception e) {
            logger.error("解密失败");
        }
        return desStr;
    }

    /**
     * 加密
     *
     * @param params 字符串
     * @return 字符串
     */
    private String desEncrypt(String params) {
        String desStr = "";
        try {
            logger.info("加密前Params：" + params);
            desStr = DesUtil.encrypt(params);
            logger.info("加密后Params：" + desStr);
        } catch (Exception e) {
            logger.error("加密失败");
        }
        return desStr;
    }
    
    @Override
	protected IMybatisBasePersitenceService getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}
}
