package com.rainsoft.riplat.web.mgrparam.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.mgrparam.model.Platform;
import org.springframework.web.servlet.support.RequestContext;

import java.util.List;

public interface IPlatformService extends IMybatisBasePersitenceService<Platform, String> {
    
    /**
     * 获取platform类型
     */
    public List<Platform> getPlatformTypeList();
    
    /**
     * 保存平台信息
     * @param platform 平台信息
     * @param requestContext 请求上下文
     * @return Result
     * @throws Exception
     */
    public Result savePlatform(Platform platform, RequestContext requestContext) throws Exception;

	/**
	 * 修改平台信息
	 * @param platform 平台实体
	 * @param requestContext 
	 * @return Result
	 * @throws Exception
	 */
	public Result updatePlatform(Platform platform, RequestContext requestContext) throws Exception;
    /**
     * 更改平台信息的激活状态
     * @param platformId
     */
    public void activeLicense(String platformId);

    /**
     * 根据id删除平台的信息
     */
    public Integer deleteById(String id);

    /**
     * 根据id删除平台的信息
     */
    public Integer deletePlatform(Platform platform);
	/**
	 * 根据平台IP获取平台信息
	 * @param platformIP 平台IP
	 * @return list 平台信息结果集
	 * @throws Exception 
	 */
	public List<Platform> getPlatInfoByIP(String platformIP) throws Exception;

	/**
	 * 根据平台ID获取平台信息
	 * @param platformId 平台ID
	 * @return list 平台信息结果集
	 */
	public List<Platform> getPlatInfoByID(String platformId) throws Exception;
	
	/**
     * 获取platform所有平台绑定码plateFormID
     */
    public List<Platform> getPlatformIdList();
    /**
     * 随机生产
     * @param length
     * @param list
     * @return
     */
    public String getStrRandom(int length);
}