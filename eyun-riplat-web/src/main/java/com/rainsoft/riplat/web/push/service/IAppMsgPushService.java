package com.rainsoft.riplat.web.push.service;

import java.util.List;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.riplat.web.push.model.AppToken;

/*APP消息推送服务接口*/
public interface IAppMsgPushService extends IMybatisBasePersitenceService<AppToken, String> {
	
	//推送APP消息
	public boolean pushMsg(String params) throws Exception;
	
	/**
	 * 
	 * @Description: 方法功能说明： 根据edaId获取tokenList
	 * @param list
	 * @return
	 * @return List<AppToken>
	 * @author yty
	 * @date 2016年8月17日下午1:59:06
	 */
	public List<AppToken> getTokenList(List<AppToken> list) throws Exception;
//	/**
//	 * 
//	 * @Description: 方法功能说明： 推送消息
//	 * @param list
//	 * @param content
//	 * @return
//	 * @return Result
//	 * @author yty
//	 * @date 2016年9月28日下午4:09:33
//	 */
//	public Result pushMessage(List<AppToken> list, String content, String appType, String actionType) throws Exception;
}
