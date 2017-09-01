package com.rainsoft.riplat.web.notice.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.base.web.system.model.BaseArea;
import com.rainsoft.riplat.web.notice.dao.IGroupDao;
import com.rainsoft.riplat.web.notice.model.Group;

@Repository
public class GroupDaoImpl extends MybatisPersitenceDaoImpl<Group, String> implements IGroupDao {

	@Override
	public List<BaseArea> getBaseAreaByUserId(String userId,String platformId) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userId);
		paramMap.put("platformId", platformId);
		return this.getSqlSession().selectList(getSqlName("getBaseAreaByUserId"), paramMap);
	}

	@Override
	public Group getGroupWithId(Group group) {
		return this.getSqlSession().selectOne(getSqlName("getGroupWithId"), group);
	}

    @Override
    public Group loadGroupData(Group group) {
        return this.getSqlSession().selectOne(getSqlName("loadGroupData"), group);
    }


    /**
     * 功能说明：根据id获取组的信息
     * @param id 组id
     * @return Group 组实体对象
     */
    public Group findById(String id) {
        return this.getSqlSession().selectOne(getSqlName("findById"), id);
    }
    
   
    /**
     * 功能说明：根据group编辑组的信息
     * @param group组实体对象
     * @return Integer 影响行数
     */
   /* public Integer update(Group group) {
        
    	return  this.getSqlSession().update(getSqlName("updateGroup"),group);
    }*/
    /**
     * 功能说明：根据group编辑组的信息
     * @param group组实体对象
     * @return Integer 影响行数
     */
    public int delete(String id){
      
    	return this.getSqlSession().delete(getSqlName("deleteById"), id);
  }
}

