package com.rainsoft.union.web.sysmanage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.sysmanage.dao.PlaceDao;
import com.rainsoft.union.web.sysmanage.model.ParamFilter;
import com.rainsoft.union.web.sysmanage.service.IPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * PlaceServiceImpl
 * Created by qianna on 2016/11/28
 */
@Service("placeServiceImpl")
public class PlaceServiceImpl implements IPlaceService {
    private static Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);

    @Resource
    private PlaceDao placeDao;

    @Override
    public JSONObject getPlaceList(ParamFilter param , PageBean pageBean) throws Exception {
        List<PlaceData> list = null;
        Map<String,Object> paramMap = param.toMap();

        paramMap.put("SORT",pageBean.getSort()); //排序字段
        paramMap.put("DIR",pageBean.getOrder());//排序顺序

        //mybatis 分页插件
        PageHelper.startPage(pageBean.getCurrPage(), pageBean.getPageSize());

        list = placeDao.getPlaceList(paramMap);
        pageBean.setTotal(((Page<PlaceData>)list).getTotal() );

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("curPage", pageBean.getCurrPage()); // 当前页
        jsonObj.put("totalPages", pageBean.getPageCount()); // 总页数
        jsonObj.put("totalRecords", pageBean.getTotal()); // 总记录数
        // 页面显示记录数设置
        jsonObj.put("rowNum", pageBean.getPageSize());
        jsonObj.put("dataRows", JSONArray.toJSON(list));
        return jsonObj;
    }

    @Override
    public int updateSmsRechargeFlag(ParamFilter param) throws Exception {
        int result = -1;
        Integer placeDeviceId = param.getPlaceDeviceId();
        if(placeDeviceId != null && placeDeviceId >0){
            Map<String,Object> paramMap = param.toMap();
            result = placeDao.updatePlaceById(paramMap);
        }else{
            logger.info("参数不合法：placeDeviceId,smsFeeOwnerFlag 必须，"+ JSONObject.toJSONString(param));
        }
        return result;
    }
}
