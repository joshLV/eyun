package com.rainsoft.riplat.web.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.INoticeReplyDao;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;

@Repository
public class NoticeReplyDaoImpl extends MybatisPersitenceDaoImpl<ReplyHistory, String> implements INoticeReplyDao {

    
}

