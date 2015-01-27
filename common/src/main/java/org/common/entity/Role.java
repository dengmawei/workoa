package org.common.entity;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class Role {
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 角色描述
	 */
	private String roleDesc;
	
	/**
	 * 是否删除：1-未删除0-删除
	 */
	private Integer isDel = 1;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getCreateTime() {
		return DateFormatUtils.format(createTime, "yyyy-MM-dd hh:mm:ss");
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
