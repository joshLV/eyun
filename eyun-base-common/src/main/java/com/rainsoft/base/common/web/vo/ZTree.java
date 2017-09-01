package com.rainsoft.base.common.web.vo;

/**
 * 功能说明：封装的ztree属性节点
 * 
 * @author admin
 * @created 2014年6月14日 下午12:38:10
 * @updated
 */
public class ZTree {

	/** 节点名称 */
	private String name;
	/** 节点链接的目标 URL */
	private String url;
	/** 节点的checkBox/radio的勾选状态 */
	private boolean checked;
	/** 设置节点的checkBox/radio是否禁用 */
	private boolean chkDisabled;
	/** 节点自定义图标的URL路径 */
	private String icon;
	/** 父节点自定义折展时图标的URL路径 */
	private String iconClose;
	/** 父节点自定义展开时图标的URL路径 */
	private String iconOpen;
	/** 记录treeNode节点是否为父节点 */
	private boolean isParent;
	/** 记录节点的展开/折叠状态 */
	private boolean open;
	/** 设置点击节点后在何处打开URL同超链接 target 属性: "_blank", "_self" 或 其他指定窗口名称，默认——blank */
	private String target;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getUrl() {

		return url;

	}

	public void setUrl(String url) {

		this.url = url;

	}

	public boolean isChecked() {

		return checked;

	}

	public void setChecked(boolean checked) {

		this.checked = checked;

	}

	public boolean isChkDisabled() {

		return chkDisabled;

	}

	public void setChkDisabled(boolean chkDisabled) {

		this.chkDisabled = chkDisabled;

	}

	public String getIcon() {

		return icon;

	}

	public void setIcon(String icon) {

		this.icon = icon;

	}

	public String getIconClose() {

		return iconClose;

	}

	public void setIconClose(String iconClose) {

		this.iconClose = iconClose;

	}

	public String getIconOpen() {

		return iconOpen;

	}

	public void setIconOpen(String iconOpen) {

		this.iconOpen = iconOpen;

	}

	public boolean isParent() {

		return isParent;

	}

	public void setParent(boolean isParent) {

		this.isParent = isParent;

	}

	public boolean isOpen() {

		return open;

	}

	public void setOpen(boolean open) {

		this.open = open;

	}

	public String getTarget() {

		return target;

	}

	public void setTarget(String target) {

		this.target = target;

	}

}