package com.rainsoft.riplat.web.isec.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.HttpUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.base.common.web.vo.PagedListResult;
import com.rainsoft.riplat.web.isec.service.IBaseIsecService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author huxiaolong
 *
 */
@Service(value="baseIsecService")
public class BaseIsecServiceImpl implements IBaseIsecService {
	
	 protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 获取isec平台IP地址
	 * @author huxiaolong
	 * @param platformid 平台ID
	 * @return data:JSONArray
	 * @throws Exception 参数格式错误
	 */
	public String findUrlByPlatformId(String platformid) {
		Map<String, String> platfMap = Constants.PLATFORM_DETAIL_MAP.get(platformid);
		return platfMap == null ? null : platfMap.get("platformIP");
	}

	/**
	 * 将接口返回data参数进行格式化
	 * @param json 接口返回数据
	 * @return data:JSONArray
	 * @throws Exception 参数格式错误
	 */
	public JSONArray toJSONArray(JSONObject json) throws Exception{
		final char[] strChar = json.getString("data").substring(0, 1).toCharArray(); 
	    final char firstChar = strChar[0];
	    JSONArray array = new JSONArray();
	    if(firstChar == '{'){ 
	    	array.add(JSON.parseObject(json.getString("data"))); 
        } else if(firstChar == '['){ 
        	array = JSON.parseArray(json.getString("data"));
        } else { 
        	throw new Exception("isec返回参数格式不合法！"); 
        }
		return array;
	}
	
	/**
	 * 处理Isec接口调用及返回参数
	 * @param params 请求参数
	 * @param urlPostfix URL后缀
	 * @return result 接口返回数据
	 * @throws Exception 参数格式错误
	 */
	public PagedListResult processIsecMsg(String params, String method) throws Exception {
		PagedListResult result = new PagedListResult();
 		JSONObject param = JSON.parseObject(params);
		logger.info("请求参数：" + param);
        if (StringUtil.isEmpty(method)) {
            logger.error("请求参数(method)为空！");
        }
		String platIP = findUrlByPlatformId(param.getString("platformid"));
		logger.info("IscIp地址：" +platIP);
		if (StringUtil.isEmpty(platIP)) {
		    logger.error("获取IsecIP地址失败！");
		}
		
		String desRsuit = HttpUtil.portConnect(platIP + SystemConfigUtil.getValue("ISEC_INTERFACE") + "?method=" + method, DesUtil.encrypt(params));
		logger.info("调用接口返回加密串：" + desRsuit);
		if (!StringUtil.isEmpty(desRsuit)) {
			String desData = DesUtil.decrypt(desRsuit);
			logger.info("调用接口返回解密串：" + desData);
			param = JSON.parseObject(desData);
			logger.info("返回解密串转Json串：" + param);
			result.setMessage(param.getString("message"));
			result.setStatus(param.getString("status"));
			if(param.containsKey("currentPage")){
				result.setCurrentPage(param.getString("currentPage"));
			}
			if(param.containsKey("totalPage")){
				result.setTotalPage(param.getString("totalPage"));
			}
			try {
				result.setData(toJSONArray(param));
			} catch (Exception e){
				result.setData(null);
			}
		} else {
			return null;
		}
		return result;
	}
	
	/**
     * 处理Isec接口调用及返回参数
     * @param params 请求参数
     * @param urlPostfix URL后缀
     * @return result 接口返回数据
     * @throws Exception 参数格式错误
     */
	public PagedListResult processIsecMsg1(String params, String urlPostfix) throws Exception {
        PagedListResult result = new PagedListResult();
        JSONObject param = JSON.parseObject(params);
        logger.info("请求参数：" + param);
        String platIP = findUrlByPlatformId(param.getString("platformid"));
        logger.info("IscIp地址：" +platIP);
        if (StringUtil.isEmpty(platIP)) {
            throw new Exception("获取IsecIP地址失败！");
        }
        logger.info("接口地址：" +urlPostfix);
        String desRsuit = HttpUtil.portConnect(platIP + urlPostfix, DesUtil.encrypt(params));
        logger.info("调用接口返回加密串：" + desRsuit);
        if (!StringUtil.isEmpty(desRsuit)) {
            String desData = DesUtil.decrypt(desRsuit);
            logger.info("调用接口返回解密串：" + desData);
            param = JSON.parseObject(desData);
            logger.info("返回解密串转Json串：" + param);
            result.setMessage(param.getString("message"));
            result.setStatus(param.getString("status"));
            if(param.containsKey("currentPage")){
                result.setCurrentPage(param.getString("currentPage"));
            }
            if(param.containsKey("totalPage")){
                result.setTotalPage(param.getString("totalPage"));
            }
            try {
                result.setData(toJSONArray(param));
            } catch (Exception e){
                result.setData(null);
            }
        } else {
            return null;
        }
        return result;
    }
	
}