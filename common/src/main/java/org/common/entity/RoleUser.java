package org.common.entity;

public class RoleUser {
	private Long id;
	
	private Long roleId;
	
	private Long userId;

	public RoleUser(){
		
	}
	
	public RoleUser(Long roleId, Long userId) {
		super();
		this.roleId = roleId;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
