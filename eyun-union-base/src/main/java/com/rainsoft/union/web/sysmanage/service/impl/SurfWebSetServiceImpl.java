package com.rainsoft.union.web.sysmanage.service.impl;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.model.AuthMethodEnum;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.*;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.dao.ISurfWebSetDao;
import com.rainsoft.union.web.sysmanage.model.SurfWebSet;
import com.rainsoft.union.web.sysmanage.service.ISurfWebSetService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *上网设置
 */
@Service("surfWebSetService")
public class SurfWebSetServiceImpl extends MybatisBasePersitenceServiceImpl<SurfWebSet, String> implements ISurfWebSetService {
	private static Logger logger = (Logger)LoggerFactory.getLogger(SystemParamServiceImpl.class);

	@Resource
	private ISurfWebSetDao surfWebSetDao;
	
	@Override
	protected IMybatisPersitenceDao<SurfWebSet, String> getBaseDao() {
		return surfWebSetDao;
	}

	/**
	 * 更新上网设置
	 */
	public Result updateSurfWebSet(SurfWebSet surfWebSet, HttpServletRequest request) throws Exception{
		RequestContext requestContext = new RequestContext(request);
		Result result = new Result();
		Integer count = null;
		SurfWebSet surferSet= null;
		//获取场所 placeId 若有placeId 则为修改，否则为新增
		try {
			surferSet = surfWebSetDao.findBy("getPlaceById", surfWebSet);
			if(surferSet != null && surferSet.getPlaceId() != null ){
				count = surfWebSetDao.update("updFeeTimeSet", surfWebSet);
				logger.info("设置上网时长返回结果："+ count);
			} else {
				count = surfWebSetDao.save("saveFeeTimeSet", surfWebSet);
				logger.info("没有设置上网时长时，添加上网时长返回结果："+ count);
			}
			if (count != null && count > 0) {
				result.setStatus(Constants.RETURN_SUCCESS);
				result.setMessage(requestContext.getMessage("上网设置成功！"));
				Map< String , Object> map = new HashMap<String, Object>() ;
				map.put("serviceCode", surfWebSet.getPlaceCode());
				map.put("netTime",surfWebSet.getFreeTime().toString());
				if(null != surfWebSet.getPlaceCode() && surfWebSet.getPlaceCode().length() > 0 && null != surfWebSet.getFreeTime()){
					String url =SystemConfigUtil.getValue("AUTHlOGIN_CENTER") ;
					url=url+ AuthMethodEnum.synNetTime.getValue();
					String params = JSONObject.toJSONString(map);
					if(StringUtil.isNotEmpty(params)){
						params = DesUtil.encrypt(params);
					}
					String resultStr = HttpUtil.portConnect(url, params);
					String data = DesUtil.decrypt(resultStr);
					JSONObject jsonObject = JSONObject.parseObject(data);
					String str = (String) jsonObject.get("result");
					logger.info("同步上网时长返回值：" +str);
				}
			} else {
				result.setStatus(Constants.RETURN_ERROR);
				result.setMessage(requestContext.getMessage("上网设置失败！"));
			}
		} catch (Exception e) {
			logger.error("上网设置失败："+e.getMessage());
			throw new Exception("上网设置失败！");
		}
		return result;
	}
}
