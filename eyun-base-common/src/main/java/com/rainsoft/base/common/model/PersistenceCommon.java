package com.rainsoft.base.common.model;

import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DateUtils;
import com.rainsoft.base.common.utils.SpringMvcUtil;

import java.util.Date;

/**
 * 功能说明：基础业务对象（必要的公共属性、逻辑删除数据的统一配置）
 * 
 * @author admin
 * @created 2014年6月12日 下午3:10:07
 * @updated
 */
public abstract class PersistenceCommon extends IdEntity {

	/** 信息录入者标识id */
	protected String creator;

	/** 更新人标识id */
	protected String updator;

	/** 创建时间 */
	protected Date createTime;

	/** 更新时间 */
	protected Date updateTime;

	/** 备注 */
	protected String remark;

	/** 状态 */
	protected String status;

	public String getRemark() {

		return remark;

	}

	public void setRemark(String remark) {

		this.remark = remark;

	}

	public String getCreator() {

		return SpringMvcUtil.getUserId().toString();

	}

	public void setCreator(String creator) {

		this.creator = creator;

	}

	public String getUpdator() {

		return updator;

	}

	public void setUpdator(String updator) {

		this.updator = updator;

	}

	public Date getCreateTime() {

		return createTime;

	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;

	}

	public Date getUpdateTime() {

		return updateTime;

	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;

	}

	public String getStatus() {

		return status;

	}

	/**
	 * 
	 * 方法功能说明：获取字符串更新时间
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年12月9日 下午5:30:21
	 * @return
	 */
	public String getUpdateTimeStr() {
		return DateUtils.dateToString(this.updateTime, Constants.DATA_FORMAT_HHMMSS);
	}
	
	/**
	 * 
	 * @Description: 获取date更新时间
	 * @param @param updateTimeStr   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2015年12月18日下午4:40:43
	 */
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTime = DateUtils.parseDate(updateTimeStr, Constants.DATA_FORMAT_HHMMSS);
	}

	/**
	 * 
	 * 方法功能说明：获取字符串创建时间
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年12月9日 下午5:31:18
	 * @return
	 */
	public String getCreateTimeStr() {
		return DateUtils.dateToString(this.createTime, Constants.DATA_FORMAT_HHMMSS);
	}
	
	/**
	 * 
	 * @Description: 获取date创建时间
	 * @param @param createTimeStr   
	 * @return void  
	 * @throws
	 * @author yty
	 * @date 2016年2月2日下午2:35:54
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTime = DateUtils.parseDate(createTimeStr, Constants.DATA_FORMAT_HHMMSS);
	}

	/**
	 * 
	 * 方法功能说明：状态设值
	 * 根据传入字段长度及字节数，判断status赋值
	 * 当传入值长度、字节数大于1，则根据文字赋对应的Code值；
	 * 反之，则根据code赋对应的value值
	 * 
	 * @author sh_j_baoxu
	 * @data 2015年12月7日 下午8:20:35
	 * @param status
	 */
	public void setStatus(String status) {
		if (status.length() > 1 && status.getBytes().length > 1) {
			this.status = ModelStatusEnum.getCode(status);
		} else {
			this.status = ModelStatusEnum.getValue(status);
		}
	}
}