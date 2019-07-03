package com.db.sys.service;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.db.sys.entity.SysLog;

@Service
public interface SysLogService extends PageService<SysLog>{

	int deleteObjects(@Param("ids")Integer...ids);
}
