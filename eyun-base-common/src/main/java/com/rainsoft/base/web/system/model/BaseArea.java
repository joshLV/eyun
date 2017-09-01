package com.rainsoft.base.web.system.model;

import com.rainsoft.base.common.model.IdEntity;

/**
 * 地区实体类
 * 
 * @author Administrator
 * 
 */
public class BaseArea extends IdEntity {
	

	/** 区域名称 */
	private String name;
	/** 父ID */
	private String pId;
	/** */
	private String treeLevel;
	/** */
	private String nodePath;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getTreeLevel() {
		return treeLevel;
	}
	public void setTreeLevel(String treeLevel) {
		this.treeLevel = treeLevel;
	}
	public String getNodePath() {
		return nodePath;
	}
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	
	
	

}
