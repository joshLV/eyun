package com.rainsoft.union.web.sysmanage.controller;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.union.web.sysmanage.model.Material;
import com.rainsoft.union.web.sysmanage.service.IMaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;

/**
 * 认证素材
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Controller
@RequestMapping("/systemManage/material")
@Log(clazzDesc = "认证素材")

public class MaterialController extends SpringBaseController<Material, String> {

    private final String PREXIF = "/sysmanage/material";

    @Resource
    private IMaterialService materialService;

    @Override
    protected IMybatisBasePersitenceService<Material, String> getBaseService() {
        return materialService;
    }

    @Override
    protected String getPrefix() {
        return PREXIF;
    }

    /**
     *  通过登录的会员ID获取该会员的素材资料
     *  参数 placeDataModel
     */
    @RequestMapping("/bySearch")
    protected void bySearch(@ModelAttribute Material material) {
        /**将会员ID赋值给素材资料实体，通过会员ID查询该会员所拥有的素材**/
        material.setUserId(SpringMvcUtil.getUserId());
        /**调用父类封装的方法，完成分页，同时将数据传到页面**/
        super.loadGrid(material);
    }

    @Log(methodDesc = "新增素材")
    @RequestMapping("/saveMaterial")
    public void saveMaterial(@ModelAttribute Material material, @RequestParam(value = "imgFile", required = false) MultipartFile file) {
        Result result = new Result();
        material.setUserId(SpringMvcUtil.getUserId());
        Integer i = -1;
        try {
            i = materialService.saveMaterial(material, file);
        } catch (Exception e) {
            logger.error("保存认证素材失败：" + e);
        }
        result.setMessage(i + "");
        SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }

    @Log(methodDesc = "修改素材")
    @RequestMapping("/updMaterial")
    public void updMaterial(@ModelAttribute Material material, @RequestParam(value = "imgFile", required = false) MultipartFile file) {
        Result result = new Result();
        material.setUserId(SpringMvcUtil.getUserId());
        Integer i = -1;
        try {
            i = materialService.updateMaterial(material, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setMessage(i + "");
        SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }

    @Log(methodDesc = "素材提交审核")
    @RequestMapping("/submitMaterial")
    public void submitMaterial(@ModelAttribute Material material) {
        Result result = new Result();
        material.setUserId(SpringMvcUtil.getUserId());
        Integer i = getBaseService().update(material);
        result.setMessage(i + "");
        /**页面获取返回值**/
        SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }

    /**
     * 删除素材（包含批量删除）
     * @throws Exception
     */
    @Log(methodDesc = "删除素材")
    @RequestMapping("/delMaterial")
    public void deleteMaterial() {
        RequestContext requestContext = new RequestContext(getRequest());
        /**result用来存放Ajax 处理结果**/
        Result result = new Result();
        /**获取ids 可以批量 也可单个id**/
        String ids = getRequest().getParameter("ids");
        /**获取传入数据的更新时间**/
        String updateTimes = getRequest().getParameter("updateTimes");
        /**获取传入数据的素材url**/
        String materialUrls = getRequest().getParameter("materialUrls");
        Integer rs = -1;
        try {
            rs = materialService.deleteMaterial(ids, updateTimes, materialUrls);
        } catch (Exception e) {
            logger.error("删除素材失败了：" + e);
        }
        if (rs>0) {
            //读取国际化代码
            String message = requestContext.getMessage("info.opt.success");
            result.setMessage(message);
        } else if (rs == -2) {
            String message = requestContext.getMessage("errors.delerror");
            result.setMessage(message);
        } else {
            String message = requestContext.getMessage("errors.error");
            result.setMessage(message);
        }
        responseAjaxData(result);
    }

    /**
     * 根据会员ID和图片类型选择素材
     * @param material 素材实体
     */
    @RequestMapping("/choseImg")
    protected void choseImg(@ModelAttribute Material material) {
        material.setUserId(SpringMvcUtil.getUserId());
        super.loadGrid(material,"choseImg");
    }

}
