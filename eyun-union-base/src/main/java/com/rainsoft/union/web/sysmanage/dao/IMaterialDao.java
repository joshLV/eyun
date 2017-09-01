package com.rainsoft.union.web.sysmanage.dao;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.union.web.sysmanage.model.Material;

/**
 * 认证素材dao
 *
 * @author 13646223842@163.com
 * @since 1.0.0 2015/11/30.
 */
public interface IMaterialDao extends IMybatisPersitenceDao<Material, String> {
    /**
     * 根据素材ID获取对应的状态
     *
     * @param material 素材
     * @return 状态 0：保存未提交；1：待审核，7：审核不通过，8：审核通过，a：作废，d：删除。缺省值0
     */
    String getMaterialStatus(Material material);
}
