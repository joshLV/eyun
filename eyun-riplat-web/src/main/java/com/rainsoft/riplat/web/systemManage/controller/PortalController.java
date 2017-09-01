package com.rainsoft.riplat.web.systemManage.controller;

import com.alibaba.fastjson.JSON;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.riplat.web.systemManage.model.Portal;
import com.rainsoft.riplat.web.systemManage.service.IPortalService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;

/**
 * 认证模板
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
@Controller
@RequestMapping("/systemManage/portal")
public class PortalController extends SpringBaseController<Portal, String> {

    private final String PREXIF = "/systemManage/portal";

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

    /**
     *  通过登录的会员ID获取该会员的模板资料
     *  参数 placeDataModel
     */
    @RequestMapping("/bySearch")
    protected void bySearch(@ModelAttribute Portal portal) {
        /**将会员ID赋值给模板资料实体，通过会员ID查询该会员所拥有的模板**/
        portal.setUserId(SpringMvcUtil.getUserId());
        /**调用父类封装的方法，完成分页，同时将数据传到页面**/
        super.loadGrid(portal);
    }

    /**
     * 获取会员的场所和设备
     * @param portal 模板实体
     */
    @RequestMapping("/getPlace")
    protected void getPlace(@ModelAttribute Portal portal) {
        /**将会员ID赋值给模板资料实体，通过会员ID查询该会员所拥有的模板**/
        portal.setUserId(SpringMvcUtil.getUserId());
        List<Portal> list = getBaseService().selectList("getPlace", portal);
        String json = JSON.toJSONString(list);
        SpringMvcUtil.responseWriter(getResponse(), json);
    }


    @RequestMapping("/call")
    protected void call(@ModelAttribute Portal portal) {

        /*List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);*/
        String[] a = {"1","2"};
        portal.setArr(a);
        getBaseService().findBy("test", portal);
        SpringMvcUtil.responseWriter(getResponse(), portal.getId() + "");
    }

}
