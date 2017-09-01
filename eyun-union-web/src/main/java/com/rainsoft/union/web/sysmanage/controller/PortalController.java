package com.rainsoft.union.web.sysmanage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.pubdata.model.PlaceData;
import com.rainsoft.union.web.pubdata.service.PubDataService;
import com.rainsoft.union.web.sysmanage.model.Portal;
import com.rainsoft.union.web.sysmanage.service.IPortalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * 认证模板
 *
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Controller
@RequestMapping("/systemManage/portal")
@Log(clazzDesc = "认证模板")

public class PortalController extends SpringBaseController<Portal, String> {

    private final String PREXIF = "/sysmanage/portal";

    @Resource
    private IPortalService portalService;

    @Override
    protected IMybatisBasePersitenceService<Portal, String> getBaseService() {
        return portalService;
    }

    @Override
    protected String getPrefix() {
        return PREXIF;
    }

    @Resource
    private PubDataService pubDataServiceImpl;

    /**
     * 通过登录的会员ID获取该会员的模板资料
     * 参数 placeDataModel
     */
    @RequestMapping("/bySearch")
    protected void bySearch(@ModelAttribute Portal portal) {
        /**将会员ID赋值给模板资料实体，通过会员ID查询该会员所拥有的模板**/
        portal.setUserId(SpringMvcUtil.getUserId());
        /**调用父类封装的方法，完成分页，同时将数据传到页面**/
        super.loadGrid(portal);
    }

    /**
     * 获取场所的businessFlag
     *
     * @param placeId 场所id
     */
    @RequestMapping("/getBusinessFlag")
    protected void getBusinessFlag(@RequestParam Integer placeId) {
        JSONObject obj = new JSONObject();
        String businessFlag = "YYYYYYYYYYYYYYY";//默认控制码 不给投放任何素材
        if (placeId > 0) {
            try {
                businessFlag = pubDataServiceImpl.getBusinessFlagByPlaceId(placeId);
            } catch (Exception e) {
                logger.error("获取businessFlag失败了：" + e);
            }
        }
        char businessFlagArr[] = businessFlag.toCharArray();
        for (int i = 0; i < businessFlagArr.length; i++) {
            obj.put("Flag" + (i + 1), businessFlagArr[i]);
        }
        SpringMvcUtil.responseJSONWriter(getResponse(), obj.toJSONString());
    }

    /**
     * 获取自己的场所列表
     */
    @RequestMapping("/getOwnPlaceList")
    protected void getOwnPlaceList() {
        PlaceData placeData = new PlaceData();
        placeData.setUserId(SpringMvcUtil.getUserId());
        List<PlaceData> placeDataList = null;
        try {
            placeDataList = pubDataServiceImpl.getPlaceList(placeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rs = JSON.toJSONString(placeDataList);
        SpringMvcUtil.responseJSONWriter(getResponse(), rs);
    }

    /**
     * 保存认证模版
     *
     * @param portal 模版实体
     */
    @Log(methodDesc = "保存")
    @RequestMapping("/savePortal")
    protected void savePortal(@ModelAttribute Portal portal) {
        // PC版和手机版重定向地址都存放在该字段中，以英文逗号分隔，PC重定向地址取，符号左边的值，手机取，符号右边的值
        String  successToUrl = portal.getPcToUrl() + "," + portal.getMobileToUrl();
        if (",".equals(successToUrl)) {
            portal.setSuccessToUrl(null);
        }else {
            portal.setSuccessToUrl(successToUrl);
        }
        Integer rs = -1;
        try {
            rs = portalService.savePortal(portal);
        } catch (Exception e) {
            logger.error("保存模版失败" + e);
        }
        SpringMvcUtil.responseJSONWriter(getResponse(), rs);
    }

    /**
     * 修改认证模版
     *
     * @param portal 模版
     */
    @RequestMapping("/updatePortal")
    protected void updatePortal(@ModelAttribute Portal portal) {
        String  successToUrl = portal.getPcToUrl() + "," + portal.getMobileToUrl();
        if (",".equals(successToUrl)) {
            portal.setSuccessToUrl(null);
        }else {
            portal.setSuccessToUrl(successToUrl);
        }
        Integer rs = -1;
        try {
            rs = portalService.updatePortal(portal);
        } catch (Exception e) {
            logger.error("编辑模版失败" +e);

        }
        SpringMvcUtil.responseJSONWriter(getResponse(), rs);
    }

    /**
     * 修改状态
     *
     * @param portal
     */
    @RequestMapping("/updateStatus")
    protected void updateStatus(@ModelAttribute Portal portal) {
        Result result = new Result();
        portal.setUserId(SpringMvcUtil.getUserId());
        Integer i = getBaseService().update(portal);
        result.setMessage(i + "");
        /**页面获取返回值**/
        SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }

    /**
     * 查询模版详细
     *
     * @param id 模版id
     */
    @RequestMapping("/getPortal")
    protected void getPortal(@RequestParam String id) {
        Portal portal = getBaseService().findById(id);
        String successToUrl = portal.getSuccessToUrl();
        if (StringUtil.isNotEmpty(successToUrl)) {
            int index = successToUrl.indexOf(",");
            if (!"".equals(successToUrl.substring(0, index) + "")) {
                portal.setPcToUrl(successToUrl.substring(0, index));
            }
            if (!"".equals(successToUrl.substring(index + 1) + "")) {
                portal.setMobileToUrl(successToUrl
                        .substring(index + 1));
            }
        }
        SpringMvcUtil.responseJSONWriter(getResponse(), portal);
    }

    /**
     * 设置默认模版
     *
     * @param id      模版id
     * @param placeId 场所ID
     */
    @RequestMapping("/setDefaultModel")
    protected void setDefaultModel(@RequestParam Integer id, @RequestParam Integer placeId) {
        Integer rs = -1;
        try {
            rs = portalService.updateDefaultModel(id, placeId);
        } catch (Exception e) {
            logger.error("设置默认模版失败" + e);
        }
        SpringMvcUtil.responseJSONWriter(getResponse(), rs);
    }

    /**
     * 跳转手机模版预览頁
     * @param id
     * @return
     */
    @RequestMapping("/mobilePreview")
    protected String  mobilePreview(@RequestParam String id,Model model) {
        Portal portal = getBaseService().findById(id);
        //getSession().setAttribute("portal",portal);
        model.addAttribute("portal", portal);
        return "sysmanage/mobilePreview";
    }
    /**
     * 跳转PC模版预览頁
     * @param id
     * @return
     */
    @RequestMapping("/portalPreview")
    protected String  portalPreview(@RequestParam String id,Model model) {
        Portal portal = getBaseService().findById(id);
        //getSession().setAttribute("portal",portal);
        model.addAttribute("portal", portal);
        return "sysmanage/portalPreview";
    }

    @Log(methodDesc = "删除模版")
    @RequestMapping("/delPortal")
    public void deleteMaterial() {
        RequestContext requestContext = new RequestContext(getRequest());
        /**result用来存放Ajax 处理结果**/
        Result result = new Result();
        /**获取ids 可以批量 也可单个id**/
        String ids = getRequest().getParameter("ids");
        /**获取传入数据的更新时间**/
        String updateTimes = getRequest().getParameter("updateTimes");
        Integer rs = -1;
        try {
            rs = portalService.deleteMaterial(ids, updateTimes);
        } catch (Exception e) {
            logger.error("删除portal失败了：" + e);
        }
        if (rs>0) {
            //读取国际化代码
            String message = requestContext.getMessage("info.opt.success");
            result.setMessage(message);
        }else if (rs == -2) {
            String message = requestContext.getMessage("errors.delerror");
            result.setMessage(message);
        } else {
            String message = requestContext.getMessage("errors.error");
            result.setMessage(message);
        }
        responseAjaxData(result);
    }
}
