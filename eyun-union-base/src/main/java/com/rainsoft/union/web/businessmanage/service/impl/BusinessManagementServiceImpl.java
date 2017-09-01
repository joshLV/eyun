package com.rainsoft.union.web.businessmanage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.*;
import com.rainsoft.union.web.businessmanage.model.*;
import com.rainsoft.union.web.businessmanage.service.IBusinessManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 经营管理service实现类
 */
@Service("businessManagementService")
public class BusinessManagementServiceImpl implements IBusinessManagementService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessManagementServiceImpl.class);

    private static final String NET_FEE_URL = "/SpfeeService/";

    @Override
    public List<CurrentYield> getCurrentYield(String placeCode) {//当前收益
        String rsString = "";
        if (StringUtil.isNotEmpty(placeCode)) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getCurrentYield.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + placeCode + "\"}";
            try {
                logger.info("获取当前收益的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取当前收益的接口失败了");
            }
        }
        List<CurrentYield> currentYieldList = new ArrayList<>();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    String data = jsonObj.getString("data");
                    currentYieldList = JSONArray.parseArray(data, CurrentYield.class);
                }
            }
        }
        return currentYieldList;
    }

    @Override
    public List<CashierInfo> getCashierInfo(String placeCode) {
        String rsString = "";
        if (StringUtil.isNotEmpty(placeCode)) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getCashierInfo.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + placeCode + "\"}";
            try {
                logger.info("调用获取当前收银员的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取当前收银员的接口失败了");
            }
        }
        List<CashierInfo> cashierInfoList = new ArrayList<>();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    String data = jsonObj.getString("data");
                    cashierInfoList = JSONArray.parseArray(data, CashierInfo.class);
                }
            }
        }
        return cashierInfoList;
    }

    @Override
    public List<OnlineHeadCountDetail> getOnlineCount(String placeCode) {
        String rsString = "";
        if (StringUtil.isNotEmpty(placeCode)) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getOnlineHeadCountDetail.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + placeCode + "\"}";
            try {
                logger.info("调用获取当前在线人数的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取当前在线人数的接口失败了");
            }
        }
        List<OnlineHeadCountDetail> onlineHeadCountDetailList = new ArrayList<>();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    String data = jsonObj.getString("data");
                    onlineHeadCountDetailList = JSONArray.parseArray(data, OnlineHeadCountDetail.class);
                }
            }
        }
        return onlineHeadCountDetailList;
    }

    @Override
    public List<DeviceStatus> getCharingDeviceStatus(String placeCode) {
        String rsString = "";
        if (StringUtil.isNotEmpty(placeCode)) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getCharingDeviceStatus.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + placeCode + "\"}";
            try {
                logger.info("调用获取计费中心状态的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取计费中心状态的接口失败了");
            }
        }
        List<DeviceStatus> deviceStatusList = new ArrayList<>();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    String data = jsonObj.getString("data");
                    //JSON.
                    deviceStatusList = JSONArray.parseArray(data, DeviceStatus.class);
                }
            }
        }
        return deviceStatusList;
    }

    /**
     * 交接班记录
     *
     * @param placeCode 传入参数
     * @return 查询的结果
     */
    @Override
    public JSONArray getShiftRecord(String placeCode) {
        String rsString = "";
        if (StringUtil.isNotEmpty(placeCode)) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getShiftRecord.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + placeCode + "\"}";
            try {
                logger.info("调用获取交接班记录的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取交接班记录的接口失败了");
            }
        }
        JSONArray jsonArray = new JSONArray();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    jsonArray = jsonObj.getJSONArray("data");
                    List<ShiftRecord> list = JSONArray.parseArray(jsonArray.toJSONString(), ShiftRecord.class);
                    jsonArray = (JSONArray) JSONArray.toJSON(list);
                }
            }
        }
        return jsonArray;
    }

    @Override
    public List<ShiftDetail> getShiftDetail(ShiftRecord shiftRecord) {
        String rsString = "";
        if (StringUtil.isNotEmpty(shiftRecord.getServiceCode())) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getShiftDetail.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + shiftRecord.getServiceCode() + "\",\"recordId\":\"" + shiftRecord.getId() + "\"}";
            try {
                logger.info("调用获取交接班记录详细信息的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取交接班记录详细信息的接口失败了");
            }
        }
        List<ShiftDetail> shiftDetailList = new ArrayList<>();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    String data = jsonObj.getString("data");
                    shiftDetailList = JSONArray.parseArray(data, ShiftDetail.class);
                }
            }
        }
        return shiftDetailList;
    }

    @Override
    public List<ShiftDetail> toShiftDetail(ShiftRecord shiftRecord) {
        String rsString = "";
        if (StringUtil.isNotEmpty(shiftRecord.getServiceCode())) {
            String url = SystemConfigUtil.getValue("EYUN_UNION_INTERFACE") + NET_FEE_URL + "getShiftDetail.do";
            String jsonString = "{\"userId\":\"111111\",\"serviceCode\":\"" + shiftRecord.getServiceCode() + "\",\"recordId\":\"" + shiftRecord.getId() + "\"}";
            try {
                logger.info("调用获取交接班记录详细信息的接口");
                rsString = getDataByHttp(jsonString, url);
            } catch (Exception e) {
                logger.error("调用获取交接班记录详细信息的接口失败了");
            }
        }
        List<ShiftDetail> shiftDetailList = new ArrayList<>();
        if (StringUtil.isNotEmpty(rsString)) {
            JSONObject jsonObj = JSONObject.parseObject(rsString);
            if (jsonObj != null) {
                String status = jsonObj.getString("status");
                if (Constants.RETURN_SUCCESS_INTERFACE.equals(status)) {
                    String data = jsonObj.getString("data");
                    shiftDetailList = JSONArray.parseArray(data, ShiftDetail.class);
                }
            }
        }
        return shiftDetailList;
    }

    /**
     * http请求获取数据
     *
     * @param params 加密的参数
     * @param url    请求的地址
     * @return 返回解密后的数据
     */
    public String getDataByHttp(String params, String url) throws Exception {
        String getString = "";
        if (StringUtil.isNotEmpty(params)) {
            logger.info("调用易达网吧接口传入参数： " + params);
            //加密参数
            params = DesUtil.encrypt(params);
            //请求接口返回的数据
            logger.info("调用易达网吧接口加密后参数： " + params);
            getString = HttpUtil.portConnect(url, params);
            //解密返回的数据
            getString = DesUtil.decrypt(getString);
            logger.info("调用易达网吧接口解密后的返回值： " + getString);
        }
        return getString;
    }
}
