package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
	int deleteObjectsByRoleId(Integer roleId);
	int insertObjects(
			@Param("userId")Integer userId,
			@Param("roleIds")Integer[] roleIds);
	List<Integer> findRoleIdsByUserId(
			@Param("userId")Integer userId);
	int deleteObjectsByUserId(Integer userId);
}
