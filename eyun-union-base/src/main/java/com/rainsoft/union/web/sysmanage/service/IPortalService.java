package com.rainsoft.union.web.sysmanage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.union.web.sysmanage.model.Portal;

/**
 * 认证模板
 *
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
public interface IPortalService extends IMybatisBasePersitenceService<Portal, String> {
    /**
     * 新增认证模版
     *
     * @param portal 模版实体
     * @return 返回值
     * @throws Exception
     */
    Integer savePortal(Portal portal) throws Exception;

    /**
     * 新增认证模版
     *
     * @param portal 模版实体
     * @return 返回值
     * @throws Exception
     */
    Integer updatePortal(Portal portal) throws Exception;

    /**
     * /**
     * 设置默认模版 只有状态8 和 状态9的可以设置
     *
     * @param id      模版ID
     * @param placeId 场所ID
     * @return
     * @throws Exception
     */
    Integer updateDefaultModel(Integer id, Integer placeId) throws Exception;

    /**
     * 删除素材
     *
     * @param ids          传入的id 一个或者多个
     * @param updateTimes  修改时间 同上
     * @return 返回值
     * @throws Exception
     */
    Integer deleteMaterial(String ids, String updateTimes) throws Exception;

}
