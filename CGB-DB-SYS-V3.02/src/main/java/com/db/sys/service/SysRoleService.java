package com.db.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleService extends PageService<SysRole>{
	int deleteObject(Integer id);
	int insertObject(SysRole sysRole,@Param("menuIds")Integer[] menuIds);
	SysRoleMenuVo findObjectById(Integer id);
	int updateObject(SysRole sysRole,@Param("menuIds")Integer[] menuIds);
	List<CheckBox> findObjects();
	int findObjectByName(String name);
}
