package com.wj.exception;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wj.vo.SysResult;

@ControllerAdvice
public class GolbalHandleException {
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public SysResult doHandleException(RuntimeException e) {
		e.printStackTrace();
		return SysResult.fail(e.getMessage());
	}
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public SysResult doHandleException(ShiroException e) {
		e.printStackTrace();
		String message = null;
		if (e instanceof IncorrectCredentialsException)
			message = "登陆密码错误";
		if (e instanceof UnauthorizedException)
			message = "没有此权限";
		if (e instanceof UnknownAccountException)
			message = "该用户不存在";
		if (e instanceof LockedAccountException)
			message = "该用户已被禁用";
		return SysResult.fail(message);
	}
}
