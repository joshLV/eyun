package com.rainsoft.riplat.web.systemManage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.riplat.web.systemManage.model.Material;

import java.util.List;
import java.util.Map;

/**
 * 认证素材
 *
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
public interface IMaterialService extends IMybatisBasePersitenceService<Material, String> {
    /**
     * 传入对应keyId 调用对应存储过程
     * keyId=save代表保存素材，update代表修改素材，updateStatus代表修改素材状态
     * @param keyId 存储过程id
     * @param material 认证素材实体
     * @return 返回值
     */
    Integer call(String keyId,Material material);

    Map<String, Object> getUpdatetimeByIds(List<String> list);
}
