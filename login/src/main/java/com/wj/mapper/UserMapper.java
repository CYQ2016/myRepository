package com.wj.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wj.pojo.User;

public interface UserMapper {

	int getCount(@Param("username")String username);

	List<User> doFindPageObjects(
			@Param("username")String username, 
			@Param("startIndex")int startIndex, 
			@Param("pageSize")Integer pageSize);

	void doValidById(
			@Param("id")Integer id, 
			@Param("valid")Integer valid);

	int doFindObject(@Param("key")String key, 
			@Param("value")String value);

	void doSaveObject(User user);

	User doFindObjectById(Integer id);

	void doUpdateObject(User user);

	User doFindObjectByMobile(@Param("mobile")String mobile);

	void doUpdateById(@Param("id")Integer id, @Param("newPwd")String newPwd, @Param("date")Date date);

	User doFindObjectByUsername(@Param("username")String username);

}
