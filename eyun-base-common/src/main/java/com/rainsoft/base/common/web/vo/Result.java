package com.rainsoft.base.common.web.vo;

import com.rainsoft.base.common.utils.Constants;

import java.io.Serializable;

/**
 * 功能说明：Ajax 处理结果
 * 
 * @author admin
 * @created 2014年7月8日 下午3:45:14
 * @updated
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 474712281925184851L;
	private String status;
	private String message;
	private Object data;
	private int count;//查询请求为List时返回的总记录数,供接口端调用

	public Result() {
		super();
	}
	
	
	public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    /**
	 * @description
	 * @param string
	 *            状态
	 * @param message
	 *            消息
	 */
	public Result(String string, String message, Object data) {
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

}