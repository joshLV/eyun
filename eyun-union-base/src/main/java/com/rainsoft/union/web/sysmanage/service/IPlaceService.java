package com.rainsoft.union.web.sysmanage.service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.sysmanage.model.ParamFilter;

/**
 * Created by qianna on 2016/11/28.
 */
public interface IPlaceService{
    /**
     * 获取场所列表信息
     * @param param 参数
     * @param pageBean 表格参数
     * @return List<PlaceData>
     * @throws Exception
     */
    public JSONObject getPlaceList(ParamFilter param,PageBean pageBean) throws Exception;

    /**
     * 更新场所短信计费方式
     * @param param
     * @return int
     * @throws Exception
     */
    public int updateSmsRechargeFlag(ParamFilter param) throws Exception;
}
