package com.db.common.vo;

import java.io.Serializable;
import java.util.List;

public class PageObject<T> implements Serializable{
	private static final long serialVersionUID = -973103533350138669L;
	private Integer pageCurrent;
	private Integer rowCount;
	private Integer pageCount;
	private Integer pageSize;
	private List<T> records;
	public Integer getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	@Override
	public String toString() {
		return "PageObject [pageCurrent=" + pageCurrent + ", rowCount=" + rowCount + ", pageCount=" + pageCount
				+ ", pageSize=" + pageSize + ", records=" + records + "]";
	}	
}
