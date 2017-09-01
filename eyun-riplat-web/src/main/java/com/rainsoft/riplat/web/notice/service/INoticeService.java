package com.rainsoft.riplat.web.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.notice.model.Notice;
import com.rainsoft.riplat.web.notice.model.Notice4App;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;


public interface INoticeService extends IMybatisBasePersitenceService<Notice, String> {
    
    Notice getNoticeByIdForWeb(String id);
    
    Notice getNoticeById(Notice notice);
    
    void saveSendNotice(String id);
    
    void saveOrUpdate(Notice notice , String userId);

    void saveOrUpdate(Notice notice ,JSONObject json);

    String storeFile(CommonsMultipartFile file);
    
    List<String> getNoticeTask(String quartzSendTime);
    
    Result noticeList(String userId, int pageSize, String lastRecordId, String noticeLevel, String isDelete);
    
    Notice getNoticeDetailInfoById(String noticeId);

    boolean validateEnablePlatformId(String platformId);

    boolean removePlatformId(String oldPlatformId);

    boolean disablePlatformId(String platformId);

    boolean validatePlatformId(String string);

    Result readNotice(String userId, String recordId);

    Result saveReplayNotice(String userId, String recordId, String replyContent,String userType);

    Notice4App noticeDetail(String recordId);

    List<ReplyHistory> getRelyHistoryByNoticeId(String recordId);
    
    /**
     * 
     * @Description: 方法功能说明：app删除通知 
     * @param map
     * @return
     * @return Result
     * @author yty
     * @date 2016年8月30日下午4:45:42
     */
    Result delOrRedNoticeByApp(Map<String, Object> map);
}