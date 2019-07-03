package com.db.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandle {
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();
		return new JsonResult(e);
	}
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
		logger.error("shiro"+e.getMessage());
		e.printStackTrace();
		JsonResult js = new JsonResult();
		js.setState(0);
		if (e instanceof UnknownAccountException) {
			js.setMessage("用户名不存在");
		}else if (e instanceof LockedAccountException) {
			js.setMessage("用户已被禁用");
		}else if (e instanceof IncorrectCredentialsException) {
			js.setMessage("密码不正确");
		}else if (e instanceof AuthorizationException) {
			js.setMessage("没有操作权限");
		}
		return js;
	}
}
