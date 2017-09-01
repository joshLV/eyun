package com.rainsoft.base.web.system.model;

import java.util.Date;

import com.rainsoft.base.common.model.PersistenceCommon;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DateUtils;

public class LogOptRecord extends PersistenceCommon {
	private String optModule;			/** 操作模块*/
	private String optAction;			/** 操作行为*/
	private String ip;					/** ip*/
	private Date startTime;				/** 开始时间*/
	private Date endTime;				/** 结束时间*/
	private String placeName;			/**场所名称*/
	private String optorName;			/**操作人姓名*/

	public String getOptModule() {
		return optModule;
	}
	public void setOptModule(String optModule) {
		this.optModule = optModule;
	}
	public String getOptAction() {
		return optAction;
	}
	public void setOptAction(String optAction) {
		this.optAction = optAction;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getOptorName() {
		return optorName;
	}
	public void setOptorName(String optorName) {
		this.optorName = optorName;
	}

	/**
	 * 
	 * @Description: 获取开始时间字符串
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2015年12月15日上午11:43:05
	 */
	public String getStartTimeStr() {
		return DateUtils.dateToString(this.startTime, Constants.DATA_FORMAT_HHMMSS);
	}
	
	/**
	 * 
	 * @Description: 获取结束时间字符串
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2015年12月15日上午11:43:24
	 */
	public String getEndTimeStr() {
		return DateUtils.dateToString(this.endTime, Constants.DATA_FORMAT_HHMMSS);
	}
}
