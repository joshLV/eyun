/**
 * @Description:短信管理controller类
 * @author:王乾
 * @date:2016年4月18日下午12:57:17
 */
package com.rainsoft.union.web.smsmanage.controller;

import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import com.rainsoft.union.web.smsmanage.service.SmsAssignaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 短信分配
 *
 * @author 13646223842@163.com
 * @since 1.0.0 2016年6月23日 14:28:37
 */
@Controller
@RequestMapping("/smsManage/assigna")
public class SmsAssignaController {
    private static final Logger logger = LoggerFactory.getLogger(SmsAssignaController.class);


    @Resource
    private SmsAssignaService smsAssignaServiceImpl;
    @Resource
    private PubDataService pubDataServiceImpl;

    /**
     * 通过登录的会员ID获取该会员的场所资料 参数 placeDataModel
     */
    @RequestMapping("/smsTransferPlace")
    protected String smsTransferPlace(Model model) {
        Integer userId = SpringMvcUtil.getUserId();
        PlaceData placeData = new PlaceData();
        placeData.setUserId(userId);
        List<PlaceData> placeDataList = new ArrayList<>();
        int balance = 0;
        try {
            placeDataList = pubDataServiceImpl.getPlaceList(placeData);
            balance = smsAssignaServiceImpl.getMemberSMSbalance(userId,placeDataList);
        } catch (Exception e) {
            logger.error("获取会员自己的场所",e);
        }
        model.addAttribute("placeList", placeDataList);
        model.addAttribute("balance", balance);
        return "smsmanage/smsTransferPlace";
    }

    /**
     * 短信场所分配设置
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/smsTransferSetup")
    public void smsTransferSetup(@RequestParam String allPlaceValue,HttpServletResponse response) {
        Integer userId = SpringMvcUtil.getUserId();
        String msg = "";
        try {
            msg = smsAssignaServiceImpl.smsTransferSetup(userId, allPlaceValue);
        } catch (Exception e) {
            logger.error("短信分配失败！",e);
        }
        SpringMvcUtil.responseWriter(response,msg);
    }

}