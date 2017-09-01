package com.rainsoft.union.web.sysmanage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.UploadImgUtil;
import com.rainsoft.union.web.sysmanage.dao.IPortalDao;
import com.rainsoft.union.web.sysmanage.model.Material;
import com.rainsoft.union.web.sysmanage.model.Portal;
import com.rainsoft.union.web.sysmanage.service.IPortalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 认证模版
 *
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Service("portalService")
public class PortalServiceImpl extends MybatisBasePersitenceServiceImpl<Portal, String> implements IPortalService {
    @Resource
    private IPortalDao portalDao;

    @Override
    protected IMybatisPersitenceDao<Portal, String> getBaseDao() {
        return portalDao;
    }

    /**
     * @param portal 模版实体
     * @return 返回值
     * @throws Exception
     */
    @Override
    public Integer savePortal(Portal portal) throws Exception {
        Integer rs = -1;
        rs = getBaseDao().findCountBy(portal);
        if (rs != null && rs >= 0) {
            rs = -2;//模版名称重复
        }else {
            rs = getBaseDao().save(portal);
        }
        return rs;
    }

    @Override
    public Integer updatePortal(Portal portal) throws Exception {
        Integer rs = -1;
        rs = getBaseDao().findCountBy(portal);
        if (rs != null && rs >= 0) {
            rs = -2;//模版名称重复
        }else {
            portal.setStatus("保存未提交");
            rs = getBaseDao().update("updatePortal", portal);
            //rs = getBaseDao().update(portal);
        }
        return rs;
    }

    /**
     * 设置默认的模版
     * @param id  模版ID
     * @param placeId 场所ID
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateDefaultModel(Integer id,Integer placeId) throws Exception {
        Portal portal = new Portal();
        portal.setPlaceId(placeId);
        portal.setIsDefault("N");
        // 设置本场所的所有模版为N
        getBaseDao().update(portal);
        portal.setId(id);
        portal.setPlaceId(null);
        portal.setIsDefault("Y");
        // 设置当前模版为默认模板
        return getBaseDao().update(portal);
    }

    @Override
    public Integer deleteMaterial(String ids, String updateTimes) throws Exception {
        /**String 转成数组用于循环处理数据**/
        String[] idArr = ids.split(",");
        String[] updateTimeArr = updateTimes.split(",");
        /**数组转成list，存放的是id**/
        List<String> list = Arrays.asList(idArr);
        Map<String, Object> map = new HashMap<>();
        /**根据传入id 查询数据更新时间放入map**/
        List<Portal> updateTimeList = portalDao.selectList("getUpdateTimeByIds", list);
        if (updateTimeList != null && updateTimeList.size() > 0) {
            for (Portal m : updateTimeList) {
                map.put(m.getId() + "", m.getUpdateTimeStr());
            }
        }
        for (int i = 0; i < idArr.length; i++) {
            /**如果数据已被更改 则取消操作**/
            if (!updateTimeArr[i].equals(map.get(idArr[i]))) {
                return -2;//数据已被更改
            }
        }
        /**开始删除数据**/
        int rs = -1;
        if (idArr.length == 1) {
            rs = getBaseDao().deleteById(idArr[0]);
        } else {
            rs = getBaseDao().deleteByIds(idArr);
        }
        return rs;
    }
}
