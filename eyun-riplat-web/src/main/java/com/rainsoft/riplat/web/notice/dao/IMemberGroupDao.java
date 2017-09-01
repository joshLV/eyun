package com.rainsoft.riplat.web.notice.dao;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.riplat.web.notice.model.MemberGroup;

public interface IMemberGroupDao extends IMybatisPersitenceDao<MemberGroup, String> {

    Integer deleteByGroupId(String id);

   
}