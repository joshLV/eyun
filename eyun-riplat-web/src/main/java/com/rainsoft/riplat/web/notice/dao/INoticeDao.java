package com.rainsoft.riplat.web.notice.dao;

import java.util.List;
import java.util.Map;

import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.riplat.web.notice.model.AccpeterUser;
import com.rainsoft.riplat.web.notice.model.AccpeteruserFeedback;
import com.rainsoft.riplat.web.notice.model.Notice;
import com.rainsoft.riplat.web.notice.model.Notice4App;
import com.rainsoft.riplat.web.notice.model.NoticeUnit;
import com.rainsoft.riplat.web.notice.model.PubArea;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.model.Unit;

public interface INoticeDao extends IMybatisPersitenceDao<Notice, String> {
    
    List<AccpeterUser> getPlaceGroupAccpeterUser(String noticeId);
    
    void saveNoticeUnit(NoticeUnit noticeUnit);
    
    void saveAccpeteruserFeedback(AccpeteruserFeedback accpeteruserFeedback);
    
//    List<String> getEdaIdsByPlaceGroup(String groupId);
    
    List<String> getEdaIdsByPlaceGroup(Map<String, Object> map);
    
//    List<String> getEdaIdsByUserGroup(String groupId);
    
    List<String> getEdaIdsByUserGroup(Map<String, Object> map);

    void deleteNoticeUnitById(Integer id);

    void deleteAccpeteruserFeedbackById(Integer id);

    Notice getUserNoticeById(Integer id);

    Notice getGroupNoticeById(Integer id);
    
    List<Unit> getNoticeUnitById(Integer id);
    
    List<String> getNoticeTask(String quartzSendTime);

    /**
     * 根据记录ID查询rownum
     * @param userId 易达Id
     * @param lastRecordId 最后一条记录的id
     * @return
     */
	int getNoticeIdRowNum(Map<String, Object> map);

	/**
	 * 获取通知列表
	 * @param userId 易达Id
	 * @return
	 */
	List<Notice4App> getNoticeList(Map<String, Object> map);

	/**
	 * 易达用户回复通知信息内容
	 * @param userId 用户Id
	 * @param recordId 通知信息Id
	 * @param message 回复内容
	 * @param userType 用户类型
	 * @return
	 */
	void saveReplyNotice(String userId, String recordId, String replyContext,String userType);

	/**
	 * 获取单个通知通告信息
	 * @param recordId 通知通告Id
	 * @return
	 */
	Notice4App getEdaNoticeById(String recordId);

	/**
	 * 获取易达用户未读的通知通告数量
	 * @param userId
	 * @return
	 */
	int getUnReadCount(Map<String, Object> map);

	/**
	 * WEB端获取信息详情
	 * @param noticeId
	 * @return
	 */
    Notice getNoticeDetailInfoById(String noticeId);

    Notice getNoticeById(String id);

//    List<String> getAllEdaIdsByUserId(String userId);
    
    List<String> getAllEdaIdsByUserId(Map<String, Object> map);

    /**
     * 查询当前用户下所有的区域
     * @param userId
     * @return
     */
    List<PubArea> getAreaIdByUserId(Integer userId);

    /**
     * 递归查询某区域下的所有子区域id
     * @param areaId
     * @return
     */
    List<String> getSubAreaIdsByParAreaId(String areaId);

    /**
     * 获取易达用户Ids根据区域Id
     * @param areaId
     * @return
     */
//    List<String> getEdaIdsByAreaId(String areaId);
    List<String> getEdaIdsByAreaId(Map<String, Object> map);

    /**
     * 获取通知内容下回复所有记录
     * @param recordId
     * @return
     */
    List<ReplyHistory> getRelyHistoryByNoticeId(String recordId);

    /**
     * 改变阅读状态
     * @param userId
     * @param recordId
     * @param status
     */
    void updateAccpterStatus(String userId, String recordId,Integer status);
    
    Integer delOrRedNoticeByApp(Map<String, Object> map);
}