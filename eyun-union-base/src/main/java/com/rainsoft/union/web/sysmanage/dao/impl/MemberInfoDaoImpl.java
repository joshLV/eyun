package com.rainsoft.union.web.sysmanage.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.sysmanage.dao.IMemberInfoDao;
import com.rainsoft.union.web.sysmanage.model.MemberInfo;

/**
 * 
 * 个人信息
 *
 */
@Repository("memberInfoDao")
public class MemberInfoDaoImpl extends MybatisPersitenceDaoImpl<MemberInfo, String> implements IMemberInfoDao {

}
