package com.db.common.util;

import org.apache.shiro.SecurityUtils;


public abstract class ShiroUtil {
	@SuppressWarnings("unchecked")
	public static <T>T getUser(Class<T> cls) {
		return (T)SecurityUtils.getSubject().getPrincipal();
	}
}