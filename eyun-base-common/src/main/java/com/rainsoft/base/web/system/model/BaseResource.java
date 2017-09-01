package com.rainsoft.base.web.system.model;

import java.util.List;

import com.rainsoft.base.common.model.PersistenceCommon;

public class BaseResource extends PersistenceCommon{
	private String name;				/**资源名称*/
	private String type;				/**资源类型*/
	private int pid;					/**上级菜单Id*/
	private int sort;					/**排序*/
	private String url;					/**资源url*/
	private String icon;				/**资源图片*/
	private String creatorName;			/**创建人*/
	private String updatorName;			/**更新人*/
	private String preMenu;				/**上级菜单*/
	private Integer menuLevel;			/**菜单等级*/
	private List<BaseResource> subResource;	/**子资源*/
	private String code;				/**资源code*/
	private String privilege;			/**资源权限*/
	public static List<BaseResource> allResource = null;

	public static List<BaseResource> getAllResource() {
		return allResource;
	}
	public static void setAllResource(List<BaseResource> allResource) {
		BaseResource.allResource = allResource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getUpdatorName() {
		return updatorName;
	}
	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}
	public String getPreMenu() {
		return preMenu;
	}
	public void setPreMenu(String preMenu) {
		this.preMenu = preMenu;
	}
	public Integer getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}
	public List<BaseResource> getSubResource() {
		return subResource;
	}
	public void setSubResource(List<BaseResource> subResource) {
		this.subResource = subResource;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	/**
	 * 
	 * @Description: type转为中文
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author yty
	 * @date 2015年12月16日下午7:24:19
	 */
	public String getTypeName() {
		if("0".equals(this.type)){
			return "url";
		}
		return "url";
	}
}