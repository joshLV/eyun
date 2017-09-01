package com.rainsoft.riplat.web.push.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.push.model.AppToken;
import com.rainsoft.riplat.web.push.service.IAppMsgPushService;

/*消息推送Controller*/
@Controller
@RequestMapping("/Msg_push")
public class MsgPushController extends BaseMsgPushController {

	private static final Logger logger = LoggerFactory.getLogger(MsgPushController.class);
	@Resource
	private IAppMsgPushService appMsgPushService;

	@Override
	protected IMybatisBasePersitenceService<AppToken, String> getBaseService() {
		return appMsgPushService;
	}

	@Override
	protected String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	/* APP消息推送 */
	@RequestMapping(value = "/pushToApp", method = RequestMethod.POST)
	public void pushToApp(@ModelAttribute("params") String params) {

		String json = null;
		Result result = new Result();
		try {
			logger.info("MsgPushController.pushToApp-->********************收到APP消息推送请求");
			if (validateHttpHeader()) {// 验证消息头
				json = desDecrypt(params);// 统一解密params
				if (json != null) {
					logger.info("================================pushToApp请求参数为：" + json + "====================================");
					JSONObject jsonObj = JSONObject.parseObject(json);
					if(jsonObj != null) {
						String platformId = jsonObj.getString("platformId");
						String message = jsonObj.getString("message");
						logger.info("MsgPushController-->********************platformId请求参数" + platformId + ",message请求参数" + message);
						if(StringUtil.isNotEmpty(platformId) && StringUtil.isNotEmpty(message)) {
							if (validatePlatformUser(jsonObj)) {
								if (appMsgPushService.pushMsg(params)) {
									result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
									logger.info("MsgPushController.pushToApp-->********************APP消息推送完毕");
								} else {
									result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
									logger.info("MsgPushController.pushToApp-->********************APP消息推送失败");
								}
							} else {
								logger.error("MsgPushController.buildRequest-->********************ISEC用户名或密码有误");
								result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
							}
						} else {
							logger.error("MsgPushController.buildRequest-->********************platformId， message请求参数验证失败");
							result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
						}
					} else {
						logger.error("MsgPushController.buildRequest-->********************json请求对象为空");
						result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
					}
//					AppMsgPushReq req = buildRequest(jsonObj, result);
//
//					if (appMsgPushService.pushMsg(req)) {
//						result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
//						logger.info("MsgPushController.pushToApp-->********************APP消息推送完毕");
//					} else {
//						result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
//						logger.info("MsgPushController.pushToApp-->********************APP消息推送失败");
//					}
				} else {
					result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
					logger.error("MsgPushController.pushToApp-->********************APP消息推送失败：json字符串为空");
				}
			} else {// HTTP消息头错误
				result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
				logger.error("MsgPushController.pushToApp-->********************APP消息推送失败：httphead验证失败");
			}
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR_INTERFACE);
			logger.error(e.getMessage(), e);
		} finally {
			SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
		}
	}

	/* 构造推送消息请求对象 */
//	private AppMsgPushReq buildRequest(JSONObject jsonObj, Result result) throws Exception {
//		logger.info("MsgPushController.buildRequest-->********************开始构造AppMsgPushReq请求");
//
//		if (jsonObj == null) {
//			logger.error("MsgPushController.buildRequest-->********************json请求对象为空");
//			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//			return null;
//		}
//
//		String platformId = jsonObj.getString("platformId");
//		logger.info("MsgPushController.buildRequest-->********************platformId请求参数" + platformId);
//		if (StringUtil.isEmpty(platformId) || !validatePlatformId(platformId)) {
//			logger.error("MsgPushController.buildRequest-->********************platformId请求参数验证失败");
//			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//			return null;
//		}
//
//		String content = jsonObj.getString("content");
//		if (StringUtil.isEmpty(content)) {
//			logger.error("MsgPushController.buildRequest-->********************content请求参数为空");
//			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//			return null;
//		}
//
//		if (!validatePlatformUser(jsonObj)) {
//			logger.error("MsgPushController.buildRequest-->********************ISEC用户名或密码有误");
//			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//			return null;
//		}
//
//		AppMsgPushReq req = new AppMsgPushReq();
//		req.setContent(content);
//
//		/* 获取请求中的易达账号列表，待实现 */
//		String edaIdListStr = jsonObj.getString("edIdList");
//		if (StringUtil.isEmpty(edaIdListStr)) {
//			logger.error("MsgPushController.buildRequest-->********************edIdList请求参数为空");
//			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//			return null;
//		}
//		List<AppToken> edaIdList = JSON.parseArray(edaIdListStr, AppToken.class);
//		req.setEdaIdList(edaIdList);
//
//		logger.info("MsgPushController.buildRequest-->********************构造AppMsgPushReq请求完成");
//		return req;
//
//	}

	/**
	 * 
	 * @Description: 方法功能说明：app推送token
	 * @param params
	 * @return void
	 * @author yty
	 * @date 2016年8月17日上午11:21:07
	 */
	@RequestMapping(value = "/saveMobileToken", method = RequestMethod.POST)
	public void saveMobileToken(@RequestParam("params") String params) {
		logger.info("========================================接收app上传token============================");
		String json = null;
		Result result = new Result();
		try {
			// 验证消息头
			if (validateHttpHeader()) {
				// 统一解密params
				json = desDecrypt(params);
				logger.info("======================MsgPushController.appPushToken===========解密后jsonStr：" + json + "===================================");
				if (json != null) {
					JSONObject jsonObj = JSONObject.parseObject(json);
					AppToken appToken = new AppToken();
//					String platformId = jsonObj.getString("platformId");
					//判断平台id是否合法
//					if(validatePlatformId(platformId)) {
						String edaId = jsonObj.getString("edaId");
						String notifyPlatform = jsonObj.getString("notifyPlatform");
						if(StringUtil.isNotEmpty(edaId) && StringUtil.isNotEmpty(notifyPlatform)) {
							appToken.setEdaId(edaId);
							appToken.setNotifyPlatform(notifyPlatform);
							appToken.setMobileToken(jsonObj.getString("mobileToken"));
							try {
								if (appMsgPushService.update(appToken) > 0) {
									logger.info("====================MsgPushController.appPushToken=========APPtoken推送成功==============================");
									result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
								} else {
									logger.info("====================MsgPushController.appPushToken=========APPtoken推送失败==============================");
									result.setStatus(Constants.RETURN_ERROR_INTERFACE);
								}
							} catch (Exception e) {
								logger.info("====================MsgPushController.appPushToken=========APPtoken保存修改失败==============================");
								result.setStatus(Constants.RETURN_ERROR_INTERFACE);
								logger.error("APPtoken保存修改失败!" + e);
							}
						} else {
							result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
							logger.error("=================MsgPushController.appPushToken===============APPtoken推送失败：edaId或notifyPlatform为空======================");
						}
//					} else {
//						result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//						logger.error("=================MsgPushController.appPushToken===============APPtoken推送失败：平台id不合法======================");
//					}
				} else {
					result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
					logger.error("=================MsgPushController.appPushToken===============APPtoken推送失败：json字符串为空======================");
				}
			} else {// HTTP消息头错误
				result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
				logger.error("====================MsgPushController.appPushToken=========APPtoken推送失败：httphead验证失败=====================");
			}
		} catch (Exception e) {
			logger.error("====================MsgPushController.appPushToken=========APPtoken推送失败==============================" + e);
		}
		SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
	}
	
//	/**
//	 * 
//	 * @Description: 方法功能说明： 推送消息
//	 * @param params
//	 * @return void
//	 * @author yty
//	 * @date 2016年9月28日下午3:23:38
//	 */
//	@RequestMapping(value = "/pushMessage", method = RequestMethod.POST)
//	public void pushMessage(@RequestParam("params") String params) {
//		logger.error("===============================推送接口pushMessage开始===========================================");
//		Result result = null;
//		if(StringUtil.isNotEmpty(params)) {
//			try {
//				params = DesUtil.decrypt(params);
//				logger.info("================================参数解密params：" + params + "=====================================");
//				JSONObject obj = JSON.parseObject(params);
//				String content = obj.getString("content");
//				String edaIdListStr = obj.getString("edaIdList");
//				String appType = obj.getString("appType");
//				String actionType = obj.getString("actionType");
//				if(StringUtil.isNotEmpty(content) && StringUtil.isNotEmpty(edaIdListStr)) {
//					List<AppToken> list = JSON.parseArray(edaIdListStr, AppToken.class);
//					if(list != null && list.size() > 0) {
//						result = appMsgPushService.pushMessage(list, content, appType, actionType);
//					}
//				}
//			} catch (Exception e) {
//				logger.error("参数解密失败！" + e);
//			}
//		}
//		if(null == result) {
//			logger.info("===============================参数错误！=======================================");
//			result = new Result();
//			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
//			result.setMessage("传入参数错误！");
//		}
//		SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
//		logger.error("===============================推送接口pushMessage结束===========================================");
//	}
}
