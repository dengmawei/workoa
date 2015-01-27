package org.common.command.workflow;

import java.util.Map;

import org.common.entity.workflow.SuperFlowProcess;

public class StartProcessCommand {
	/**
	 * 流程发起人id
	 */
	private Long userId;
	/**
	 * 流程key
	 */
	private String processKey;
	
	/**
	 * 初始化参数
	 */
	private Map variables;
	
	/**
	 * 流程记录
	 */
	private SuperFlowProcess superFlowProcess;

	public StartProcessCommand(Long userId,String processKey, Map variables,
			SuperFlowProcess superFlowProcess) {
		this.userId = userId;
		this.processKey = processKey;
		this.variables = variables;
		this.superFlowProcess = superFlowProcess;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public Map getVariables() {
		return variables;
	}

	public void setVariables(Map variables) {
		this.variables = variables;
	}

	public SuperFlowProcess getSuperFlowProcess() {
		return superFlowProcess;
	}

	public void setSuperFlowProcess(SuperFlowProcess superFlowProcess) {
		this.superFlowProcess = superFlowProcess;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
