package com.rainsoft.riplat.web.systemManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.UsrmemberbaseDao;
import com.rainsoft.riplat.web.systemManage.model.Usrmemberbase;

@Repository("usrmemberDao")
public class UsrmemberbaseDaoImpl extends MybatisPersitenceDaoImpl<Usrmemberbase,String> implements UsrmemberbaseDao  {
 
 
}