package com.db.common.util;

import java.util.List;

import com.db.common.vo.PageObject;

public abstract class PageUtil {
	public static <T>PageObject<T> newInstance(Integer pageCurrent, int pageSize, int rowCount, int pageCount,
			List<T> list) {
		PageObject<T> pageObject = new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setPageCount(pageCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		return pageObject;
	}
}
