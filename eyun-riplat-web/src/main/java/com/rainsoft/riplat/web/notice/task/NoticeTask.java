package com.rainsoft.riplat.web.notice.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rainsoft.base.common.utils.DateUtils;
import com.rainsoft.riplat.web.notice.service.INoticeService;



/**
 * 
 * @author ldz
 *
 */
@Component
public class NoticeTask {
	
    @Resource
    private INoticeService noticeServiceImpl;
    
    @Scheduled(cron = "0 0/1 * * * ?")
	public void execute() {
	    List<String> noticeIds = noticeServiceImpl.getNoticeTask(DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm"));
	    if (!CollectionUtils.isEmpty(noticeIds)) {
	        for (String noticeId : noticeIds) {
	            noticeServiceImpl.saveSendNotice(noticeId);
	        }
	    }
	}
	
}
