package com.rainsoft.riplat.web.notice.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.rainsoft.base.common.dao.IMybatisPersitenceDao;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.service.impl.MybatisBasePersitenceServiceImpl;
import com.rainsoft.base.common.utils.CommonUtil;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DateUtils;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.riplat.web.mgrparam.service.IPlatformService;
import com.rainsoft.riplat.web.notice.dao.INoticeDao;
import com.rainsoft.riplat.web.notice.model.AccpeteruserFeedback;
import com.rainsoft.riplat.web.notice.model.Notice;
import com.rainsoft.riplat.web.notice.model.Notice4App;
import com.rainsoft.riplat.web.notice.model.NoticeUnit;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.model.Unit;
import com.rainsoft.riplat.web.notice.service.INoticeService;
import com.rainsoft.riplat.web.notice.service.IPlatformUserService;

@Service
public class NoticeServiceImpl extends MybatisBasePersitenceServiceImpl<Notice, String> implements INoticeService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	private static final int PAGESIZE = 20;//默认的页记录数
	private static final String URLSPLICE = "NoticeService/noticeDetail.do?recordId=";//减少创建对象次数
    private static final String SUCCESS = Constants.RETURN_SUCCESS_INTERFACE;
    private static final String ERROR = Constants.RETURN_ERROR_INTERFACE;
    private static final String folderName = "upload";
    
    @Resource
    private INoticeDao noticeDaoImpl;
    
    @Resource
    private IPlatformService platformServiceImpl;

    @Resource
    private IPlatformUserService platformUserServiceImpl;

    
    @Override
    protected IMybatisPersitenceDao<Notice, String> getBaseDao() {
        return noticeDaoImpl;
    }    
    
    @Override
    public Integer deleteById(String id) {
        if(StringUtil.isNotEmpty(id)){
            noticeDaoImpl.deleteNoticeUnitById(Integer.valueOf(id));
        }
        return super.deleteById(id);
    }

    /**
     * 验证平台ID是否存在切已经激活
     * @param platformId
     * @return{
     *     是否能被激活；
     *      0：有效的License；
     *      1：已被平台激活，不能再次激活';
     * }
     */
    public boolean validatePlatformId(String platformId){
        String mapValue = Constants.PLATFORM_MAP.get(platformId);
        if (StringUtil.isNotEmpty(mapValue) && Constants.DISABLE.equals(mapValue)) {
            return true;
        }
        return false;
    }
    
    /**
     * 验证导入文件中的key文件是否还有效
     */
    public boolean validateEnablePlatformId(String platformId) {
        String mapValue = Constants.PLATFORM_MAP.get(platformId);
        if (StringUtil.isNotEmpty(mapValue) && Constants.ENABLE.equals(mapValue)) {
            return true;
        }
        return false;
    }       

    /**
     * 导入key文件后将使用后的platformId设置失效
     */
    public boolean disablePlatformId(String platformId) {
        String mapValue = Constants.PLATFORM_MAP.get(platformId);
        if (StringUtil.isNotEmpty(mapValue) && Constants.ENABLE.equals(mapValue)) {
            if(Constants.PLATFORM_MAP.containsKey(platformId)){
                Constants.PLATFORM_MAP.put(platformId, Constants.DISABLE);                
            }
            platformServiceImpl.activeLicense(platformId);            
            return true;
        }
        return false;
    }
    
    /**
     * 移除被替换的PlatFormId
     */
    public boolean removePlatformId(String platformId) {
        if (validatePlatformId(platformId)) {
            Constants.PLATFORM_MAP.remove(platformId);//移除Map中的变量
            return true;
        }
        return false;
    }
    
    @Override
    public Notice getNoticeById(Notice notice) {
        if (0 == notice.getAccpeterType()) {
            return noticeDaoImpl.getUserNoticeById(notice.getId());
        } else {
            return noticeDaoImpl.getGroupNoticeById(notice.getId());
        }
    }
    
    
    @Override
    public Notice getNoticeByIdForWeb(String id) {
        return noticeDaoImpl.getNoticeById(id);
    }

    @Override
    public List<Notice> findListBy(Notice item, String sortColumn, String des) {
        if(!"1".equals(SpringMvcUtil.getUserId().toString())){//当为本系统用户的admin账户登录时，可以查询所有的通知信息
            item.setCreateBy(SpringMvcUtil.getUserId().toString());
        }
        return super.findListBy(item, sortColumn, des);
    }
    
    @Override
    public void saveSendNotice(String noticeId) {// 发送通知时插入数据到实际接收人表
        super.update("sendNotice", noticeId);// 修改通知的状态
        
        //获取所有组下面的易达ids。求并集 
        Set<String> idSet = this.getReceiveEdaIdsByNoticeId(noticeId);
        
        for (String id : idSet) {
            AccpeteruserFeedback accpeteruserFeedback = new AccpeteruserFeedback();
            accpeteruserFeedback.setNoticeId(String.valueOf(noticeId));
            accpeteruserFeedback.setAppuserId(id);//ID为用户ID
            noticeDaoImpl.saveAccpeteruserFeedback(accpeteruserFeedback);
        }
    }
    
    /**
     * 接收该通知的所有易达号
     * @param userId
     * @return
     */
//    private Set<String> getReceiveEdaIdsByNoticeIdOld(String noticeId){
//        
//        List<Unit> unitList = noticeDaoImpl.getNoticeUnitById(Integer.valueOf(noticeId));
//        Notice notice = super.findById(noticeId);
//        
//        //获取所有组下面的易达ids。求并集
//        
//        Set<String> idSet = new HashSet<String>();
//        
//        if (unitList.size() > 0) {
//            for (Unit unit : unitList) {//循环单位进行发送可能会存在重复发送的问题，需改造
//                List<String> userIds = null;
//                if (0 == notice.getAccpeterType()) {//用户组
//                    if("-1".equals(unit.getCode())){//如果选择全部用户,全部用户和全部场所实际上发送的对象都是一样
//                        //查询当前用户管辖区域内的所有用户进行发送
//                        List<String> allEdaSet = noticeDaoImpl.getAllEdaIdsByUserId(notice.getCreateBy());
//                        idSet.addAll(allEdaSet);
//                        break;
//                    }
//                    userIds = noticeDaoImpl.getEdaIdsByUserGroup(unit.getCode());//用户组,
//                }
//                if (1 == notice.getAccpeterType()) {// 场所组
//                    if ("-2".equals(unit.getCode())) {// 如果选择全部场所
//                        // 查询当前用户管辖区域内的所有场所进行发送
//                        List<String> allEdaSet = noticeDaoImpl.getAllEdaIdsByUserId(notice.getCreateBy());
//                        idSet.addAll(allEdaSet);
//                        break;
//                    }
//                    userIds = noticeDaoImpl.getEdaIdsByPlaceGroup(unit.getCode());//场所组
//                }
//                idSet.addAll(userIds);
//            }
//            idSet.remove(null);//防止存在Null对象
//        }
//        
//        return idSet;
//    }
    
	/**
	 * 接收该通知的所有易达号
	 * 
	 * @param userId
	 * @return
	 */
	private Set<String> getReceiveEdaIdsByNoticeId(String noticeId) {

		List<Unit> unitList = noticeDaoImpl.getNoticeUnitById(Integer.valueOf(noticeId));
		Notice notice = super.findById(noticeId);

		// 获取所有组下面的易达ids。求并集

		Set<String> idSet = new HashSet<String>();
		// 查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (unitList.size() > 0) {
			map.put("userId", notice.getCreateBy());
			map.put("list", unitList);
			map.put("sendTarget", notice.getSendTarget());
			List<String> userIds = null;
			//如果选择全部用户或全部场所,全部用户和全部场所实际上发送的对象都是一样
			if (unitList.size() == 1 && ("-1".equals(unitList.get(0).getCode()) || "-2".equals(unitList.get(0).getCode()))) {
				userIds = noticeDaoImpl.getAllEdaIdsByUserId(map);
			} else {
				if (0 == notice.getAccpeterType()) {
					//用户组
					userIds = noticeDaoImpl.getEdaIdsByUserGroup(map);
				} else if(1 == notice.getAccpeterType()) {
					//场所组
					userIds = noticeDaoImpl.getEdaIdsByPlaceGroup(map);
				} else if(2 == notice.getAccpeterType()) {
					//网吧
					if(notice.getSendTarget() == 2) {
						userIds = noticeDaoImpl.getEdaIdsByAreaId(map);
					}
					//公安
					if(notice.getSendTarget() == 3) {
						userIds = new ArrayList<String>();
						for(Unit unit: unitList) {
							if(unit.getCode().indexOf("-") < 0) {
								userIds.add(unit.getCode());
							}
						}
					}
				}
			}
			idSet.addAll(userIds);
			idSet.remove(null);//防止存在Null对象
		}
		return idSet;
	}
	
//	/**
//	 * 
//	 * @Description: 方法功能说明： 获取截取的区域id
//	 * @param areaId
//	 * @return
//	 * @return String
//	 * @author yty
//	 * @date 2016年8月31日下午5:57:09
//	 */
//	private String getSubAreaId(String areaId) {
//		String subAreaId = "";
//		if("0000".equals(areaId.substring(2, 6))) {
//			subAreaId = areaId.substring(0, 2);
//		} else if("00".equals(areaId.substring(4, 6))) {
//			subAreaId = areaId.substring(0, 4);
//		} else {
//			subAreaId = areaId;
//		}
//		return subAreaId;
//	}

    @Override
    public void saveOrUpdate(Notice notice,String userId) {
//        String userId = SpringMvcUtil.getUserId().toString();

        if ("0".equals(notice.getItem())) {// 页面点击发送按钮
            notice.setStatus(2);
            if (0==notice.getType()) {// 只有即时发送才填写发送时间
                notice.setSendTime(DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }
        } else {
            notice.setStatus(0);// 保存草稿
        }
        if (notice.getId() != null) {// 修改操作
            notice.setUpdateBy(userId);
            noticeDaoImpl.deleteNoticeUnitById(notice.getId());
            super.update(notice);
        } else {
            notice.setCreateBy(userId);
            super.save(notice);
        }
        saveNoticeDetail(notice, userId);//保存通知基本信息
        
        if("0".equals(notice.getItem())&&0==notice.getType()){//如果即时且页面点击发送则插入数据到接收人表中
            saveSendNotice(notice.getId().toString());
        }

        // 1.保存通知的基本信息
        // 2.更新通知与单位的关系
        // 3.如果是直接发送，则保存关联关系到接收信息表中
    }

    /**
     * 保存或者更新通知通告，适用Isec那边获取不到userId的情况
     * @param notice
     * @param jsonObj {loginUserName,platformId}
     */
    @Override
    public void saveOrUpdate(Notice notice, JSONObject jsonObj) {
        String userId = SpringMvcUtil.getUserId().toString(); //将这块放在外面
        //如果是ISEC那边新增通知通告，则不会又userId
        if(userId==null || CommonUtil.isEmpty(userId) || "0".equals(userId)){//userId不存在
            String loginName = jsonObj.getString("loginUsername");
            String password = jsonObj.getString("loginPassword");
            String platformId = jsonObj.getString("platformId");
            userId = platformUserServiceImpl.getUserIdByParam(platformId,loginName)+"";
        }
        saveOrUpdate(notice,userId);
    }

    /*
     * 保存上传的附件
     */
    public String storeFile(CommonsMultipartFile file) {
    	
    	/**
    	 * 修改为获取相对地址且按照用户、月份来进行分文件夹
    	 */
    	if (null == file) {
            return "";
        }
        String basePath = SpringMvcUtil.getRequest().getSession().getServletContext().getRealPath("");
        String fileName = file.getOriginalFilename();
        StringBuffer relativePath = new StringBuffer();
        relativePath.append(File.separatorChar).append(folderName).append(File.separatorChar).append(SpringMvcUtil.getUserId().toString()).append(File.separatorChar).append(DateUtils.dateToString(new Date(), "yyyyMMdd")).append(File.separatorChar);
        String targetFileName = System.nanoTime()+UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf(".")==-1?fileName.length():fileName.lastIndexOf("."));
        String targetPath = basePath+relativePath;//每个用户有单独的文件夹进行存放
        File targetFile = new File(targetPath,targetFileName); 
        if(!targetFile.exists()){
            targetFile.mkdirs();  
        }	         
        try {
            file.transferTo(targetFile);//拷贝文件到目标目录
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return relativePath + targetFileName;
    	
    }

    @Override
    public List<String> getNoticeTask(String quartzSendTime) {
        return noticeDaoImpl.getNoticeTask(quartzSendTime);
    }
    
    
    /**
     * 保存通知详细信息
     * @param notice
     * @param userId
     */
    private void saveNoticeDetail(Notice notice, String userId) {
        List<String> unitCodes = notice.getUnitCodes();
        if (CollectionUtils.isEmpty(unitCodes)) {
            return;
        }
        Integer accpeterType = notice.getAccpeterType();
        if (null == accpeterType) {
            throw new RuntimeException("accpeterType不能为空");
        }
        //当为发送给组的时候，存储通知与接收者的关系
            /** 通知接收方为组（场所组或用户组） */
            for (String unitCode : unitCodes) {
                NoticeUnit noticeUnit = new NoticeUnit();
                noticeUnit.setNoticeId(String.valueOf(notice.getId()));
                noticeUnit.setUnitCode(unitCode);
                noticeUnit.setCreateBy(userId);
                noticeDaoImpl.saveNoticeUnit(noticeUnit);
            }
        
    }
    
    /**
     * 易达用户获取通知列表
     * @param userId 易达id
     * @param pageSize 单页显示条数
     * @param lastRecordId 最后一条记录ID
     * @return
     */
	@SuppressWarnings("finally")
	public Result noticeList(String userId, int pageSize, String lastRecordId, String noticeLevel, String isDelete) {
		logger.info("调用webservice noticeList method");
		Result result = new Result();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<Notice4App> dataList = new ArrayList<Notice4App>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("noticeLevel", noticeLevel);
		map.put("isDelete", isDelete);
		map.put("noticeId", lastRecordId);
		try {
				if (pageSize < 1) {
					pageSize = PAGESIZE;
				}
				int startRow = -1;//默认给-1
				if(StringUtil.isEmptyString(lastRecordId)){//则认为是起始页进行查询
					PageHelper.startPage(1, pageSize);
				}else{//当该recordId不存在时则不返回任何新的信息
					startRow = noticeDaoImpl.getNoticeIdRowNum(map);//没有记录置为0
					PageHelper.startPageWithStartRowNum(pageSize,startRow);//当查询不到该条记录时返回0
				}
				if(startRow != 0){//只有当该条记录存在或Refresh操作时才进行查询
					dataList = noticeDaoImpl.getNoticeList(map);
					for(Notice4App notice:dataList){
						notice.setUrl(SpringMvcUtil.getContentPath()+URLSPLICE+notice.getId()+"&userId="+userId);
					}
					dataMap.put("dataList",dataList);
					int unReadCount = noticeDaoImpl.getUnReadCount(map);
					dataMap.put("unReadCount", unReadCount);
				}				
				
				result.setData(dataMap);
				result.setStatus(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ERROR);
			logger.error("调用webservice noticeList method 失败");
		}finally{
			return result;
		}
	}

	/**
	 * App端阅读通知信息为已读
	 * @param userId 易达Id
	 * @param recordId 记录Id
	 * @return
	 */
	@SuppressWarnings("finally")
	public Result readNotice(String userId, String recordId) {
		logger.info("调用webservice readNotice method");
		Result result = new Result();
		try {
			noticeDaoImpl.updateAccpterStatus(userId,recordId,1);
			result.setStatus(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ERROR);
			logger.error("调用webservice readNotice method 失败");
		} finally {
			return result;
		}
	}

	/**
	 * App端--易达用户通过App端回复通知通告
	 * @param userId 易达Id
	 * @param recordId 通知通告Id
	 * @param replyContent 回复内容
	 * @return
	 */
	public Result saveReplayNotice(String userId, String recordId, String replyContent,String userType) {
		logger.info("调用webservice replayNotice method");
		Result result = new Result();
		try {
		    //更改接收方的状态。
		    noticeDaoImpl.updateAccpterStatus(userId,recordId,2);
		    
			noticeDaoImpl.saveReplyNotice(userId,recordId,replyContent,userType);//保存实际回复的内容信息
			
			result.setStatus(SUCCESS);
		} catch (Exception e) {
		    logger.warn("回复信息失败");
			result.setStatus(ERROR);
			logger.error("调用webservice replayNotice method 失败");
			throw new RuntimeException("调用webservice replayNotice method 失败");
		}
		return result;
	}

	/**
	 * App端查看通知通告详情
     * web页面获取通知详情
	 * @param recordId 通知通告Id
	 * @return
	 */
	public Notice4App noticeDetail(String recordId) {
		logger.info("调用webservice readNotice method");
		Notice4App notice = null;
		try {
			notice = noticeDaoImpl.getEdaNoticeById(recordId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用webservice readNotice method 失败");
		} 
		return notice;
	}

	/**
	 * WEB 接口端获取单个详情信息
	 * @param noticeId
	 * @return
	 */
    public Notice getNoticeDetailInfoById(String noticeId) {
        Notice notice = null;
        try {
            notice = noticeDaoImpl.getNoticeDetailInfoById(noticeId);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return notice;
    }

    @Override
    public List<ReplyHistory> getRelyHistoryByNoticeId(String recordId) {
       return noticeDaoImpl.getRelyHistoryByNoticeId(recordId);
    }

	@Override
	public Result delOrRedNoticeByApp(Map<String, Object> map) {
		Result result = new Result();
		try {
			Integer num = noticeDaoImpl.delOrRedNoticeByApp(map);
			if(num != null && num > 0) {
				result.setStatus(SUCCESS);
			} else {
				result.setStatus(ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ERROR);
			logger.error("调用webservice delNoticeByApp method 失败");
		}
		return result;
	}
    
}