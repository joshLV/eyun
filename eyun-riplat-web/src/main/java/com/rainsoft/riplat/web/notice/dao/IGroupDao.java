package com.rainsoft.riplat.web.notice.dao;

import java.util.List;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.riplat.web.notice.model.Group;

public interface IGroupDao extends IMybatisPersitenceDao<Group, String> {

	List<BaseArea> getBaseAreaByUserId(String userId,String platformId);
	 /**
     * 功能说明：根据id获取组的信息
     * @param id 组id
     * @return Group组实体对象
     */
	Group getGroupWithId(Group group);

	/**
	 * 根据用户管辖的
	 * @param group
	 * @return 返回组信息实体
	 */
    Group loadGroupData(Group group);
    
    Group findById(String id);
    
    
    /**
     * 功能说明：根据groupId删除组信息
     * @param groupId 组id
     * @return  int 影响行数
     */ 
    int delete(String id);
    
    
}