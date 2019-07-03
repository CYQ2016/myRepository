package com.db.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.util.ShiroUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.SysUserDeptVo;
@Service
public class SysUserServiceImpl implements SysUserService {
	private Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		if (pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值不符合规定");
		int rows = sysUserDao.getRowCount(username);
		if (rows==0)
			throw new ServiceException("未查询到用户信息");
		int pageSize = 5;
		int startIndex = (pageCurrent-1)*pageSize;
		int pageCount = (rows-1)/pageSize+1;
		List<SysUserDeptVo> list = sysUserDao.findPageObjects(username, startIndex, pageSize);
		PageObject<SysUserDeptVo> pageObject = PageUtil.newInstance(pageCurrent, pageSize, rows, pageCount, list);
		return pageObject;
	}
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		System.out.println(id+","+valid);
		if (id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		if (valid==null||(valid!=0&&valid!=1))
			throw new IllegalArgumentException("valid值无效");
		int rows = sysUserDao.validById(id, valid, modifiedUser);
		if (rows==0)
			throw new ServiceException("该用户可能已经不存在了");
		return rows;
	}
	@Override
	public int insertObject(SysUser sysUser,Integer[] roleIds) {
		if (sysUser==null)
			throw new IllegalArgumentException("添加用户不能为空");
		if (StringUtils.isEmpty(sysUser.getUsername()))
			throw new IllegalArgumentException("用户姓名不能为空");
		if (sysUser.getPassword()==null||sysUser.getPassword()=="")
			throw new IllegalArgumentException("用户密码不能为空");
		if (sysUser.getDeptId()==null)
			throw new IllegalArgumentException("请为用户添加部门");
		if (roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("请为用户分配角色");
		//对密码进行加密
		String salt = UUID.randomUUID().toString();
		SimpleHash sh = new SimpleHash("MD5", sysUser.getPassword(), salt, 1);
		sysUser.setPassword(sh.toHex());
		sysUser.setSalt(salt);
		int rows = sysUserDao.insertObject(sysUser);	
		if (rows==0)
			throw new ServiceException("添加失败");
		sysUserRoleDao.insertObjects(sysUser.getId(), roleIds);
		return rows;
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		if (id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		SysUserDeptVo user = sysUserDao.findObjectById(id);
		if (user==null)
			throw new ServiceException("未查询到用户信息");
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	@Override
	public int updateObject(SysUser sysUser, Integer[] roleIds) {
		if (sysUser==null)
			throw new IllegalArgumentException("添加用户不能为空");
		if (StringUtils.isEmpty(sysUser.getUsername()))
			throw new IllegalArgumentException("用户姓名不能为空");
		if (sysUser.getDeptId()==null)
			throw new IllegalArgumentException("请为用户添加部门");
		if (roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("请为用户分配角色");
		
		int rows = sysUserDao.updateObject(sysUser);	
		if (rows==0)
			throw new ServiceException("记录可能已经不存在了");
		sysUserRoleDao.deleteObjectsByUserId(sysUser.getId());
		sysUserRoleDao.insertObjects(sysUser.getId(), roleIds);
		return rows;
	}
	@Override
	public int updatePwd(String pwd, String newPwd, String cfgPwd) {
		logger.debug(pwd);
		logger.debug(newPwd);
		logger.debug(cfgPwd);
		if (pwd==null||pwd=="")
			throw new IllegalArgumentException("请输入原密码");
		if (newPwd==null||newPwd=="")
			throw new IllegalArgumentException("请输入新密码");
		if (cfgPwd==null||cfgPwd=="")
			throw new IllegalArgumentException("请输入确认密码");
		if (!newPwd.equals(cfgPwd))
			throw new ServiceException("确认密码需与新密码保持一致");
		if (newPwd.equals(pwd))
			throw new ServiceException("新密码不能与原密码一致");
		SysUser user = ShiroUtil.getUser(SysUser.class);
		logger.error(user.getPassword());
		String username = user.getUsername();
		String salt = user.getSalt();
		SimpleHash sh = new SimpleHash("MD5", pwd, salt, 1);
		String password = sh.toHex();
		logger.error(password);
		int rows = sysUserDao.searchPwdByUsername(username, password);
		if (rows==0)
			throw new ServiceException("原密码不正确");
		salt = UUID.randomUUID().toString();
		sh = new SimpleHash("MD5", newPwd, salt, 1);
		password = sh.toHex();
		logger.error(password);
		rows = sysUserDao.updatePwdByUsername(username, password,salt);
		if (rows==0)
			throw new ServiceException("修改失败");
		return rows;
	}
}
