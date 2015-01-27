package org.common.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.common.command.LoginCommand;
import org.common.dao.UserRepository;
import org.common.entity.User;
import org.common.exception.OaException;
import org.common.exception.ParamsValidatorException;
import org.common.exception.system.user.UserNotFoundException;
import org.common.exception.system.user.UserPassErrorException;
import org.common.service.ILoginService;
import org.common.util.OvalUtils;
import org.common.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private PasswordHelper passwordHelper;
	
	public boolean login(LoginCommand cmd,HttpServletRequest request) throws OaException{
		return loginValidate(cmd,request);
	}

	public void loginOut(){
		SecurityUtils.getSubject().logout();
	}
	
	public String getPath(HttpServletRequest request){
		String path = request.getContextPath();
		return (path == null || path.length()==0)?"/":path;
	}
	
	public boolean loginValidate(LoginCommand cmd,HttpServletRequest request) throws OaException{
		try{
			UsernamePasswordToken token = new UsernamePasswordToken(cmd.getUserName(), cmd.getPassword());
			token.setRememberMe(cmd.getRemeberMe());
			SecurityUtils.getSubject().login(token);
			return true;
		}catch(AuthenticationException e){
			request.setAttribute("shiroLoginFailure", e.getClass().getName());
			request.setAttribute("userName", cmd.getUserName());
			request.setAttribute("password", cmd.getPassword());
			return false;
		}
	}

	public void changePassword(String userName, String password,
			String newPassword) throws OaException {
		User user = userDao.selectUserByUserName(userName);
		if(user==null){
			throw new UserNotFoundException();
		}
		
		if(!passwordHelper.encryptPassword(password, user.getCredentialsSalt()).equals(user.getPassword())){
			throw new UserPassErrorException();
		}
		
		user.setPassword(passwordHelper.encryptPassword(newPassword, user.getCredentialsSalt()));
		userDao.updateUser(user);
	}
}
