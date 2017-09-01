package com.rainsoft.riplat.web.notice.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.dao.ISyncDataDao;
import com.rainsoft.riplat.web.notice.model.AppuserServiceCode;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.model.PlatformUser;
import com.rainsoft.riplat.web.notice.model.PlatformUserArea;
import com.rainsoft.riplat.web.notice.service.ISyncDataService;

@Service
public class SyncDataServiceImpl implements ISyncDataService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SyncDataServiceImpl.class);
	
    @Resource
    private ISyncDataDao iSyncDataDao;


    /**
     * 同步场所与易达号绑定关系
     */
	public Result syncAppuserServiceCode(List<AppuserServiceCode> jsonArr) {
		logger.info("开始同步---同步场所与易达号绑定关系");
		Result result = new Result();
		try{
			for (AppuserServiceCode obj : jsonArr) {
				if ("0".equals(obj.getBindStatus())) {// 绑定
					iSyncDataDao.saveAppuserServiceCode(obj.getEdaId(),
							obj.getServiceCode());
				}
				if ("1".equals(obj.getBindStatus())) {// 解绑
					iSyncDataDao.deleteAppuserServiceCode(obj.getEdaId(),
							obj.getServiceCode());
				}
			}
			result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
			logger.info("开始同步---同步场所与易达号绑定关系--成功");
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR_INTERFACE);//同步失败
			logger.warn("开始同步---同步场所与易达号绑定关系--失败");
			e.printStackTrace();
		}		
		return result;
	}


	@Override
	public Result syncEdaAcccount(EdaAppMembers edaObj) {
		logger.info("开始同步---易达账号信息");
		Result result = new Result();
		try{
			if("0".equals(edaObj.getOperate())){//新增或删除	
				int count = iSyncDataDao.countEdaAccount(edaObj);
				if(count==0){//insert
					iSyncDataDao.addEdaAccount(edaObj);
				}else{ //update
					iSyncDataDao.updateEdaAccount(edaObj);
				}				
			}
			if("1".equals(edaObj.getOperate())){
				iSyncDataDao.deleteEdaAccount(edaObj);
			}
			result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
			logger.info("开始同步---易达账号信息--成功");
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR_INTERFACE);//同步失败
			logger.warn("开始同步---易达账号信息--失败");
			e.printStackTrace();
		}		
		return result;
	}


	@SuppressWarnings("finally")
    @Override
	public Result syncPlatformUser(PlatformUser platformUser) throws Exception {
		logger.info("开始同步---平台编号为"+platformUser.getPlatformId()+"的账户信息");
		Result result = new Result();
		try{
			if("0".equals(platformUser.getOperate())){//新增或修改
				int count = iSyncDataDao.countPlatformUserAccount(platformUser);
				if(count==0){//insert
					iSyncDataDao.addPlatformUserAccount(platformUser);
				}else{ //update
					//先删除管理的区域ID
					PlatformUserArea userArea = new PlatformUserArea();
					Integer userId = iSyncDataDao.getPlatformUserId(platformUser);
					userArea.setUserId(userId==null?null:userId.toString());//决定了唯一的用户
					iSyncDataDao.deletePlatformUserArea(userArea);
					iSyncDataDao.updatePlatformUserAccount(platformUser);
				}
                if (StringUtil.isNotEmpty(platformUser.getAreaIds())) {
                    String[] areaIds = platformUser.getAreaIds().split(",");
                    Integer userId = iSyncDataDao.getPlatformUserId(platformUser);
                    for (String areaId : areaIds) {// 循环插入管理的区域ID
                        if (StringUtil.isNotEmpty(areaId)) {
                            PlatformUserArea userArea = new PlatformUserArea();
                            userArea.setAreaId(areaId);
                            userArea.setCreateTime(new Date());
                            userArea.setUserId(userId == null ? null : userId.toString());
                            iSyncDataDao.addPlatformUserArea(userArea);
                        }
                    }
                }
			}
			if("1".equals(platformUser.getOperate())){//删除
				Integer userId = iSyncDataDao.getPlatformUserId(platformUser);
				//删除管理的区域ID
				PlatformUserArea userArea = new PlatformUserArea();
				userArea.setUserId(userId==null?null:userId.toString());
				iSyncDataDao.deletePlatformUserArea(userArea);
				iSyncDataDao.deletePlatformUserAccount(platformUser);
			}
			result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
			logger.info("同步---平台编号为"+platformUser.getPlatformId()+"--成功");
		} catch (Exception e) {
			result.setStatus(Constants.RETURN_ERROR_INTERFACE);//同步失败
			logger.warn("同步---平台编号为"+platformUser.getPlatformId()+"--失败");
			e.printStackTrace();
		}finally{
		    return result;
		}
	}
	
	
	
	
	
}