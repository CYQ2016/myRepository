package com.db.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		if (pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码不符合规定");
		int pageSize = 5;
		int rowCount = sysLogDao.getRowCount(username);
		if (rowCount==0)
			throw new ServiceException("查询记录不存在");
		int pageCount = (rowCount-1)/pageSize+1;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysLog> list = sysLogDao.findPageObjects(username, startIndex, pageSize);
		return PageUtil.newInstance(pageCurrent, pageSize, rowCount, pageCount, list);
	}
	@RequiresPermissions("sys:log:delete")
	@Override
	public int deleteObjects(Integer... ids) {
		if (ids==null||ids.length==0)
			throw new ServiceException("请先选择");
		int rows = 0;
		try {
			rows = sysLogDao.deleteObjects(ids);
		}catch (Throwable e) {
			e.printStackTrace();
			throw new ServiceException("系统维护中。。。");
		}
		if (rows==0)
			throw new ServiceException("查询记录不存在");
		return rows;
	}

}
