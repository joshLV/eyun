package com.rainsoft.riplat.web.push.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.riplat.web.push.model.AppToken;

/*
 APP推送请求对象
 */
public class AppMsgPushReq {
	

	private String content ="";//推送消息
	
	private List<AppToken> edaIdList = new ArrayList<AppToken>();//易达idList

	private List<AppToken> tokenList = new ArrayList<AppToken>();//根据请求的易达id查出的数据
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<AppToken> getEdaIdList() {
		return edaIdList;
	}
	public void setEdaIdList(List<AppToken> edaIdList) {
		this.edaIdList = edaIdList;
	}
	public List<AppToken> getTokenList() {
		return tokenList;
	}
	public void setTokenList(List<AppToken> tokenList) {
		this.tokenList = tokenList;
	}

	//根据平台类型返回对应的tokenList
	public List<AppToken> getTokenListByNotfiyPlatform (String notfiyPlatform) {
		String token = "";
		List<AppToken> list = new ArrayList<AppToken>();
		for(AppToken appToken: tokenList) {
			token = appToken.getMobileToken();
			if(StringUtil.isNotEmpty(token) && notfiyPlatform.equals(appToken.getNotifyPlatform())) {
				list.add(appToken);
			}
		}
		return list;
	}
}
