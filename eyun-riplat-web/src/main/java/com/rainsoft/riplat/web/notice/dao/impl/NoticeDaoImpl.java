package com.rainsoft.riplat.web.notice.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rainsoft.base.common.dao.impl.MybatisPersitenceDaoImpl;
import com.rainsoft.riplat.web.notice.dao.INoticeDao;
import com.rainsoft.riplat.web.notice.model.AccpeterUser;
import com.rainsoft.riplat.web.notice.model.AccpeteruserFeedback;
import com.rainsoft.riplat.web.notice.model.Notice;
import com.rainsoft.riplat.web.notice.model.Notice4App;
import com.rainsoft.riplat.web.notice.model.NoticeUnit;
import com.rainsoft.riplat.web.notice.model.PubArea;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.model.Unit;

@Repository
public class NoticeDaoImpl extends MybatisPersitenceDaoImpl<Notice, String> implements INoticeDao {

    @Override
    public List<AccpeterUser> getPlaceGroupAccpeterUser(String noticeId) {
        return this.getSqlSession().selectList(getSqlName("getPlaceGroupAccpeterUser"), noticeId);
    }

    @Override
    public void saveNoticeUnit(NoticeUnit noticeUnit) {
        this.getSqlSession().insert(getSqlName("saveNoticeUnit"), noticeUnit);
    }
    
    @Override
    public void saveAccpeteruserFeedback(AccpeteruserFeedback accpeteruserFeedback) {
        this.getSqlSession().insert(getSqlName("saveAccpeteruserFeedback"), accpeteruserFeedback);
    }

//    @Override
//    public List<String> getEdaIdsByPlaceGroup(String groupId) {
//        return this.getSqlSession().selectList(getSqlName("getEdaIdsByPlaceGroup"), groupId);
//    }
    
    @Override
    public List<String> getEdaIdsByPlaceGroup(Map<String, Object> map) {
        return this.getSqlSession().selectList(getSqlName("getEdaIdsByPlaceGroup"), map);
    }

//    @Override
//    public List<String> getEdaIdsByUserGroup(String groupId) {
//        return this.getSqlSession().selectList(getSqlName("getEdaIdsByUserGroup"), groupId);
//    }
    
    @Override
    public List<String> getEdaIdsByUserGroup(Map<String, Object> map) {
        return this.getSqlSession().selectList(getSqlName("getEdaIdsByUserGroup"), map);
    }

    @Override
    public void deleteNoticeUnitById(Integer id) {
        this.getSqlSession().delete(getSqlName("deleteNoticeUnitById"), id);
    }

    @Override
    public void deleteAccpeteruserFeedbackById(Integer id) {
        this.getSqlSession().delete(getSqlName("deleteAccpeteruserFeedbackById"), id);
    }

    @Override
    public Notice getUserNoticeById(Integer id) {
        return this.getSqlSession().selectOne(getSqlName("getUserNoticeById"), id);
    }

    @Override
    public Notice getGroupNoticeById(Integer id) {
        return this.getSqlSession().selectOne(getSqlName("getGroupNoticeById"), id);
    }

    @Override
    public List<String> getNoticeTask(String quartzSendTime) {
        return this.getSqlSession().selectList(getSqlName("getNoticeTask"), quartzSendTime);
    }

	@Override
	public List<Notice4App> getNoticeList(Map<String, Object> map) {
		return this.getSqlSession().selectList(getSqlName("getNoticeList"), map);
	}
	
	@Override
	public int getNoticeIdRowNum(Map<String, Object> map) {
//		AccpeteruserFeedback accpeteruserFeedback = new AccpeteruserFeedback();
//		accpeteruserFeedback.setAppuserId(userId);
//		accpeteruserFeedback.setNoticeId(lastRecordId);
		return (int)getSqlSession().selectOne(getSqlName("getNoticeIdRowNum"),map);
	}

	@Override
	public void updateAccpterStatus(String userId, String recordId,Integer status) {
		AccpeteruserFeedback accpeteruserFeedback = new AccpeteruserFeedback();
		accpeteruserFeedback.setAppuserId(userId);
		accpeteruserFeedback.setNoticeId(recordId);
		accpeteruserFeedback.setStatus(status);
		this.getSqlSession().insert(getSqlName("updateAccpterStatus"), accpeteruserFeedback);
	}

	@Override
	public void saveReplyNotice(String userId, String recordId, String replyContent,String userType) {
	    
	    ReplyHistory replyHistory = new ReplyHistory();
	    replyHistory.setNoticeId(recordId);
	    replyHistory.setReplyContent(replyContent);
	    replyHistory.setReplyTime(new Date());
	    replyHistory.setUserId(userId);
	    replyHistory.setUserType(userType);
	    
		this.getSqlSession().insert(getSqlName("replayNotice"), replyHistory);
	}

	@Override
	public Notice4App getEdaNoticeById(String recordId) {
		return this.getSqlSession().selectOne(getSqlName("getEdaNoticeById"), recordId);
	}

	@Override
	public int getUnReadCount(Map<String, Object> map) {
		return (int)getSqlSession().selectOne(getSqlName("getUnReadCount"), map);
	}

    @Override
    public Notice getNoticeDetailInfoById(String noticeId) {
        return this.getSqlSession().selectOne(getSqlName("getNoticeDetailInfo"), noticeId);
    }

    @Override
    public List<Unit> getNoticeUnitById(Integer id) {
        return this.getSqlSession().selectList(getSqlName("getNoticeUnitById"), id);
    }

    @Override
    public Notice getNoticeById(String id) {
        return this.getSqlSession().selectOne(getSqlName("getNoticeById"), id);
    } 
    
//    @Override
//    public List<String> getAllEdaIdsByUserId(String userId) {
//        return this.getSqlSession().selectList(getSqlName("getAllEdaIdsByUserId"), userId);
//    }
    
    @Override
    public List<String> getAllEdaIdsByUserId(Map<String, Object> map) {
        return this.getSqlSession().selectList(getSqlName("getAllEdaIdsByUserId"), map);
    }
    
    @Override
    public List<PubArea> getAreaIdByUserId(Integer userId){
        return this.getSqlSession().selectList(getSqlName("getAreaIdByUserId"),userId);
    }

    @Override
    public List<String> getSubAreaIdsByParAreaId(String areaId) {
        return this.getSqlSession().selectList(getSqlName("getSubAreaIdsByParAreaId"),areaId);
    }

//    @Override
//    public List<String> getEdaIdsByAreaId(String areaId) {
//        return this.getSqlSession().selectList(getSqlName("getEdaIdsByAreaId"),areaId);
//    }
    @Override
    public List<String> getEdaIdsByAreaId(Map<String, Object> map) {
        return this.getSqlSession().selectList(getSqlName("getEdaIdsByAreaId"),map);
    }

    @Override
    public List<ReplyHistory> getRelyHistoryByNoticeId(String recordId) {
        return this.getSqlSession().selectList(getSqlName("getRelyHistoryByNoticeId"),recordId);
    }

	@Override
	public Integer delOrRedNoticeByApp(Map<String, Object> map) {
		return this.getSqlSession().update("delOrRedNoticeByApp", map);
	}
    
}

