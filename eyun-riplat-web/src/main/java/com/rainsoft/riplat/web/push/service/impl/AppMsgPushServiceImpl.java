package com.rainsoft.riplat.web.push.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.HttpUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.riplat.web.push.dao.IAppMsgPushDao;
import com.rainsoft.riplat.web.push.model.AppToken;
import com.rainsoft.riplat.web.push.service.IAppMsgPushService;

/*APP消息推送服务*/
@Service("appMsgPushService")
public class AppMsgPushServiceImpl extends MybatisBasePersitenceServiceImpl<AppToken, String> implements IAppMsgPushService {
	private static final Logger logger = LoggerFactory.getLogger(AppMsgPushServiceImpl.class);
	@Resource
	private IAppMsgPushDao appMsgPushDao;

	@Override
	protected IMybatisPersitenceDao<AppToken, String> getBaseDao() {
		return appMsgPushDao;
	}

	// 推送APP消息
	public boolean pushMsg(String params) throws Exception {
//		if (req == null) {
//			logger.info("AppMsgPushServiceImpl.pushMsg-->********************请求为空");
//			return false;
//		}
		/* 根据易达号查询手机通知token和手机厂家类型列表，并设置到AppMsgPushReq */
//		List<AppToken> tokenList = getTokenList(req.getEdaIdList());
//		req.setTokenList(tokenList);

		// 推送平台url
		String notify_url = SystemConfigUtil.getValue("APP_NOTIFY_CENTER");
		logger.info("======================推送url：" + notify_url + "==============================");
//		String params = "";
//		JSONObject obj = new JSONObject();
//		JSONObject message = new JSONObject();
//		message.put("content", req.getContent());
//		message.put("title", "您有一条新消息！");
//		message.put("appType", "GONGAN");
//		message.put("actionType", "NOTIFY");
//		obj.put("message", message);
//		String jsonStr = JSON.toJSONString(tokenList);
//		obj.put("tokenList", jsonStr);
//		logger.info("======================参数加密前：" + obj + "==============================");
//		params = DesUtil.encrypt(obj.toJSONString());
//		logger.info("======================参数加密后：" + params + "==============================");
//		logger.info("======================参数加密后长度：" + params.length() + "==============================");
		String result = HttpUtil.portConnect(notify_url, params);
		logger.info("======================推送消息结果：" + result + "==============================");
		result = DesUtil.decrypt(result);
		logger.info("======================推送消息结果解密：" + result + "==============================");
		JSONObject obj = JSONObject.parseObject(result);
		if(null != obj && Constants.RETURN_SUCCESS_INTERFACE.equals(obj.getString("status"))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<AppToken> getTokenList(List<AppToken> list) throws Exception {
		return appMsgPushDao.selectList("getTokenList", list);
	}

//	@Override
//	public Result pushMessage(List<AppToken> list, String content, String appType, String actionType) throws Exception {
//		Result resultEntity = new Result();
//		List<AppToken> tokenList = getTokenList(list);
//		// 推送平台url
//		String notify_url = SystemConfigUtil.getValue("APP_NOTIFY_CENTER");
//		logger.info("======================推送url：" + notify_url + "==============================");
//		String params = "";
//		JSONObject obj = new JSONObject();
//		JSONObject message = new JSONObject();
//		message.put("content", content);
//		message.put("title", "您有一条新消息！");
//		message.put("appType", appType);
//		message.put("actionType", actionType);
//		obj.put("message", message);
//		String jsonStr = JSON.toJSONString(tokenList);
//		obj.put("tokenList", jsonStr);
//		logger.info("======================参数加密前：" + obj + "==============================");
//		params = DesUtil.encrypt(obj.toJSONString());
//		logger.info("======================参数加密后：" + params + "==============================");
//		logger.info("======================参数加密后长度：" + params.length() + "==============================");
//		String result = HttpUtil.portConnect(notify_url, params);
//		logger.info("======================推送消息结果：" + result + "==============================");
//		result = DesUtil.decrypt(result);
//		logger.info("======================推送消息结果解密：" + result + "==============================");
//		resultEntity = JSON.parseObject(result, Result.class);
//		return resultEntity;
//	}
}
