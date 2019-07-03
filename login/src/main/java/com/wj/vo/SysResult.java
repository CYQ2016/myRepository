package com.wj.vo;


import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer state;
	private String message;
	private Object data;
	private SysResult(Integer state, String message, Object data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}
	public static SysResult ok() {
		return new SysResult(1, "操作成功", null);
	}
	public static SysResult ok(String message, Object data) {
		return new SysResult(1, message, data);
	}
	public static SysResult ok(Object data) {
		return new SysResult(1, "操作成功", data);
	}
	public static SysResult fail() {
		return new SysResult(0, "操作失败", null);
	}
	public static SysResult fail(String message) {
		return new SysResult(0, message, null);
	}

}
