package com.rainsoft.riplat.web.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.IEdaAppDao;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;

@Repository
public class EdaAppDaoImpl extends MybatisPersitenceDaoImpl<EdaAppMembers, String> implements IEdaAppDao {

}

