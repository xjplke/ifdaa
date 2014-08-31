package cn.adfi.radius.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.adfi.radius.model.User;
import cn.adfi.radius.repo.UserRepository;

@Component
public class UserRealm extends AuthorizingRealm{
	@Autowired
	UserRepository userRepo;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();
		
		List<User> list = userRepo.findByUsername(username);
		User user = list.get(0);
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(user.getRolesStringSet());
		authorizationInfo.setStringPermissions(user.getPermissionStringSet());
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();
		List<User> list = userRepo.findByUsername(username);
		if(null==list || list.size() ==0){
			throw new UnknownAccountException("Username or Password error!");//没找到帐号
		}
		User user = list.get(0);

        if(Boolean.FALSE.equals(user.getIsActive())) {
            throw new LockedAccountException("Username is locked!"); //帐号锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		 user.getUsername(), //用户名
                 user.getPassword(), //密码
                 ByteSource.Util.bytes(""),//salt=username+salt
                 getName()  //realm name
        );
		return authenticationInfo;
	}

}
