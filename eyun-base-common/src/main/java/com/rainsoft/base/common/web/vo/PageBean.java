package com.rainsoft.base.common.web.vo;

import java.util.ArrayList;
import java.util.List;

public class PageBean {
	/** 每页大小 */
	private int pageSize = 10;// Records per page;
	/** 总记录数 */
	private long total = 0;// Total record;
	/** 当前页 */
	private int currPage = 1;// The page num which is loaded from,one based;
	/** 总页数 */
	private long pageCount = 0;
	/** 数据List */
	private List<String> pageList;
	/** 排序字段 */
	private String sort;

	/** 排序顺序（asc,desc） */
	private String order;

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getStart() {
		int startNum = Long.valueOf(this.getPageCount()).intValue();
		if (currPage > startNum) {
			currPage = startNum;
		}
		return (currPage - 1) * pageSize;
	}

	public int getCurrPage() {
		if (currPage <= 0) {
			currPage = 1;
		}
		return currPage;
	}

	public long getPageCount() {
		if (total % pageSize == 0) {
			pageCount = total / pageSize;
		} else {
			pageCount = total / pageSize + 1;
		}
		return pageCount;
	}

	public List<String> getPageList() {
		pageList = new ArrayList<String>();
		for (int i = 1; i <= this.pageCount; i++) {
			pageList.add(String.valueOf(i));
		}
		return pageList;
	}
}