package com.rainsoft.riplat.web.systemManage.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.systemManage.dao.IMaterialDao;
import com.rainsoft.riplat.web.systemManage.model.Material;

import org.springframework.stereotype.Repository;

/**
 * 认证素材
 * 
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Repository("materialDao")
public class MaterialDaoImpl extends MybatisPersitenceDaoImpl<Material, String> implements IMaterialDao {
}
