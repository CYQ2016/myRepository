package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysLog;

public interface SysLogDao {
	int getRowCount(@Param("username")String username);
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	int deleteObjects(@Param("ids")Integer...ids);
}
