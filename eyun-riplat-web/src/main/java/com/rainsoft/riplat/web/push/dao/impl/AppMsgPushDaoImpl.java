package com.rainsoft.riplat.web.push.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.push.dao.IAppMsgPushDao;
import com.rainsoft.riplat.web.push.model.AppToken;
@Repository("appMsgPushDao")
public class AppMsgPushDaoImpl extends MybatisPersitenceDaoImpl<AppToken, String> implements IAppMsgPushDao {

}
