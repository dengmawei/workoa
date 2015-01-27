package org.common.command.workflow.overtime;

import org.common.entity.view.user.UserView;

public class OvertimeApproveCommand {
	private Long flowId;
	
	private UserView user;
	
	private int status;
	
	private String auditContent;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public UserView getUser() {
		return user;
	}

	public void setUser(UserView user) {
		this.user = user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
}
