package com.rainsoft.riplat.web.isec.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.web.vo.PagedListResult;


public interface IBaseIsecService {
	
	/**
	 * 获取isec平台IP地址
	 * @author huxiaolong
	 * @param platformid 平台ID
	 * @return data:JSONArray
	 * @throws Exception 参数格式错误
	 */
	public String findUrlByPlatformId(String platformid);
	
	/**
	 * 处理Isec接口调用及返回参数
	 * @author huxiaolong
	 * @param params 请求参数
	 * @param urlPostfix URL后缀
	 * @return result 接口返回数据
	 * @throws Exception 参数格式错误
	 */
	public PagedListResult processIsecMsg(String params, String method) throws Exception;

    /**
     * 处理Isec接口调用及返回参数
     * @author huxiaolong
     * @param params 请求参数
     * @param urlPostfix URL后缀
     * @return result 接口返回数据
     * @throws Exception 参数格式错误
     */
    public PagedListResult processIsecMsg1(String params, String urlPostfix) throws Exception;
	
	/**
	 * 将接口返回data参数进行格式化
	 * @author huxiaolong
	 * @param str 接口返回数据
	 * @return data:JSONArray
	 * @throws Exception 参数格式错误
	 */
	public JSONArray toJSONArray(JSONObject json) throws Exception;

}
