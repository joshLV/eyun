package com.rainsoft.union.web.pubdata.dao;

import com.rainsoft.union.web.pubdata.model.DeviceData;
import com.rainsoft.union.web.pubdata.model.PlaceData;

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
public interface PubDataDao {
    /**
     * 场所列表
     *
     * @param map 传入sql的参数
     * @return List场所列表
     */
    List<PlaceData> getPlaceList(Map<String, Object> map);

    /**
     * 根据会员Id，或者父会员Id，获取场所选项
     * @param map{userId}
     * @return List<PlaceData>
     */
    List<PlaceData> getPlaceOptByUserId(Map<String , Object> map);

    /**
     * 设备列表
     *
     * @param map 传入sql的参数
     * @return List设备列表
     */
    List<DeviceData> getDeviceList(Map<String, Object> map);

    /**
     * .
     * 计费设备列表
     *
     * @param placeCode 场所编号
     * @return 计费设备
     */
    List<DeviceData> getBillDeviceList(String placeCode);

    /**
     * 根据场所id 查询商业控制标志
     *
     * @param placeId 场所id
     * @return 例如："YYYYYYYYYYYYYYY"
     */
    String getBusinessFlagByPlaceId(int placeId);

    /**
     * SysParameter表查询默认的商业控制标志
     *
     * @return 例如："YYYYYYYYYYYYYYY"
     */
    String getBusinessFlagFromSysParameter();

    /**
     * 根据当前登录用户获取他对应的的场所类型 如果是子账号 则获取父账号对应的场所类型
     * @param userId 当前登录会员的id
     * @return
     */
    String getPlaceTypeByMemberId(Integer userId);
}
