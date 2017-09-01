package com.rainsoft.union.web.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.union.web.account.model.PromotionsModel;
import com.rainsoft.union.web.account.model.WwbStrategy;
import com.rainsoft.union.web.account.service.PromotionsService;
import com.rainsoft.union.web.pubdata.model.DeviceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 旺旺币优惠活动
 * 包含区域性的设置 和 单个场所的赠送
 * Created by sun on 2016/9/20.
 */
@Controller
@RequestMapping("/promotions")
public class PromotionsController {
    private static final Logger logger = LoggerFactory.getLogger(PromotionsController.class);

    private static final String rowList = Constants.ROW_LIST;

    @Resource
    private PromotionsService promotionsServiceImpl;
    @Resource
    private PubDataService pubDataServiceImpl;
    /**
     * 简单的页面跳转 表格数据异步加载
     * @return
     */
    @RequestMapping("/toPresentPage")
    public String toPresentPage(Model model) {
        model.addAttribute("rowList", rowList);
        return "/account/wwbPresent";
    }


    /**
     * 通过登录的会员ID获取该会员的场所资料 参数 placeDataModel
     */
    @RequestMapping("/bySearch")
    protected void bySearch(@ModelAttribute PromotionsModel promotionsModel,HttpServletResponse response, HttpServletRequest request) {
        PageBean pageBean = getGridData(request);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = promotionsServiceImpl.getPromotionsList(promotionsModel, pageBean);
        } catch (Exception e) {
            logger.error("获取赠送列表数据出错了",e);
        }
        //jqGrid 请求
        SpringMvcUtil.responseJSONWriter(response, jsonObject);
    }

    /**
     * 根据易盟账号查询对应的场所
     */
   /* @RequestMapping("/getPlace")
    @ResponseBody
    protected Map getPlace(HttpServletRequest request) {
        String json = null;
        List<PromotionsModel> list1 = null;
        List<DeviceData> list2 = null;
        try {
            String eyName = request.getParameter("eyName");
            if (StringUtil.isNotEmpty(eyName)) {
                list1 = promotionsServiceImpl.getPlaceListByEyun(eyName);
                if (list1 != null && list1.size() > 0) {
                    String placeCode = list1.get(0).getPlaceCode();
                    list2 = pubDataServiceImpl.getBillDeviceList(placeCode);
                }
            }
        } catch (Exception e) {
            logger.error("获取赠送列表数据出错了", e);
        }
        //jqGrid 请求
        Map map = new HashMap();
        map.put("place", list1);
        map.put("device", list2);
        return map;
    }*/

    @RequestMapping("/getPlace")
    @ResponseBody
    protected Map getPlace(HttpServletRequest request) {
        String json = null;
        List<PromotionsModel> list1 = null;
        List<DeviceData> list2 = null;
        try {
            request.setCharacterEncoding("utf-8");
            String eyName = request.getParameter("eyName");
            if (StringUtil.isNotEmpty(eyName)) {
                eyName = URLDecoder.decode(eyName,"UTF-8");
                list1 = promotionsServiceImpl.getPlaceListByEyun(eyName);
                if (list1 != null && list1.size() > 0) {
                    String placeCode = list1.get(0).getPlaceCode();
                    list2 = pubDataServiceImpl.getBillDeviceList(placeCode);
                }
            }
        } catch (Exception e) {
            logger.error("获取赠送列表数据出错了", e);
        }
        //jqGrid 请求
        Map map = new HashMap();
        map.put("place", list1);
        map.put("device", list2);
        return map;
    }

    /**
     * 根据场所编号查询设备序列号及其关联主键
     */
    @RequestMapping("/getSerialNum")
    @ResponseBody
    protected String getSerialNum(HttpServletRequest request) {
        String json = null;
        try {
            String placeCode = request.getParameter("placeCode");
            if (StringUtil.isNotEmpty(placeCode)) {
                List<DeviceData> list = pubDataServiceImpl.getBillDeviceList(placeCode);
                json = JSONObject.toJSONString(list);
            }
        } catch (Exception e) {
            logger.error("获取赠送列表数据出错了", e);
        }
        //jqGrid 请求
        return json;
    }
    /**
     * 充送旺旺币
     */
    @RequestMapping("/presentWwb")
    @ResponseBody
    protected int presentWwb(@ModelAttribute PromotionsModel promotionsModel,@RequestParam(value = "imgFile", required = false) MultipartFile file) {
        int rs = -1;
        try {
            //rs = 1;
            promotionsModel.setUserId(SpringMvcUtil.getUserId());
            rs = promotionsServiceImpl.updatePresentWwb(promotionsModel,file);
        } catch (Exception e) {
            logger.error("赠送旺旺币失败了", e);
        }
        return rs;
    }
    /**
     * 审核
     */
    @RequestMapping("/commitPresentWwb")
    @ResponseBody
    protected int commitPresentWwb(@ModelAttribute PromotionsModel promotionsModel,HttpServletResponse response, HttpServletRequest request) {
        int rs = -1;
        try {
            promotionsModel.setUserId(SpringMvcUtil.getUserId());
            promotionsModel.setStatus("P");//审核通过状态
            rs = promotionsServiceImpl.updateSubmitPresentWwb(promotionsModel,null);
        } catch (Exception e) {
            logger.error("审核失败了",e);
        }
        return rs;
    }
    /**
     * 编辑
     */
    @RequestMapping("/editPresentWwb")
    @ResponseBody
    protected int editPresentWwb(@ModelAttribute PromotionsModel promotionsModel,@RequestParam(value = "imgFile", required = false) MultipartFile file) {
        int rs = -1;
        System.out.println("文件大小----------------"+file.getSize());
        try {
            promotionsModel.setUserId(SpringMvcUtil.getUserId());
            rs = promotionsServiceImpl.updateSubmitPresentWwb(promotionsModel,file);
        } catch (Exception e) {
            logger.error("编辑失败了",e);
        }
        return rs;
    }
    /**
     * 审核通过后
     * 赠送的钱加到账号
     */
    @RequestMapping("/giftPresentWwb")
    @ResponseBody
    protected int giftPresentWwb(@ModelAttribute PromotionsModel promotionsModel,HttpServletResponse response, HttpServletRequest request) {
        int rs = -1;
        try {
            promotionsModel.setUserId(SpringMvcUtil.getUserId());
            rs = promotionsServiceImpl.updateWwbInfo(promotionsModel);
        } catch (Exception e) {
            logger.error("编辑失败了",e);
        }
        return rs;
    }
    /**
     * 删除赠送记录 未审核的可以删除
     */
    @RequestMapping("/deletePresent")
    @ResponseBody
    protected int deletePresent(@RequestParam Integer id) {
        int rs = -1;
        try {
            rs = promotionsServiceImpl.deletePresent(id);
        } catch (Exception e) {
            logger.error("删除赠送旺旺币失败了",e);
        }
        return rs;
    }
        /**
     * 根据场所编号查询设备序列号及其关联主键
     */
    @RequestMapping("/wwbStrategy")
    protected String wwbStrategy(Model model) {
        model.addAttribute("rowList", rowList);
        return "/account/wwbStrategy";
    }
    //todo
    /**
     * 通过登录的会员ID获取该会员的场所资料 参数 placeDataModel
     */
    @RequestMapping("/strategyList")
    @ResponseBody
    protected  JSONObject strategyList(@ModelAttribute WwbStrategy wwbStrategy,HttpServletRequest request) {
        PageBean pageBean = getGridData(request);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = promotionsServiceImpl.getWwbStrategyList(wwbStrategy, pageBean);
        } catch (Exception e) {
            logger.error("获取赠送列表数据出错了",e);
        }
        return jsonObject;
    }

    /**
     * 新增策略
     */
    @RequestMapping("/insertStrategy")
    @ResponseBody
    protected int insertStrategy(@ModelAttribute WwbStrategy wwbStrategy) {
        int rs = -1;
        try {
            wwbStrategy.setUserId(SpringMvcUtil.getUserId());
            rs = promotionsServiceImpl.insertStrategy(wwbStrategy);
        } catch (Exception e) {
            logger.error("新增赠送旺旺币策略失败了",e);
        }
        return rs;
    }

    /**
     * 修改策略
     */
    @RequestMapping("/updateStrategy")
    @ResponseBody
    protected int updateStrategy(@ModelAttribute WwbStrategy wwbStrategy) {
        int rs = -1;
        try {
            wwbStrategy.setUserId(SpringMvcUtil.getUserId());
            rs = promotionsServiceImpl.updateStrategy(wwbStrategy);
        } catch (Exception e) {
            logger.error("修改赠送旺旺币策略失败了",e);
        }
        return rs;
    }

    /**
     * 审核策略
     * @param wwbStrategy
     * @return
     */
    @RequestMapping("/commitStrategy")
    @ResponseBody
    protected int commitStrategy(@ModelAttribute WwbStrategy wwbStrategy) {
        int rs = -1;
        try {
            wwbStrategy.setUserId(SpringMvcUtil.getUserId());
            wwbStrategy.setStatus("8");
            rs = promotionsServiceImpl.updateStrategy(wwbStrategy);
        } catch (Exception e) {
            logger.error("审核失败了",e);
        }
        return rs;
    }
    /**
     * 页面有jqGrid 处理表格的数据
     *
     * @param request
     * @return
     */
    protected PageBean getGridData(HttpServletRequest request) {
        // 当前页
        String currPageObj = request.getParameter("page");

        // 当前页显示条数
        String pageSizeObj = request.getParameter("rows");

        // 排序字段
        String sortObj = request.getParameter("sidx");

        // 排序顺序
        String sordObj = request.getParameter("sord");


        PageBean page = new PageBean();

        if (StringUtil.isNotEmpty(currPageObj)) {
            page.setCurrPage(Integer.parseInt((currPageObj)));
        } else {
            page.setCurrPage(1);
        }

        if (StringUtil.isNotEmpty(pageSizeObj)) {
            page.setPageSize(Integer.parseInt(pageSizeObj));
        } else {
            page.setPageSize(10);
        }

        if (StringUtil.isNotEmpty(sortObj)) {
            page.setSort(sortObj.toString());
        } else {
            page.setSort("id");
        }

        if (StringUtil.isNotEmpty(sordObj)) {
            page.setOrder(sordObj.toString());
        } else {
            page.setOrder("asc");
        }
        return page;
    }

}
