package com.wj.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wj.pojo.User;
import com.wj.service.UserService;
import com.wj.vo.PageVo;
import com.wj.vo.SysResult;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public SysResult doFindPageObjects(String username, Integer pageCurrent) {
		PageVo<User> pageVo = userService.doFindPageObjects(username, pageCurrent);
		return SysResult.ok(pageVo);
	}
	@RequestMapping("doValidById")
	@ResponseBody
	public SysResult doValidById(Integer id, Integer valid) {
		userService.doValidById(id, valid);
		return SysResult.ok();
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public SysResult doSaveObject(User user) {
		userService.doSaveObject(user);
		return SysResult.ok();
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public SysResult doFindObjectById(Integer id) {
		User user = userService.doFindObjectById(id);
		return SysResult.ok(user);
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public SysResult doUpdateObject(User user) {
		userService.doUpdateObject(user);
		return SysResult.ok();
	}
	@RequestMapping("doFindUserByPhone")
	@ResponseBody
	public SysResult doFindUserByPhone(String phone) {
		userService.doFindUserByPhone(phone);
		return SysResult.ok();
	}
	@RequestMapping("doResetPwd")
	@ResponseBody
	public SysResult doResetPwd(String phone, String newPwd, String cfgPwd) {
		userService.doResetPwd(phone, newPwd, cfgPwd);
		return SysResult.ok();
	}
	@RequestMapping("doLogin")
	@ResponseBody
	public SysResult doLogin(String username,String password,boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		return SysResult.ok();
	}
	@RequestMapping("doUpdatePassword")
	@ResponseBody
	public SysResult doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
		userService.doUpdatePassword(pwd, newPwd, cfgPwd);
		return SysResult.ok();
	}
	@RequestMapping("doFindUsername")
	@ResponseBody
	public SysResult doFindUsername() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return SysResult.ok(user);
	}
}
