package com.wj.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.wj.exception.ServiceException;
import com.wj.mapper.UserMapper;
import com.wj.pojo.User;
@Component
public class Realm extends AuthorizingRealm{
	@Autowired
	private UserMapper userMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		String permission = user.getPermission();
		System.out.println(permission);
		if (StringUtils.isEmpty(permission))
			throw new ServiceException("该用户没有权限");
		Set<String> roles = new HashSet<>();
		if (permission.indexOf(",")==-1) {
			roles.add(permission);
		}else {
			String[] permissions = permission.split(",");		
			for (String role : permissions) {
				if (!StringUtils.isEmpty(role))
					roles.add(role);
			}
		}
		System.out.println(roles);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(roles);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		User user = userMapper.doFindObjectByUsername(username);
		if (user == null)
			throw new UnknownAccountException("该用户不存在");
		if (user.getValid() == 0)
			throw new LockedAccountException("该用户已被禁用");
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt().getBytes());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");
		matcher.setHashIterations(1);
		super.setCredentialsMatcher(matcher);
	}
	
}
