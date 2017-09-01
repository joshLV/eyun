package com.rainsoft.union.web.businessmanage.service;

import com.alibaba.fastjson.JSONArray;
import com.rainsoft.union.web.businessmanage.model.CashierInfo;
import com.rainsoft.union.web.businessmanage.model.CurrentYield;
import com.rainsoft.union.web.businessmanage.model.DeviceStatus;
import com.rainsoft.union.web.businessmanage.model.OnlineHeadCountDetail;
import com.rainsoft.union.web.businessmanage.model.ShiftDetail;
import com.rainsoft.union.web.businessmanage.model.ShiftRecord;

import java.util.List;


public interface IBusinessManagementService {
    /**
     * 查询当前收益
     *
     * @param params 传入参数
     * @return 返回查询数据
     */
    List<CurrentYield> getCurrentYield(String params);

    /**
     * 查询当前收银员
     *
     * @param params 传入参数
     * @return 返回查询数据
     */
    List<CashierInfo> getCashierInfo(String params);

    /**
     * 查询当前在线人数
     *
     * @param params 传入参数
     * @return 返回查询数据
     */
    List<OnlineHeadCountDetail> getOnlineCount(String params);

    /**
     * 计费中心状态
     *
     * @param params 传入参数
     * @return 返回查询数据
     */
    List<DeviceStatus> getCharingDeviceStatus(String params);

    /**
     * 交接班记录
     *
     * @param params 传入参数
     * @return 返回查询数据
     */
    JSONArray getShiftRecord(String params);

    /**
     * 交接班记录详细页面
     *
     * @param shiftRecord 传入参数
     * @return 返回查询数据
     */
    List<ShiftDetail> getShiftDetail(ShiftRecord shiftRecord);

    /**
     * 交接班记录详细页面
     *
     * @param shiftRecord 传入参数
     * @return 返回查询数据
     */
    List<ShiftDetail> toShiftDetail(ShiftRecord shiftRecord);
}
