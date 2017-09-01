package com.rainsoft.riplat.web.systemManage.controller;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.UploadImgUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.systemManage.model.Material;
import com.rainsoft.riplat.web.systemManage.service.IMaterialService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 认证素材
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Controller
@RequestMapping("/systemManage/material")
public class MaterialController extends SpringBaseController<Material, String> {

    private final String PREXIF = "/systemManage/material";

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

    @RequestMapping("/saveOrUpdMaterial")
    public void saveMaterial(@ModelAttribute Material material) {
        Result result = new Result();
        /**页面传入存储过程的Id**/
        String keyId = getRequest().getParameter("keyId");
        /**获取当前操作用户的ID**/
        material.setUserId(SpringMvcUtil.getUserId());
        /**开始调用存储过程**/
        Integer i = materialService.call(keyId, material);
        result.setMessage(i.toString());
        /**页面获取返回值**/
        SpringMvcUtil.responseJSONWriter(getResponse(), result);
    }

    /**
     * 删除素材（包含批量删除）
     * @throws Exception
     */
    @RequestMapping("/delMaterial")
    public void deleteMaterial() throws Exception {
        /**result用来存放Ajax 处理结果**/
        Result result = new Result();
        /**获取ids 可以批量 也可单个id**/
        String ids = getRequest().getParameter("ids");
        /**获取传入数据的更新时间**/
        String updateTimes = getRequest().getParameter("updateTimes");
        /**获取传入数据的素材url**/
        String materialUrls = getRequest().getParameter("materialUrls");
        /**String 转成数组用于循环处理数据**/
        String[] idArr = ids.split(",");
        String[] updateTimeArr = updateTimes.split(",");
        String[] materialUrlArr = materialUrls.split(",");
        /**文件url**/
        List<String> fileList = new ArrayList<String>();
        /**数组转成list，存放的是id**/
        List<String> list = Arrays.asList(idArr);
        /**根据传入id 查询数据更新时间放入map**/
        Map<String, Object> map = materialService.getUpdatetimeByIds(list);
        for (int i = 0; i < idArr.length; i++) {
            /**如果数据已被更改 则取消操作**/
            if (!updateTimeArr[i].equals(map.get(idArr[i]))) {
                result.setMessage(Constants.DEL_ALL_ERR_MSG);
                responseAjaxData(result);
                return;
            }
            fileList.add(materialUrlArr[i]);
        }
        /**开始删除数据**/
        int rs = 0;
        if (idArr.length==1){
            rs = getBaseService().deleteById(idArr[0]);
        }else {
            rs = getBaseService().deleteByIds(idArr);
        }
        if (rs>0) {
            result.setMessage(Constants.RETURN_SUCCESS);
            /**数据删除成功后才开始删除文件**/
            UploadImgUtil.delFiles(fileList);
        }else{
            result.setMessage(Constants.SYS_EXCEPTION);
        }
        responseAjaxData(result);
    }
}
