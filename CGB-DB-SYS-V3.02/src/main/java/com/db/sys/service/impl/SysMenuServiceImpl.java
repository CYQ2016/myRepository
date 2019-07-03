package com.db.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.vo.Node;
import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.entity.SysMenu;
import com.db.sys.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if (list==null||list.size()==0)
			throw new ServiceException("未查询到菜单");
		return list;
	}
	@Override
	public int deleteObject(Integer id) {
		if (id==null||id<1)
			throw new IllegalArgumentException("页码不符合规定");
		int childs = sysMenuDao.getChildObject(id);
		if (childs!=0)
			throw new ServiceException("有子菜单未删除，删除失败");
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		int rows = sysMenuDao.deleteObject(id);
		if (rows==0)
			throw new ServiceException("记录可能已经不存在了");
		return rows;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		if (list==null||list.size()==0)
			throw new ServiceException("没有菜单信息");
		return list;
	}
	@Override
	public int insertObject(SysMenu sysMenu) {
		if (sysMenu==null)
			throw new ServiceException("保存对象不能为空");
		if (StringUtils.isEmpty(sysMenu.getName()))
			throw new ServiceException("姓名不能为空");
		int rows ;
		try {
			rows = sysMenuDao.insertObject(sysMenu);
		}catch(ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("添加失败");
		}
		return rows;
	}
	@Override
	public int updateObject(SysMenu sysMenu) {
		if (sysMenu==null)
			throw new ServiceException("保存对象不能为空");
		if (StringUtils.isEmpty(sysMenu.getName()))
			throw new ServiceException("姓名不能为空");
		int rows ;
		try {
			rows = sysMenuDao.updateObject(sysMenu);
		}catch(ServiceException e) {
			e.printStackTrace();
			throw new ServiceException("添加失败");
		}
		return rows;
	}

}
