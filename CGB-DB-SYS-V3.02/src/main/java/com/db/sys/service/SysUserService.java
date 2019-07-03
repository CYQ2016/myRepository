package com.db.sys.service;


import java.util.Map;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserService extends PageService<SysUserDeptVo>{
	int validById(Integer id,
			Integer valid,
			String modifiedUser);
	int insertObject(SysUser sysUser,Integer[] roleIds);
	Map<String,Object> findObjectById(Integer id);
	int updateObject(SysUser sysUser,Integer[] roleIds);
	int updatePwd(String pwd,String newPwd,String cfgPwd);
}
