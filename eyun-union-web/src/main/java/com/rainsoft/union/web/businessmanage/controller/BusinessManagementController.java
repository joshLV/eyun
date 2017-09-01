package com.rainsoft.union.web.businessmanage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.union.web.businessmanage.model.CashierInfo;
import com.rainsoft.union.web.businessmanage.model.CurrentYield;
import com.rainsoft.union.web.businessmanage.model.DeviceStatus;
import com.rainsoft.union.web.businessmanage.model.OnlineHeadCountDetail;
import com.rainsoft.union.web.businessmanage.model.ShiftDetail;
import com.rainsoft.union.web.businessmanage.model.ShiftRecord;
import com.rainsoft.union.web.businessmanage.service.IBusinessManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/management/operationState")
public class BusinessManagementController {

    @Resource
    private IBusinessManagementService businessManagementService;


    /**
     * 查询场所的当前收益
     *
     * @param placeCode 场所ID
     * @param model   返回页面的对象
     * @return 跳转的页面
     */
    @RequestMapping(value = "/current_yield", method = RequestMethod.POST)
    public String getCurrentYield(@RequestParam("placeCode") String placeCode, Model model) {
        //如果没有选择场所，直接返回
        if (!"-1".equals(placeCode)) {
            //请求接口返回查询结果
            List<CurrentYield> currentYieldList = businessManagementService.getCurrentYield(placeCode);
            if (currentYieldList.size()>0) {
                model.addAttribute("currentYield", currentYieldList.get(0));
            }
        }
        return "businessmanage/currentYield";
    }

    /**
     * 交接班记录
     *
     * @param placeCode 场所Id
     * @param model   返回页面的实体
     * @return 跳转的地址
     */
    @RequestMapping(value = "/toShiftRecord", method = RequestMethod.POST)
    public String toShiftRecord(@RequestParam("placeCode") String placeCode, Model model) {
        //String rs = "";
        JSONArray jsonArray = new JSONArray();
        if (!"-1".equals(placeCode)) {
            //请求接口返回查询结果 jqgrid显示
            jsonArray = businessManagementService.getShiftRecord(placeCode);
        }
        model.addAttribute("data", jsonArray);
        return "businessmanage/shiftRecordList";
    }

    /**
     * 交接班记录详情页
     * @param shiftRecord 交接班记录实体
     * @param response response
     */
    @RequestMapping(value = "/getShiftDetail", method = RequestMethod.POST)
    public void getShiftDetail(@ModelAttribute ShiftRecord shiftRecord,HttpServletResponse response) {
        List<ShiftDetail> shiftDetailList = businessManagementService.getShiftDetail(shiftRecord);
        String rs = "";
        if (shiftDetailList.size() > 0) {
            rs = JSON.toJSONString(shiftDetailList.get(0), SerializerFeature.WriteMapNullValue);
        }
        SpringMvcUtil.responseJSONWriter(response, rs);
    }
    /**
     * 交接班记录详情页
     * @param shiftRecord 交接班记录实体
     * @param model 模板
     */
    @RequestMapping(value = "/toShiftDetail", method = RequestMethod.POST)
    public String toShiftDetail(@ModelAttribute ShiftRecord shiftRecord,Model model) {
        List<ShiftDetail> shiftDetailList = businessManagementService.getShiftDetail(shiftRecord);
        if (shiftDetailList.size() > 0) {
            model.addAttribute("shiftDetail", shiftDetailList.get(0));
        }
        return "businessmanage/shiftDetailInfo";
    }

    /**
     * 当前收银员
     *
     * @param placeCode 场所Id
     * @param model   收银员实体
     * @return 跳转的页面
     */
    @RequestMapping(value = "/getCashierInfo", method = RequestMethod.POST)
    public String getCashierInfo(@RequestParam("placeCode") String placeCode, Model model) {
        JSONArray jsonArray = new JSONArray();
        if (!"-1".equals(placeCode)) {
            //请求接口返回查询结果
            List<CashierInfo> currentYieldList = businessManagementService.getCashierInfo(placeCode);
            //model.addAttribute("currentYieldList", currentYieldList);
            String s = JSON.toJSONString(currentYieldList, SerializerFeature.WriteMapNullValue);
            jsonArray = JSONArray.parseArray(s);
        }
        model.addAttribute("data", jsonArray);
        return "businessmanage/cashierInfo";
    }

    /**
     * 当前在线人数
     * @param placeCode 场所Id
     * @param model   实体
     * @return 跳转的页面
     */
    @RequestMapping(value = "/getOnlineCount", method = RequestMethod.POST)
    public String getOnlineCount(@RequestParam("placeCode") String placeCode, Model model) {
        JSONArray jsonArray = new JSONArray();
       // model.addAttribute("data", jsonArray);
        if (!"-1".equals(placeCode)) {
            //请求接口返回查询结果
            List<OnlineHeadCountDetail> onlineHeadCountDetailList = businessManagementService.getOnlineCount(placeCode);
            String s =JSON.toJSONString(onlineHeadCountDetailList,  SerializerFeature.WriteMapNullValue);
            jsonArray = JSONArray.parseArray(s);
            //model.addAttribute("onlineHeadCountDetailList", onlineHeadCountDetailList);
        }
        model.addAttribute("data", jsonArray);
        return "businessmanage/onlineCount";
    }

    /**
     * 计费中心状态
     * @param placeCode 场所Id
     * @param model 实体
     * @return 跳转的页面
     */
    @RequestMapping(value = "/getCharingStatus", method = RequestMethod.POST)
    public String getCharingDeviceStatus(@RequestParam("placeCode") String placeCode, Model model) {
        if (!"-1".equals(placeCode)) {
            //请求接口返回查询结果
            List<DeviceStatus> deviceStatusList = businessManagementService.getCharingDeviceStatus(placeCode);
            if (deviceStatusList.size()>0) {
                model.addAttribute("deviceStatus", deviceStatusList.get(0));
            }
        }
        return "businessmanage/deviceStatus";
    }

    /**
     * 意见反馈
     *
     * @return 页面
     */
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String feedback() {

        return "businessmanage/feedback";
    }

}
