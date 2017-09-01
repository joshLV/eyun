package com.rainsoft.union.web.pubdata.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.bean.BeanMapUtil;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.pubdata.dao.PubDataDao;
import com.rainsoft.union.web.pubdata.model.DeviceData;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sun on 2016/5/25.
 * 致终于来到这里的勇敢的人：
 * 你是被上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改
 * 我们这最棘手的代码的编程骑士。你，我们的救世主，人中之龙，
 * 我要对你说：永远不要放弃，永远不要对自己失望，永远不要逃走，辜负了自己。
 * 永远不要哭啼，永远不要说再见。永远不要说谎来伤害自己。
 */
@Service
public class PubDataServiceImpl implements PubDataService {
    @Resource
    private PubDataDao pubDataDao;

    /**
     * jqgrid分页场所列表
     *
     * @param placeData 场所对象
     * @param page      分页对象
     * @return
     * @throws Exception
     */
    // only for jqGrid
    @Override
    public JSONObject getPlaceListJSON(PlaceData placeData, PageBean page) throws Exception {
        List<PlaceData> list = null;
        //使用map作为参数传入dao层
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap = BeanMapUtil.toMap(placeData);
        paramMap.put("SORT", page.getSort());
        paramMap.put("DIR", page.getOrder());
        // mybatis 分页插件
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = pubDataDao.getPlaceList(paramMap);
        page.setTotal(((Page<PlaceData>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());          // 当前页
        jsonObject.put("totalPages", page.getPageCount());      // 总页数
        jsonObject.put("totalRecords", page.getTotal());        // 总记录数
        // 页面显示记录数设置
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
    }

    /**
     * 根据会员Id，或者父会员Id，获取场所选项
     * @param userId
     * @return List<PlaceData>
     */
    @Override
    @Cacheable(value = "placesOpt",key = "#userId")
    public List<PlaceData> getPlaceOptByUserId(Integer userId) throws Exception {
        List<PlaceData> dataList = null;
        if(userId != null && userId > 0 ){
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("userId" , userId);
            dataList = pubDataDao.getPlaceOptByUserId(paramMap);
        }
        return dataList;
    }

    /**
     * 场所列表
     *
     * @param placeData 场所对象
     * @param page      分页对象
     * @return
     * @throws Exception
     */
    @Override
    public List<PlaceData> getPlaceList(PlaceData placeData, PageBean page) throws Exception {
        List<PlaceData> list = null;
        //使用map作为参数传入dao层
        Map<String, Object> paramMap = BeanMapUtil.toMap(placeData);
        // 排序字段
        paramMap.put("SORT", page.getSort());
        // 排序顺序
        paramMap.put("DIR", page.getOrder());
        // mybatis 分页插件
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = pubDataDao.getPlaceList(paramMap);
        page.setTotal(((Page<PlaceData>) list).getTotal());
        return list;
    }

    /**
     *
     * @param placeData 场所对象
     * @return
     * @throws Exception
     */
    @Override
    public List<PlaceData> getPlaceList(PlaceData placeData) throws Exception {
        Map<String, Object> paramMap = BeanMapUtil.toMap(placeData);
        List<PlaceData> list = pubDataDao.getPlaceList(paramMap);
        return list;
    }

    /**
     * jqgrid列表需要的设备列表json
     * @param deviceData 设备对象
     * @param page       分页对象
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getDeviceListJSON(DeviceData deviceData, PageBean page) throws Exception {
        List<DeviceData> list = null;
        //使用map作为参数传入dao层
        Map<String, Object> paramMap = BeanMapUtil.toMap(deviceData);
        paramMap.put("SORT", page.getSort());
        paramMap.put("DIR", page.getOrder());
        // mybatis 分页插件
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = pubDataDao.getDeviceList(paramMap);
        page.setTotal(((Page<DeviceData>) list).getTotal());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("curPage", page.getCurrPage());          // 当前页
        jsonObject.put("totalPages", page.getPageCount());      // 总页数
        jsonObject.put("totalRecords", page.getTotal());        // 总记录数
        // 页面显示记录数设置
        jsonObject.put("rowNum", page.getPageSize());
        jsonObject.put("dataRows", list);
        return jsonObject;
    }

    /**
     * 查询设备列表
     * @param deviceData 设备实体
     * @param page 分页实体
     * @return
     * @throws Exception
     */
    @Override
    public List<DeviceData> getDeviceList(DeviceData deviceData, PageBean page) throws Exception {
        List<DeviceData> list = null;
        //使用map作为参数传入dao层
        Map<String, Object> paramMap = BeanMapUtil.toMap(deviceData);
        // 排序字段
        paramMap.put("SORT", page.getSort());
        // 排序顺序
        paramMap.put("DIR", page.getOrder());
        // mybatis 分页插件
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        list = pubDataDao.getDeviceList(paramMap);
        page.setTotal(((Page<DeviceData>) list).getTotal());
        return list;
    }

    /**
     * @param deviceData 设备对象
     * @return
     * @throws Exception
     */
    @Override
    public List<DeviceData> getDeviceList(DeviceData deviceData) throws Exception {
        Map<String, Object> paramMap = BeanMapUtil.toMap(deviceData);
        List<DeviceData> list = pubDataDao.getDeviceList(paramMap);
        return list;
    }

    /**
     * 查询计费设备
     *
     * @param placeCode 场所编号
     * @return
     * @throws Exception
     */
    @Override
    public List<DeviceData> getBillDeviceList(String placeCode) throws Exception {
        List<DeviceData> deviceDataList = null;
        if (StringUtil.isNotEmpty(placeCode)) {
            deviceDataList = pubDataDao.getBillDeviceList(placeCode);
        }
        return deviceDataList;
    }

    /**
     * 获取businessFlag
     * @param placeId 场所id
     * @return
     */
    @Override
    public String getBusinessFlagByPlaceId(int placeId) throws Exception {
        String businessFlag = "";
        if (placeId > 0) {
            businessFlag = pubDataDao.getBusinessFlagByPlaceId(placeId).trim();
            if (StringUtil.isEmpty(businessFlag)) {
                businessFlag = pubDataDao.getBusinessFlagFromSysParameter().trim();
            }
        }
        return businessFlag;
    }

    @Override
    public String getPlaceTypeByMemberId(Integer userId) throws Exception {
        return pubDataDao.getPlaceTypeByMemberId(userId);
    }

}
