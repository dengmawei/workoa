package org.common.command.workflow;

import java.util.Map;

import org.common.workflow.JoinsignTaskCommand;

public class CompleteTaskCommand {
	/**
	 * 流程id
	 */
	private String processId;
	
	/**
	 * 当前活动名
	 */
	private String activityName;
	
	/**
	 * 成功流向
	 */
	private String transitionNamePass;
	
	/**
	 * 失败流向
	 */
	private String transitionNameNoPass;
	
	/**
	 * 输出参数
	 */
	private Map<String,Object> variables;
	
	/**
	 * 执行人id
	 */
	private Long executorId;
	
	/**
	 * 是否会签：0-竞争1-会签
	 */
	private int signType;
	
	/**
	 * 会签类型
	 */
	private int joinsignType;
	
	/**
	 * 通过与否0-驳回1-通过
	 */
	private int status;
	
	/**
	 * 审批信息
	 */
	private String message;
	
	/**
	 * 审核通过比例
	 */
	private double precent;

	public CompleteTaskCommand(String processId, String activityName,
			String transitionNamePass, String transitionNameNoPass, Map<String, Object> variables, Long executorId,
			int signType,int status) {
		this.processId = processId;
		this.activityName = activityName;
		this.transitionNamePass = transitionNamePass;
		this.transitionNameNoPass = transitionNameNoPass;
		this.variables = variables;
		this.executorId = executorId;
		this.signType = signType;
		this.status = status;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}


	public String getTransitionNamePass() {
		return transitionNamePass;
	}

	public void setTransitionNamePass(String transitionNamePass) {
		this.transitionNamePass = transitionNamePass;
	}

	public String getTransitionNameNoPass() {
		return transitionNameNoPass;
	}

	public void setTransitionNameNoPass(String transitionNameNoPass) {
		this.transitionNameNoPass = transitionNameNoPass;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}

	public int getSignType() {
		return signType;
	}

	public void setSignType(int signType) {
		this.signType = signType;
	}

	public int getJoinsignType() {
		return joinsignType;
	}

	public void setJoinsignType(int joinsignType) {
		this.joinsignType = joinsignType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrecent() {
		return precent;
	}

	public void setPrecent(double precent) {
		this.precent = precent;
	}
}
