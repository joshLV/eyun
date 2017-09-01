package com.rainsoft.base.common.web.vo;

import java.io.Serializable;

import com.rainsoft.base.common.utils.Constants;

public class PagedListResult implements Serializable{
	
	private static final long serialVersionUID = 3490682086509665295L;
	
	private String status;
	private String message;
	private String currentPage;//当前页
	private String totalPage;//总页码
	private Object data;

	public PagedListResult() {
		super();
	}
	
    /**
	 * @description
	 * @param string
	 *            状态
	 * @param message
	 *            消息
	 */
	public PagedListResult(String string, String message, Object data) {
		this.status = string;
		this.message = message;
		this.data = data;
	}

	/**
	 * 功能说明：返回结果类型
	 * 
	 * @author admin
	 * @created 2014年7月8日 下午3:48:58
	 * @updated
	 */
	/*public enum String {
		SUCC, ERROR, NOLOGIN, OTHER,SUCCESS,WORNGPARAM,NOPERMISSION,UNAUTHORISED,OK
	}*/

	/**
	 * 功能说明：添加成功信息
	 * 
	 * @author admin
	 * @created 2014年7月8日 下午3:48:07
	 * @updated
	 * @param message
	 *            提示信息
	 */
	public void addSucc(String message) {
		this.message = message;
		this.status = Constants.RETURN_SUCCESS;
	}

	/**
	 * 添加错误消息
	 * 
	 * @param message
	 */
	public void addError(String message) {
		this.message = message;
		this.status = Constants.RETURN_ERROR;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


	public String getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}


	public String getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

}
