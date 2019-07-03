package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleDao {
	int getRowCount(@Param("username") String name);
	List<SysRole> findPageObjects(
			@Param("username")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	int deleteObject(Integer id);
	int insertObject(SysRole sysRole);
	int updateObject(SysRole sysRole);
	SysRoleMenuVo findObjectById(Integer id);
	List<CheckBox> findObjects();
	int findObjectByName(String name);
}
