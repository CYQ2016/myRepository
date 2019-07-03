package com.db.sys.service.realm;

import java.util.HashSet;
import java.util.List;
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
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	/**
	 * 通过此方法完成授权信息的获取和封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//1.拿到用户id
		//基于用户id查找角色id
		//基于角色id查找菜单id并判定
		//基于菜单id获取授权标识
		//对信息进行封装
		System.out.println("===doGetAuthorizationInfo===");
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if (roleIds==null||roleIds.size()==0)
			throw new AuthorizationException("该用户暂未分配角色");
		Integer[] array = {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
		if (menuIds==null||menuIds.size()==0)
			throw new AuthorizationException();
		List<String> permissions = sysMenuDao.findPremssionsById(menuIds.toArray(array));
		if (permissions==null||permissions.size()==0)
			throw new AuthorizationException();
		Set<String> permissionSet = new HashSet<>();
		for (String per : permissions) {
			if (per!=null)
				permissionSet.add(per);
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissionSet);
		return info;
	}
	/**
	 * 通过此方法完成认证信息的获取和封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1.从token中获取用户信息（前端用户输入）
		//2.基于用户名从数据库中查询用户信息
		//3.判定用户是否存在
		//4.判定用户是否已被禁用
		//5.封装用户信息并返回
		UsernamePasswordToken up = (UsernamePasswordToken) token;
		String username = up.getUsername();
		SysUser sysUser = sysUserDao.findUserByUserName(username);
		if (sysUser==null)
			throw new UnknownAccountException("该用户不存在");
		if (sysUser.getValid()==0)
			throw new LockedAccountException("该用户已被禁用");
		String password = sysUser.getPassword();
		ByteSource salt = ByteSource.Util.bytes(sysUser.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
												sysUser, password, 
												salt, getName());
		return info;
	}
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("MD5");
		hcm.setHashIterations(1);
		super.setCredentialsMatcher(hcm);
	}
}
