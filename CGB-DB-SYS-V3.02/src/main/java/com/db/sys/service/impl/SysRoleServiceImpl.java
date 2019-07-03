package com.db.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;
@Service
public class SysRoleServiceImpl implements SysRoleService{
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		if (pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码不符合规定");
		int rows = sysRoleDao.getRowCount(name);
		if (rows==0)
			throw new ServiceException("查询记录不存在");
		int pageSize = 5;
		int startIndex = (pageCurrent-1)*pageSize;
		int pageCount = (rows-1)/pageSize+1;
		List<SysRole> list = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		return PageUtil.newInstance(pageCurrent, pageSize, rows, pageCount, list);
	}
	@Override
	public int deleteObject(Integer id) {
		if (id==null||id<0)
			throw new IllegalArgumentException("id值不符合规定");
		int rows = 0;
		try {
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		rows = sysRoleDao.deleteObject(id);
		}catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		if (rows==0)
			throw new ServiceException("记录可能已经不存在了");
		return rows;
	}
	@Override
	public int insertObject(SysRole sysRole, Integer[] menuIds) {
		if (sysRole==null)
			throw new IllegalArgumentException("添加对象不能为空");
		if (StringUtils.isEmpty(sysRole.getName()))
			throw new IllegalArgumentException("角色姓名不能为空");
		int count = sysRoleDao.findObjectByName(sysRole.getName());
		if (count!=0)
			throw new ServiceException("角色已存在");
		if (menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("角色菜单不能为空");
		int rows = 0;
		try {
			rows = sysRoleDao.insertObject(sysRole);
			sysRoleMenuDao.insertObjects(sysRole.getId(), menuIds);
		}catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("添加失败");
		}
		if (rows==0)
			throw new ServiceException("系统维护中，请稍后再试");
		return rows;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		if (id==null||id<0)
			throw new IllegalArgumentException("id值不符合规定");
		SysRoleMenuVo result = sysRoleDao.findObjectById(id);
		if (result==null)
			throw new ServiceException("此记录已经不存在");
		return result;
	}
	@Override
	public int updateObject(SysRole sysRole, Integer[] menuIds) {
		if (sysRole==null)
			throw new IllegalArgumentException("添加对象不能为空");
		if (StringUtils.isEmpty(sysRole.getName()))
			throw new IllegalArgumentException("角色姓名不能为空");
		if (menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("角色菜单不能为空");
		int rows = 0;
		try {
			sysRoleMenuDao.deleteObjectsByRoleId(sysRole.getId());
			rows = sysRoleDao.updateObject(sysRole);
			sysRoleMenuDao.insertObjects(sysRole.getId(), menuIds);
		}catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("修改失败");
		}
		if (rows==0)
			throw new ServiceException("系统维护中，请稍后再试");
		return rows;
	}
	@Override
	public List<CheckBox> findObjects() {
		List<CheckBox> list = sysRoleDao.findObjects();
		if (list==null||list.isEmpty())
			throw new ServiceException("系统没有角色信息");
		return list;
	}
	@Override
	public int findObjectByName(String name) {
		int count = sysRoleDao.findObjectByName(name);
		System.out.println("service"+count);
		return count;
	}	
}
