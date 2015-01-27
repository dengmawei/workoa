package org.common.entity.workflow;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class SuperFlowProcess {
	private Long id;
	
	private Long userId;
	
	private String userName;
	
	private String realName;
	
	private Date createTime;
	
	private String processKey;
	
	private String processName;

	public SuperFlowProcess(){
		
	}
	
	public SuperFlowProcess(Long userId, String userName, String realName, Date createTime,
			String processKey, String processName) {
		this.userId = userId;
		this.userName = userName;
		this.realName = realName;
		this.createTime = createTime;
		this.processKey = processKey;
		this.processName = processName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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
}
