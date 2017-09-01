package com.rainsoft.riplat.web.notice.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.notice.dao.IAccpeterUserDao;
import com.rainsoft.riplat.web.notice.model.AccpeterUser;
import com.rainsoft.riplat.web.notice.service.IAccpeterUserService;

@Service
public class AccpeterUserServiceImpl extends MybatisBasePersitenceServiceImpl<AccpeterUser, String> implements IAccpeterUserService {

    @Resource
    private IAccpeterUserDao accpeterUserDaoImpl;
    
    @Override
    protected IMybatisPersitenceDao<AccpeterUser, String> getBaseDao() {
        return accpeterUserDaoImpl;
    }

}