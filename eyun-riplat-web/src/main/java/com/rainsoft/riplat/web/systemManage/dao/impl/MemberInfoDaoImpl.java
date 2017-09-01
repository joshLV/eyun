package com.rainsoft.riplat.web.systemManage.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.IMemberInfoDao;
import com.rainsoft.riplat.web.systemManage.model.MemberInfo;

/**
 * 个人信息
 *
 **/
@Repository("memberInfoDao")
public class MemberInfoDaoImpl extends MybatisPersitenceDaoImpl<MemberInfo, String> implements IMemberInfoDao {

}
