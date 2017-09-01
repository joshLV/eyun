package com.rainsoft.union.web.pubdata.service;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.pubdata.model.DeviceData;
import com.rainsoft.union.web.pubdata.model.PlaceData;

import java.util.List;

/**
 * 场所 设备 相关的查询
 * Created by Sun on 2016/5/25.
 * 致终于来到这里的勇敢的人：
 * 你是被上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改
 * 我们这最棘手的代码的编程骑士。你，我们的救世主，人中之龙，
 * 我要对你说：永远不要放弃，永远不要对自己失望，永远不要逃走，辜负了自己。
 * 永远不要哭啼，永远不要说再见。永远不要说谎来伤害自己。
 */
public interface PubDataService {
    /**
     * 场所列表的json
     * jqGrid表格请求需要返回的json（仅供jqGrid使用）
     *
     * @param placeData 场所对象
     * @param page      分页对象
     * @return json
     * @throws Exception
     */
    JSONObject getPlaceListJSON(PlaceData placeData, PageBean page) throws Exception;

    /**
     * 根据会员Id，或者父会员Id，获取场所选项
     * @param userId
     * @return List<PlaceData>
     */
    List<PlaceData> getPlaceOptByUserId(Integer userId) throws Exception;
    /**
     * 场所列表
     *
     * @param placeData 场所对象
     * @param page      分页对象
     * @return list
     * @throws Exception
     */
    List<PlaceData> getPlaceList(PlaceData placeData, PageBean page) throws Exception;

    /**
     * 场所列表 无分页
     * @param placeData 场所对象
     * @return
     * @throws Exception
     */
    List<PlaceData> getPlaceList(PlaceData placeData) throws Exception;

    /**
     * 场所列表的json
     * jqGrid表格请求需要返回的json（仅供jqGrid使用）
     *
     * @param deviceData 设备对象
     * @param page       分页对象
     * @return json
     * @throws Exception
     */
    JSONObject getDeviceListJSON(DeviceData deviceData, PageBean page) throws Exception;

    /**
     * 设备列表
     *
     * @param deviceData 设备对象
     * @param page       分页对象
     * @return
     * @throws Exception
     */
    List<DeviceData> getDeviceList(DeviceData deviceData, PageBean page) throws Exception;

    /**
     * 设备列表
     *
     * @param deviceData 设备对象
     * @return
     * @throws Exception
     */
    List<DeviceData> getDeviceList(DeviceData deviceData) throws Exception;
    /**
     * .
     * 计费设备列表
     *
     * @param placeCode 场所编号
     * @return 计费设备
     */
    List<DeviceData> getBillDeviceList(String placeCode) throws Exception;

    /**
     * 根据场所id 查询商业控制标志
     *
     * @param placeId 场所id
     * @return 例如："YYYYYYYYYYYYYYY"
     */
    String getBusinessFlagByPlaceId(int placeId) throws Exception;

    /**
     * 根据当前登录用户获取他对应的的场所类型 如果是子账号 则获取父账号对应的场所类型
     * @param userId 当前登录会员的id
     * @return
     */
    String getPlaceTypeByMemberId(Integer userId)throws Exception;


}
