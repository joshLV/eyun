package com.rainsoft.riplat.web.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.IMemberGroupDao;
import com.rainsoft.riplat.web.notice.model.MemberGroup;

@Repository
public class MemberGroupDaoImpl extends MybatisPersitenceDaoImpl<MemberGroup, String> implements IMemberGroupDao {
    
    @Override
    public Integer deleteByGroupId(String id) {
        return this.getSqlSession().delete(getSqlName("deleteByGroupId"), id);
    }
}

