package com.rainsoft.riplat.web.notice.dao;

import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.model.PlatformUser;
import com.rainsoft.riplat.web.notice.model.PlatformUserArea;
import org.apache.ibatis.annotations.Param;


public interface ISyncDataDao {

	/**
	 * 新增场所与易达Id绑定关系
	 * @param appuserId
	 * @param serviceCode
	 */
	void saveAppuserServiceCode(@Param("edaId")String edaId,@Param("serviceCode")String serviceCode);

	/**
	 * 删除场所与易达Id绑定关系
	 * @param appuserId
	 * @param serviceCode
	 */
	void deleteAppuserServiceCode(@Param("edaId")String edaId,@Param("serviceCode")String serviceCode);

	/**
	 * 易达账号新增或修改
	 * @param edaObj
	 */
//	void saveOrUpdateEdaAccount(EdaAppMembers edaObj);

	/**
	 * 易达账号删除
	 * @param edaObj
	 */
	void deleteEdaAccount(EdaAppMembers edaObj);

	/**
	 * 易达账号是否存在
	 * @param edaObj
	 */
	int countEdaAccount(EdaAppMembers edaObj);

	/**
	 * 易达账号新增
	 * @param edaObj
	 */
	void addEdaAccount(EdaAppMembers edaObj);

	/**
	 * 易达账号修改
	 * @param edaObj
	 */
	void updateEdaAccount(EdaAppMembers edaObj);

	/**
	 * 查看其他平台用户是否存在
	 * @param platformUser
	 * @return
	 */
	int countPlatformUserAccount(PlatformUser platformUser);

	/**
	 * 其他平台用户新增
	 * @param platformUser
	 * @return
	 */
	void addPlatformUserAccount(PlatformUser platformUser) throws Exception;

	/**
	 * 其他平台用户修改
	 * @param platformUser
	 * @return
	 */
	void updatePlatformUserAccount(PlatformUser platformUser);

	/**
	 * 其他平台用户删除
	 * @param platformUser
	 * @return
	 */
	void deletePlatformUserAccount(PlatformUser platformUser);

	/**
	 * 其他平台用户管理的区域信息--删除
	 * @param platformUser
	 * @return
	 */
	void deletePlatformUserArea(PlatformUserArea userArea);

	/**
	 * 其他平台用户管理的区域信息--新增
	 * @param platformUser
	 * @return
	 */
	void addPlatformUserArea(PlatformUserArea userArea);

	/**
	 * 获取其他平台用户的Id
	 * @param platformUser
	 * @return
	 */
	Integer getPlatformUserId(PlatformUser platformUser);
        
}