package org.common.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.common.entity.view.user.UserLoginView;
import org.common.service.IPermitService;
import org.common.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userRealm")
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPermitService permitService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String)principals.getPrimaryPrincipal();
		UserLoginView userView = userService.selectUserLoginInfoByUserName(userName);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		//设置用户角色（隐式）
		
		//设置用户权限（显式）
		if(userView.getUserName().equals("admin")){
			authorizationInfo.addObjectPermission(new WildcardPermission("*:*",false));
		}else{
			authorizationInfo.addObjectPermissions(permitService.selectPermitByUserId(userView.getUserId()));
		}
		
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String userName = (String)token.getPrincipal();
		
		UserLoginView user = userService.selectUserLoginInfoByUserName(userName);
		
		if(user==null){
			throw new UnknownAccountException();
		}
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
		
		
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}


}
