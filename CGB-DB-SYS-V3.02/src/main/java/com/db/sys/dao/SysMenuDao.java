package com.db.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

public interface SysMenuDao {
	List<Map<String, Object>> findObjects();
	int deleteObject(Integer id);
	int getChildObject(Integer id);
	List<Node> findZtreeMenuNodes();
	int insertObject(SysMenu sysMenu);
	int updateObject(SysMenu sysMenu);
	List<String> findPremssionsById(
			@Param("ids")Integer...ids);
}
