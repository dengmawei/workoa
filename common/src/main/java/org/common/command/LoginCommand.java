package org.common.command;

import net.sf.oval.constraint.NotBlank;

public class LoginCommand {
	@NotBlank(message="用户名不能为空")
	private String userName;
	
	@NotBlank(message="密码不能为空")
	private String password;
	
	private Boolean remeberMe;

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

	public Boolean getRemeberMe() {
		return remeberMe;
	}

	public void setRemeberMe(Boolean remeberMe) {
		this.remeberMe = remeberMe;
	}
}
