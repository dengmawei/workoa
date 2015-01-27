package org.common.entity.view.role;

public class RoleUserView {
	/**
	 * 关联id
	 */
	private Long ruId;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户编码
	 */
	private String userCode;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	public Long getRuId() {
		return ruId;
	}

	public void setRuId(Long ruId) {
		this.ruId = ruId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
