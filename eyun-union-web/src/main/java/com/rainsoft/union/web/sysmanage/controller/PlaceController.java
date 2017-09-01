package com.rainsoft.union.web.sysmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.pubdata.model.PlaceEnum;
import com.rainsoft.union.web.sysmanage.model.ParamFilter;
import com.rainsoft.union.web.sysmanage.service.IPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * PlaceController
 * Created by qianna on 2016/11/28
 */
@Controller
@RequestMapping("/place")
public class PlaceController extends SpringBaseController {
    private static Logger logger = LoggerFactory.getLogger(PlaceController.class);

    @Resource
    private IPlaceService placeServiceImpl;

    @RequestMapping("/placePage")
    public String placePage(Model model ){
        PlaceEnum[] feeFlagArr = PlaceEnum.values();
        model.addAttribute("rowList", rowList);
        model.addAttribute("feeFlagArr",feeFlagArr);
        return "/sysmanage/place/placeList";
    }

    @RequestMapping("/placeList")
    public void placeList(@ModelAttribute("param")ParamFilter param){
        JSONObject jsonObject = null;
        PageBean pageBean = getGridData();
        try {
             jsonObject = placeServiceImpl.getPlaceList(param,pageBean);
        } catch (Exception e) {
            logger.error("PlaceController.placeList-->"+e.getMessage(),e);
        }
        SpringMvcUtil.responseJSONWriter(getResponse() , jsonObject);
    }

    @RequestMapping("/updateSmsRechargeFlag")
    public void updateSmsRechargeFlag(@ModelAttribute("param")ParamFilter param){

        Integer row = -1;
        String msg = "更新失败";
        String status = Constants.RETURN_ERROR;
        try {
            row = placeServiceImpl.updateSmsRechargeFlag(param);
        } catch (Exception e) {
            logger.error("PlaceController.updateSmsRechargeFlag-->" + e.getMessage(),e);
        }
        if(row > 0 ){
            status = Constants.RETURN_SUCCESS;
            msg = "更新成功";
        }
        Result result = new Result(status,msg , null);
        responseAjaxData(result);
    }

    @Override
    protected IMybatisBasePersitenceService getBaseService() {
        return null;
    }

    @Override
    protected String getPrefix() {
        return null;
    }
}
