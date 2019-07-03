package com.wj.vo;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer rowCount;
	private Integer pageCount;
	private Integer pageSize;
	private Integer pageCurrent;
	private List<T> records;
}
