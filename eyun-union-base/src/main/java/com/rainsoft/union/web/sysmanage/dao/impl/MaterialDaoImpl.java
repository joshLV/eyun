package com.rainsoft.union.web.sysmanage.dao.impl;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.union.web.sysmanage.dao.IMaterialDao;
import com.rainsoft.union.web.sysmanage.model.Material;
import org.springframework.stereotype.Repository;

/**
 * 认证素材
 *
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
@Repository("materialDao")
public class MaterialDaoImpl extends MybatisPersitenceDaoImpl<Material, String> implements IMaterialDao {

    @Override
    public String getMaterialStatus(Material material) {
        material = this.getSqlSession().selectOne(getSqlName("getMaterial"), material);
        return material.getStatus();
    }
}
