package org.common.command.user;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.ValidateWithMethod;

public class ChangePassCommand {
	@NotEmpty(message="用户名不能为空")
	private String userName;
	
	@NotEmpty(message="旧密码不能为空")
	private String password;
	
	@NotEmpty(message="新密码不能为空")
	private String newPassword;
	
	@NotEmpty(message="再次确认新密码不能为空")
	@ValidateWithMethod(methodName="isPassEqual",message="两次输入的新密码不一致",parameterType=String.class)
	private String newAgainPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewAgainPassword() {
		return newAgainPassword;
	}

	public void setNewAgainPassword(String newAgainPassword) {
		this.newAgainPassword = newAgainPassword;
	}
	
	public boolean isPassEqual(String password){
		return password.equals(newPassword);
	}
}
