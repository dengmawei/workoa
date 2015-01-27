package org.common.entity;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class Permit {
	private Long id;
	
	/**
	 * 权限编码
	 */
	private String permitCode;
	
	/**
	 * 权限值（关键字）
	 */
	private String permitValue;
	
	/**
	 * 权限描述
	 */
	private String permitDesc;
	
	/**
	 * 模块id
	 */
	private Long moduleId;
	
	/**
	 * 动作id
	 */
	private Long actionId;
	
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

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
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
