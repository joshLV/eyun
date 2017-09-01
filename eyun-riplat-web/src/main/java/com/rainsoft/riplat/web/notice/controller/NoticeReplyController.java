package com.rainsoft.riplat.web.notice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.service.INoticeReplyService;

@Controller
@RequestMapping("/noticeReply")
public class NoticeReplyController extends SpringBaseController<ReplyHistory, String> {

    @Resource
    private INoticeReplyService noticeReplyServiceImpl;
    
    @Override
    protected IMybatisBasePersitenceService<ReplyHistory, String> getBaseService() {
        return noticeReplyServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/notice/";
    }    
}
