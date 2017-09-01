package com.rainsoft.riplat.web.notice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.riplat.web.notice.dao.INoticeReplyDao;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.service.INoticeReplyService;

@Service
public class NoticeReplyServiceImpl extends MybatisBasePersitenceServiceImpl<ReplyHistory, String> implements INoticeReplyService {
    
    @Resource
    private INoticeReplyDao noticeReplyDaoImpl;
    
    @Override
    protected IMybatisPersitenceDao<ReplyHistory, String> getBaseDao() {
        return noticeReplyDaoImpl;
    }

    @Override
    public List<ReplyHistory> findListBy(ReplyHistory item, String sortColumn, String des) {
        if(StringUtil.isEmpty(sortColumn)){
            sortColumn = "reply_Time";
        }
        if(StringUtil.isEmpty(des)){
            des = "desc";// 设置按到时间倒序排列
        }
        return super.findListBy(item, sortColumn, des);
    }
}