package org.web.biz.system;

import java.io.OutputStream;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.common.command.user.UserAddCommand;
import org.common.command.user.UserUpdateCommand;
import org.common.entity.User;
import org.common.entity.view.PaginationView;
import org.common.entity.view.user.UserBaseView;
import org.common.exception.OaException;
import org.common.exception.ParamsValidatorException;
import org.common.service.IPaginationService;
import org.common.service.IUserService;
import org.common.shiro.realm.UserRealm;
import org.common.util.OvalUtils;
import org.common.util.WebResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userBiz")
public class UserBiz {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPaginationService paginationService;
	
	public WebResult selectUser(PaginationView<UserBaseView> paginationView){
		WebResult webResult = new WebResult();
		webResult.success();
		paginationService.selectUser(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		return webResult;
	}
	
	public WebResult addUser(UserAddCommand userCommand){
		WebResult webResult = new WebResult();
		webResult.success();
		User user = new User();
		BeanUtils.copyProperties(userCommand, user);
		try {
			OvalUtils.paramsValidator(userCommand);
			userService.addUser(user,userCommand.getInputStream());
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		} catch (ParamsValidatorException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult updateUser(UserUpdateCommand userCommand){
		WebResult webResult = new WebResult();
		webResult.success();
		User user = new User();
		BeanUtils.copyProperties(userCommand, user);
		try {
			OvalUtils.paramsValidator(userCommand);
			userService.updateUser(user,userCommand.getInputStream());
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		} catch (ParamsValidatorException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult selectUserById(Long userId){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setData(userService.selectUserById(userId));
		return webResult;
	}
	
	public void selectUserImage(Long userId,OutputStream output){
		userService.selectUserImage(userId, output);
	}
	
	public WebResult selectUserByUserName(String userName){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(userService.selectUserByUserName(userName));
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult refreshPersionPermission(){
		WebResult webResult = new WebResult();
		
		RealmSecurityManager securityManager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
		UserRealm userRealm = (UserRealm)securityManager.getRealms().iterator().next();
		
		//清空缓存
		userRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
		
		webResult.success();
		return webResult;
	}
}
