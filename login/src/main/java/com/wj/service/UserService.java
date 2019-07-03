package com.wj.service;

import com.wj.pojo.User;
import com.wj.vo.PageVo;

public interface UserService {

	PageVo<User> doFindPageObjects(String username, Integer pageCurrent);

	void doValidById(Integer id, Integer valid);

	void doSaveObject(User user);

	User doFindObjectById(Integer id);

	void doUpdateObject(User user);

	void doFindUserByPhone(String phone);

	void doResetPwd(String phone, String newPwd, String cfgPwd);

	void doUpdatePassword(String pwd, String newPwd, String cfgPwd);

}
