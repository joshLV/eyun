package com.rainsoft.riplat.web.notice.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.notice.dao.IMemberGroupDao;
import com.rainsoft.riplat.web.notice.model.MemberGroup;
import com.rainsoft.riplat.web.notice.service.IMemberGroupService;

@Service
public class MemberGroupServiceImpl extends MybatisBasePersitenceServiceImpl<MemberGroup, String> implements IMemberGroupService {

    @Resource
    private IMemberGroupDao memberGroupDaoImpl;
    
    @Override
    protected IMybatisPersitenceDao<MemberGroup, String> getBaseDao() {
        return memberGroupDaoImpl;
    }

    @Override
    public Integer deleteById(String id) {        
        return memberGroupDaoImpl.deleteByGroupId(id);
    }
    
    

}