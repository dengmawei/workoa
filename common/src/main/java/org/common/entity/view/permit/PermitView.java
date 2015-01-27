package org.common.entity.view.permit;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;


public class PermitView {
	/**
	 * 权限描述
	 */
	@NotNull(message="权限描述不能为空")
	@NotEmpty(message="权限描述不能为空")
	private String permitDesc;
	
	/**
	 * 模块id
	 */
	@NotNull(message="模块id不能为空")
	private Long moduleId;
	
	/**
	 * 动作id
	 */
	@NotNull(message="Action id不能为空")
	private Long actionId;

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
}
