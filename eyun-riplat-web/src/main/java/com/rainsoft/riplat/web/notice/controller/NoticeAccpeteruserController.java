package com.rainsoft.riplat.web.notice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.riplat.web.notice.model.AccpeterUser;
import com.rainsoft.riplat.web.notice.service.IAccpeterUserService;

@Controller
@RequestMapping("/noticeAccpeteruser")
public class NoticeAccpeteruserController extends SpringBaseController<AccpeterUser, String> {

    @Resource
    private IAccpeterUserService accpeterUserServiceImpl;
    
    @Override
    protected IMybatisBasePersitenceService<AccpeterUser, String> getBaseService() {
        return accpeterUserServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/notice/";
    }

    @Override
    protected void loadGrid(AccpeterUser item) {
        
        super.loadGrid(item);
    }
    
    
}
