package com.db.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.CheckBox;
import com.db.common.vo.JsonResult;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;

@Controller
@RequestMapping("role")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}
	@RequestMapping("doRoleEditUI")
	public String doRoleEditUI() {
		return "sys/role_edit";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name,Integer pageCurrent) {
		PageObject<SysRole> pageObject = sysRoleService.findPageObjects(name, pageCurrent);
		return new JsonResult(pageObject);
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole sysRole,Integer[] menuIds) {
		sysRoleService.insertObject(sysRole, menuIds);
		return new JsonResult("insert ok");
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		SysRoleMenuVo result = sysRoleService.findObjectById(id);
		return new JsonResult(result);
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole sysRole,Integer[] menuIds) {
		sysRoleService.updateObject(sysRole, menuIds);
		return new JsonResult("update ok");
	}
	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindObjects() {
		List<CheckBox> list = sysRoleService.findObjects();
		return new JsonResult(list);
	}
	@RequestMapping("doFindObjectByName")
	@ResponseBody
	public JsonResult doFindObjectByName(String name) {
		System.out.println(name);
		int count = sysRoleService.findObjectByName(name);
		System.out.println(count);
		return new JsonResult(count);
	}
}
