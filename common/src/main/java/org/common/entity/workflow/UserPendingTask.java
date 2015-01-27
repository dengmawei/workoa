package org.common.entity.workflow;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class UserPendingTask {
	private Long id;
	
	/**
	 * 待办人id
	 */
	private Long userId;
	
	/**
	 * 流程key
	 */
	private String processKey;
	
	/**
	 * 流程名
	 */
	private String processName;
	
	/**
	 * 流程实例Id
	 */
	private String processId;
	
	/**
	 * 待办任务id
	 */
	private String taskId;
	
	/**
	 * 任务活动名
	 */
	private String activityName;
	
	/**
	 * 状态 0-待办1-完成2-挂起3-失败
	 */
	private int status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 会签类型 0-竞争1-会签
	 */
	private int signType;
	
	/**
	 * 待办处理人id
	 */
	private Long executorId;

	public UserPendingTask(){
		
	}
	
	public UserPendingTask(Long userId, String processKey,
			String processName, String processId, String taskId,
			String activityName, int status,Date createtTime,
			int signType) {
		this.userId = userId;
		this.processKey = processKey;
		this.processName = processName;
		this.processId = processId;
		this.taskId = taskId;
		this.activityName = activityName;
		this.status = status;
		this.signType = signType;
		this.createTime = createtTime;
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

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getSignType() {
		return signType;
	}

	public void setSignType(int signType) {
		this.signType = signType;
	}

	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}
}
