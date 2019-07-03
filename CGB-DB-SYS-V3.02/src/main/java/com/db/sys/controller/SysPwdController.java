package com.db.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pwd/")
public class SysPwdController {
	@RequestMapping("doPwdEditUI")
	public String doPwdEditUI() {
		return "sys/pwd_edit";
	}
}
