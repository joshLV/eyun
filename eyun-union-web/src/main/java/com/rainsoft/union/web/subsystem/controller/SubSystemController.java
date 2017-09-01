package com.rainsoft.union.web.subsystem.controller;

import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.SystemConfigUtil;
import com.rainsoft.union.web.subsystem.service.SubSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 子系统 服务
 * 1、旺旺吧进销存
 */
@Controller
@RequestMapping("/subSystem")
public class SubSystemController {
    private static final Logger logger = LoggerFactory.getLogger(SubSystemController.class);


    @Resource
    private SubSystemService subSystemServiceImpl;//@bmanageCoobar

    /**
     * 跳转到旺旺吧登录页面
     *
     * @return
     */
    @RequestMapping("/coobar/login")
    public String coobar_login(Model model) {
        Integer userID = SpringMvcUtil.getUserId();
        Integer rs = -1;
        try {
            rs = subSystemServiceImpl.saveCoobar(userID);
        } catch (Exception e) {
            logger.error("注册失败", e);
        }
        String url = SystemConfigUtil.getValue("COOBAR_LOGIN_URL");
        return "redirect:" + url;

    }

}