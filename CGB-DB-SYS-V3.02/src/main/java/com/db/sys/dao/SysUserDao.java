package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserDao {
	int getRowCount(@Param("username") String username);
	List<SysUserDeptVo> findPageObjects(
			@Param("username") String username,
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);
	int insertObject(SysUser sysUser);
	SysUserDeptVo findObjectById(Integer id);
	int updateObject(SysUser sysUser);
	SysUser findUserByUserName(String username);
	int searchPwdByUsername(@Param("username")String username,@Param("password")String password);
	int updatePwdByUsername(@Param("username")String username,@Param("password")String password,@Param("salt")String salt);
}
