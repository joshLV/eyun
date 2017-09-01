package com.rainsoft.riplat.web.mgrparam.service.impl;

import ch.qos.logback.classic.Logger;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.mgrparam.dao.IPlatformDao;
import com.rainsoft.riplat.web.mgrparam.model.Platform;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class PlatformServiceImpl extends MybatisBasePersitenceServiceImpl<Platform, String> implements IPlatformService {
	private static Logger logger = (Logger)LoggerFactory.getLogger(PlatformServiceImpl.class);
	@Resource
    private IPlatformDao platformDaoImpl;

    @Override
    protected IMybatisPersitenceDao<Platform, String> getBaseDao() {
        return platformDaoImpl;
    }

    @Override
    public List<Platform> getPlatformTypeList() {
        return platformDaoImpl.getPlatformTypeList();
    }

    @Override
    public void activeLicense(String platformId) {
      platformDaoImpl.activeLicense(platformId);
    }


	/**
	 * 删除平台信息
	 * 说明：先根据数据库主键删除数据库中的平台信息，再根据平台ID删除缓存中的平台信息
	 * @param platform
	 * @return
	 */
	@Override
	public Integer deletePlatform(Platform platform) {
		Integer row = super.deleteById(platform.getId()+"");
		if(row != null && row > 0){
			/*********** start of 删除数据库中数据成功后，要同时删除缓存中的平台信息 ************/
			String platformId = platform.getPlatformId();
			Map<String,Map<String, String>>  detailMap = Constants.PLATFORM_DETAIL_MAP;
			if(detailMap != null && !detailMap.isEmpty()) {
				if (detailMap.containsKey(platformId)) {
					detailMap.remove(platformId);
					Constants.PLATFORM_MAP.remove(platformId);
				}
			}
			/*********** end of 删除数据库中数据成功后，要同时删除缓存中的平台信息 ************/
		}
		return row;
	}
	
    
	@Override
	public List<Platform> getPlatInfoByIP(String platformIP) throws Exception {
		return platformDaoImpl.getPlatInfoByIP(platformIP);
	}

	@Override
	public List<Platform> getPlatInfoByID(String platformId) throws Exception {
		return platformDaoImpl.getPlatInfoByID(platformId);
	}

  /**
   * 
   * 檢查平台绑定码/名称名称唯一性
   * 1、平台绑定码必须唯一
   * 2、平台名称必须唯一
   * 3、平台名称 必须名称
   * @param platform
   * @param requestContext
   * @return result
   * @throws Exception
   */
	private Result check(Platform platform, RequestContext requestContext) throws Exception {
		Result result = new Result();
		Integer resultType =null;
		Integer count = platformDaoImpl.findCountByKeyId("checkPlatformName", platform);
		if(count == null){
			result.setCount(0);
		}else{
			result.setCount(count);
		}
		logger.info("检查名称是否存在返回：" + count);
		if(count == null) {
			count = platformDaoImpl.findCountByKeyId("checkPlatformIP", platform);
			if(count == null){
				result.setCount(0);
			} else {
				result.setCount(count);
			}
			logger.info("检查平台IP是否存在返回：" + count);
			if(count == null){
				 count = platformDaoImpl.findCountByKeyId("checkPlatformId", platform);
				 if(count == null){
					result.setCount(0);
				 } else {
					result.setCount(count);
				 }
				 logger.info("检查平台绑定码是否存在返回：" + count);
				 if(count == null){
					 
				 } else {
					 resultType = -3;
				 }
			} else {
				resultType = -2;
			}
		} else {
			resultType = -1;
		}
		if(resultType != null){
			if(resultType == -1){
				result.setStatus(Constants.RETURN_EXIST);
				result.setMessage(requestContext.getMessage("平台名称重复,请重新输入"));
			} else if(resultType == -2){
				result.setStatus(Constants.RETURN_EXIST);
				result.setMessage(requestContext.getMessage("平台IP重复,请重新输入"));
			} else {
				result.setStatus(Constants.RETURN_EXIST);
				result.setMessage(requestContext.getMessage("平台绑定码重复,请重新输入"));
			}
		}
		return result;
	}
	
	 
	
	/**
	 * 保存平台信息
	 * @param platform
     * @param requestContext
     * @return result
     * @throws Exception
	 */
	public Result savePlatform(Platform platform, RequestContext requestContext) throws Exception {
		Result result = null;
		logger.info("处理结果含义resultType: 1:平台信息保存成功； -1：平台名称重复；-2：平台IP重复； -3：平台绑定码重复 ；-4：保存平台信息失败；");
		result = check(platform,requestContext);
		if(result !=null && result.getCount() > 0){
			return result;
		}
		logger.info("返回结果："+result);
		Integer count =  platformDaoImpl.save(platform);
		if(count != null && count > 0){
			/*********** start of 保存到数据库成功后，要同时向缓存中增加平台信息 ************/
			platform.setActiveable(Constants.DISABLE);
			platform.setActiveableName("已激活");
			String platformId = platform.getPlatformId();
			Map<String,Map<String, String>>  detailMap = Constants.PLATFORM_DETAIL_MAP;
			if(detailMap != null && !detailMap.isEmpty()) {
				if (!detailMap.containsKey(platformId)) {
					Map<String, String> platMap = new HashMap<String, String>();
					platMap.put("activeableName", platform.getActiveableName());
					platMap.put("platformTypeName", platform.getPlatformTypeName());
					platMap.put("activeable", platform.getActiveableName());
					platMap.put("platformType", platform.getActiveableName());
					platMap.put("platformId", platform.getActiveableName());
					platMap.put("platformName", platform.getActiveableName());
					platMap.put("platformIP", platform.getPlatformIP());
					detailMap.put(platform.getPlatformId(),platMap);

					Constants.PLATFORM_MAP.put(platform.getPlatformId(), platform.getActiveable());
				}
			}
			/*********** end of 保存到数据库成功后，要同时向缓存中增加平台信息 ************/
			String idStr =  getStrRandom(8);
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("保存成功"));
			result.setData(idStr);
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("保存失败"));
		}
		return result;
	}

	/**
	 * 修改平台信息
	 * @param platform
     * @param requestContext
     * @return result
     * @throws Exception
	 */
	public Result updatePlatform(Platform platform, RequestContext requestContext) throws Exception {
		Result result = null ;
		logger.info("处理resultType说明: 1:更新成功； -1：平台名名称重复；-2：平台IP重复； -3：平台绑定码重复 ；-4：更新平台信息失败；");
		result = check(platform,requestContext);
		if(result != null && result.getCount() > 0){
			return result;
		}
		logger.info("返回结果："+ result);
		Integer count = platformDaoImpl.update(platform);
		logger.info("更新平台信息返回：" + count);
		if(count != null && count > 0){
			/************** start of 更新数据库中的平台Ip成功后，更新缓存中的平台IP信息***************/
			String platformId = platform.getPlatformId();
			Map<String,Map<String, String>>  detailMap = Constants.PLATFORM_DETAIL_MAP;
			if(detailMap != null && !detailMap.isEmpty()){
				if(detailMap.containsKey(platformId)){
					Map<String,String> platformMap = detailMap.get(platformId);
					platformMap.put("platformIP", platform.getPlatformIP());
				}
			}
			/************** end of 更新数据库中的平台Ip成功后，更新缓存中的平台IP信息***************/
			result.setStatus(Constants.RETURN_SUCCESS);
			result.setMessage(requestContext.getMessage("更新成功"));
		}else{
			result.setStatus(Constants.RETURN_ERROR);
			result.setMessage(requestContext.getMessage("更新失败"));
		}
		return result;
	}

	/**
	 *获取所有平台绑定码
	 */
	public List<Platform> getPlatformIdList() {
		return platformDaoImpl.findPlatformIdList();
	}
	
	/**
	 * 随机生成字母和数字合成的platFormId	
	 */
	public String getStrRandom(int length) {
		List<Platform> list = getPlatformIdList();
		String idStr = "";
		Random random = new Random();
		// 参数length，表示生成几位随机数
		Boolean check = true;
		while (check) {
			for (int i = 0; i < length; i++) {

				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
				// 输出字母还是数字
				if ("char".equalsIgnoreCase(charOrNum)) {
					// 输出是大写字母还是小写字母
					int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
					idStr += (char) (random.nextInt(26) + temp);
				} else if ("num".equalsIgnoreCase(charOrNum)) {
					idStr += String.valueOf(random.nextInt(10));
				}
			}
			idStr = idStr.toLowerCase();
			int i = 0;
			for (Platform plat : list) {
				if (idStr.equals(plat.getPlatformId())) {
					i++;
				}
			}
			if (i == 0) {
				check = false;
			}
		}
		return idStr;
	}
}