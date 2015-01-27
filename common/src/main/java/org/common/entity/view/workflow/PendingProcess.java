package org.common.entity.view.workflow;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class PendingProcess {
	private String taskId;
	
	private String taskName;
	
	private Date createTime;
	
	private String executionId;
	
	private String activityName;
	
	private String processName;
	
	public PendingProcess(){
		
	}
	
	public PendingProcess(String taskId, String taskName,
			Date createTime, String executionId, String activityName) {
		this.taskId = taskId;
		this.taskName = taskName;
		this.createTime = createTime;
		this.executionId = executionId;
		this.activityName = activityName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
}
