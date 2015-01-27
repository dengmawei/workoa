package org.common.entity.workflow;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class ProcessConfigure {
	private Long id;
	
	private String processKey;
	
	private String processName;
	
	private String taskName;
	
	private String taskDesc;
	
	private String taskDealUrl;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskDealUrl() {
		return taskDealUrl;
	}

	public void setTaskDealUrl(String taskDealUrl) {
		this.taskDealUrl = taskDealUrl;
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
