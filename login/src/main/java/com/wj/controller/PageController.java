package com.wj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wj.vo.SysResult;

@Controller
public class PageController {
	@RequestMapping("/doLoginUI")
	public String hello() {
		return "login";
	}
	@RequestMapping("/doReset")
	public String reset() {
		return "reset";
	}
	@RequestMapping("/doIndexUI")
	public String start() {
		return "index";
	}
	@RequestMapping("/doUserList")
	public String doUserList() {
		return "user_list";
	}
	@RequestMapping("/doPageUI")
	public String doPageUI() {
		return "page";
	}
	@RequestMapping("/doUserEditUI")
	public String doUserEditUI() {
		return "user_edit";
	}
	@RequestMapping("/doUpdatePwd")
	public String doUpdatePwd() {
		return "pwd_edit";
	}
}
