package org.common.entity;

public class UserPermit {
	private Long id;
	
	private Long userId;

	private Long roleId = 0L;
	
	private Long positionId = 0L;
	
	private Long permitId = 0L;
	
	public UserPermit(){
		
	}
	
	public UserPermit(Long userId, Long roleId,Long positionId, Long permitId) {
		this.userId = userId;
		
		if(roleId!=null){
			this.roleId = roleId;
		}
		
		if(positionId!=null){
			this.positionId = positionId;
		}
		
		if(permitId!=null){
			this.permitId = permitId;
		}
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

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getPermitId() {
		return permitId;
	}

	public void setPermitId(Long permitId) {
		this.permitId = permitId;
	}
}
