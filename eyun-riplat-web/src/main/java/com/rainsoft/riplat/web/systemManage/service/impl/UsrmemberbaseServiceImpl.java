package com.rainsoft.riplat.web.systemManage.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.systemManage.dao.UsrmemberbaseDao;
import com.rainsoft.riplat.web.systemManage.model.Usrmemberbase;
import com.rainsoft.riplat.web.systemManage.service.UsrmemberbaseService;

@Service("memberbaseService")
public class UsrmemberbaseServiceImpl extends MybatisBasePersitenceServiceImpl<Usrmemberbase, String> implements UsrmemberbaseService {
	@Resource
	private UsrmemberbaseDao usrmemberDao;
	
	@Override
	protected IMybatisPersitenceDao<Usrmemberbase, String> getBaseDao() {
		return usrmemberDao;
	}

}