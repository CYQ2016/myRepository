package com.wj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wj.exception.ServiceException;
import com.wj.mapper.UserMapper;
import com.wj.pojo.User;
import com.wj.service.UserService;
import com.wj.vo.PageVo;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public PageVo<User> doFindPageObjects(String username, Integer pageCurrent) {
		int rowCount = userMapper.getCount(username);
		int pageSize = 5;
		int pageCount = (rowCount - 1) / pageSize + 1;
		int startIndex = (pageCurrent - 1) * pageSize;
		List<User> list = userMapper.doFindPageObjects(username, startIndex, pageSize);
		PageVo<User> pageVo = new PageVo<>();
		pageVo.setPageCount(pageCount).setPageCurrent(pageCurrent).setPageSize(pageSize).setRecords(list).setRowCount(rowCount);
		return pageVo;
	}

	@Override
	@RequiresPermissions("wj.user.valid")
	public void doValidById(Integer id, Integer valid) {
		userMapper.doValidById(id,valid);		
	}

	@Override
	public void doSaveObject(User user) {
		if (StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getEmail())
				||StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(user.getUsername()))
			throw new ServiceException("邮箱和手机号不能为空");
		int count = userMapper.doFindObject("username",user.getUsername());
		if (count != 0)
			throw new ServiceException("用户名已存在");
		count = userMapper.doFindObject("email",user.getEmail());
		if (count != 0)
			throw new ServiceException("邮箱已存在");
		count = userMapper.doFindObject("mobile",user.getMobile());
		if (count != 0)
			throw new ServiceException("手机号码已存在");
		String salt = UUID.randomUUID().toString();
		SimpleHash hash = new SimpleHash("md5", user.getPassword(), salt, 1);
		String password = hash.toHex();
		user.setPassword(password).setSalt(salt).setValid(1).setCreatedTime(new Date()).setModifiedTime(user.getCreatedTime());
		userMapper.doSaveObject(user);
	}

	@Override
	public User doFindObjectById(Integer id) {
		User user = userMapper.doFindObjectById(id);
		return user;
	}

	@Override
	public void doUpdateObject(User user) {
		int count = 0;
		User oldUser = userMapper.doFindObjectById(user.getId());
		if (StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getEmail()))
			throw new ServiceException("邮箱和手机号不能为空");
		if (!oldUser.getEmail().equals(user.getEmail())) {
			count = userMapper.doFindObject("email",user.getEmail());
			if (count != 0)
				throw new ServiceException("邮箱已存在");
		}
		if (!oldUser.getMobile().equals(user.getMobile())) {
			count = userMapper.doFindObject("mobile",user.getMobile());
			if (count != 0)
				throw new ServiceException("手机号码已存在");
		}
		user.setModifiedTime(new Date());
		userMapper.doUpdateObject(user);
	}

	@Override
	public void doFindUserByPhone(String phone) {
		int count = userMapper.doFindObject("mobile", phone);
		System.out.println(count);
		if (count == 0)
			throw new ServiceException("该手机号未注册");
	}

	@Override
	public void doResetPwd(String phone, String newPwd, String cfgPwd) {
		if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(newPwd)||StringUtils.isEmpty(cfgPwd))
			throw new ServiceException("重置信息不能为空");
		if (!newPwd.equals(cfgPwd))
			throw new ServiceException("新密码和确认密码不一致");
		User user = userMapper.doFindObjectByMobile(phone);
		if (user == null)
			throw new ServiceException("该手机号未注册");
		String salt = user.getSalt();
		SimpleHash hash = new SimpleHash("md5", newPwd, salt, 1);
		newPwd = hash.toHex();
		userMapper.doUpdateById(user.getId(), newPwd, new Date());
		
	}

	@Override
	public void doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
		if (StringUtils.isEmpty(pwd)||StringUtils.isEmpty(newPwd)||StringUtils.isEmpty(cfgPwd))
			throw new ServiceException("密码不能为空");
		if (!newPwd.equals(cfgPwd))
			throw new ServiceException("新密码和确认密码不一致");
		if (pwd.equals(newPwd))
			throw new ServiceException("新密码不能和原密码一致");
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user == null)
			throw new ServiceException("未登陆");
		String salt = user.getSalt();
		pwd = new SimpleHash("md5", pwd, salt, 1).toHex();
		if (!pwd.equals(user.getPassword()))
			throw new ServiceException("原密码不正确");
		newPwd = new SimpleHash("md5", newPwd, salt, 1).toHex();
		userMapper.doUpdateById(user.getId(), newPwd, new Date());
	}
}
