package com.rainsoft.riplat.web.notice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.riplat.web.notice.model.PlatformUser;
import com.rainsoft.riplat.web.notice.service.IPlatformUserService;

@Controller
@RequestMapping("/platformUser")
public class PlatformUserController extends SpringBaseController<PlatformUser, String> {

    @Resource
    private IPlatformUserService platformUserServiceImpl;
    
    @Override
    protected IMybatisBasePersitenceService<PlatformUser, String> getBaseService() {
        return platformUserServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/platformUser/";
    }
    

    /**
     * 获取平台用户信息
     * @param params
     */
    @RequestMapping(value = "/loadPlatformUserGrid", method = RequestMethod.POST)
    public void loadPlatformUserGrid(@ModelAttribute("params") String params) {

      
    }
    
}
