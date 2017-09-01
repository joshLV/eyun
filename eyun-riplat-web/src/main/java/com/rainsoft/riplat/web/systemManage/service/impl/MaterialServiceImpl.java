package com.rainsoft.riplat.web.systemManage.service.impl;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.riplat.web.systemManage.dao.IMaterialDao;
import com.rainsoft.riplat.web.systemManage.model.Material;
import com.rainsoft.riplat.web.systemManage.service.IMaterialService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证素材
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Service("materialService")
public class MaterialServiceImpl extends MybatisBasePersitenceServiceImpl<Material, String> implements IMaterialService {
    @Resource
    private IMaterialDao materialDao;

    @Override
    protected IMybatisPersitenceDao<Material, String> getBaseDao() {
        return materialDao;
    }

    @Override
    public Integer call(String keyId, Material material) {
        materialDao.findBy(keyId, material);
        return  material.getId();
    }

    @Override
    public Map<String, Object> getUpdatetimeByIds(List<String> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Material> updateTimeList = materialDao.selectList("getUpdateTimeByIds", list);
        if (updateTimeList != null && updateTimeList.size() > 0) {
            for (Material m : updateTimeList){
                map.put(m.getId()+"",m.getUpdateTimeStr());
            }
        }
        return map;
    }
}
