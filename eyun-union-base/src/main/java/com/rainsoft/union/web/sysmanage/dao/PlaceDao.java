package com.rainsoft.union.web.sysmanage.dao;

import com.rainsoft.union.web.pubdata.model.PlaceData;

import java.util.List;
import java.util.Map;

/**
 * 场所操作相关
 * Created by qianna on 2016/11/28.
 */
public interface PlaceDao {
    /**
     * 获取场所列表信息
     * @param map
     * @return
     * @throws Exception
     */
    public List<PlaceData> getPlaceList(Map<String,Object> map) throws Exception;

    /**
     * 更新场所
     * @param map
     * @throws Exception
     */
    public Integer updatePlaceById(Map<String,Object> map) throws Exception;
}
