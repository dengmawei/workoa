package org.common.service;

import javax.servlet.http.HttpServletRequest;

import org.common.command.LoginCommand;
import org.common.exception.OaException;

public interface ILoginService {
	boolean login(LoginCommand cmd,HttpServletRequest request) throws OaException;
	
	/**
	 * 登出
	 */
	void loginOut();
	
	/**
	 * 修改密码
	 * @param userName 用户名
	 * @param password 旧密码
	 * @param newPassword 新密码
	 * @throws OaException
	 */
	void changePassword(String userName,String password,String newPassword)throws OaException;
}
