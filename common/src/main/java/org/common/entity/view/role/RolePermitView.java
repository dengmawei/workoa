package org.common.entity.view.role;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class RolePermitView {
	private Long roleId;
	
	private Long permitId;
	
	private String permitCode;
	
	private String permitValue;
	
	private String permitDesc;
	
	private Date createTime;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermitId() {
		return permitId;
	}

	public void setPermitId(Long permitId) {
		this.permitId = permitId;
	}

	public String getPermitCode() {
		return permitCode;
	}

	public void setPermitCode(String permitCode) {
		this.permitCode = permitCode;
	}

	public String getPermitValue() {
		return permitValue;
	}

	public void setPermitValue(String permitValue) {
		this.permitValue = permitValue;
	}

	public String getPermitDesc() {
		return permitDesc;
	}

	public void setPermitDesc(String permitDesc) {
		this.permitDesc = permitDesc;
	}

	public String getCreateTime() {
		if(createTime==null){
			return "";
		}
		return DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
