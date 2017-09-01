package com.rainsoft.riplat.web.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.riplat.web.notice.dao.IEdaAppDao;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.service.IEdaAppService;

@Service
public class EdaAppServiceImpl extends MybatisBasePersitenceServiceImpl<EdaAppMembers, String> implements IEdaAppService {

    @Resource
    private IEdaAppDao edaAppDaoImpl;
    
    @Override
    protected IMybatisPersitenceDao<EdaAppMembers, String> getBaseDao() {
        return edaAppDaoImpl;
    }

    @Override
    public List<EdaAppMembers> findListBy(EdaAppMembers item, String sortColumn, String des) {
        item.setUserId(SpringMvcUtil.getUserId().toString());
        return super.findListBy(item, sortColumn, des);
    }
}