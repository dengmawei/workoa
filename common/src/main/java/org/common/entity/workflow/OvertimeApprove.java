package org.common.entity.workflow;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class OvertimeApprove {
	private Long id;
	
	private Long applicantId;
	
	private String applicantName;
	
	private Long auditorId;
	
	private String auditorName;
	
	private Date createTime;
	
	private String auditContent;
	
	private int status;
	
	private Long flowId;
	
	private String processId;
	
	private String taskName;

	public OvertimeApprove() {
	}

	public OvertimeApprove(Long applicantId, String applicantName,
			Long auditorId, String auditorName, Date createTime,
			String auditContent, int status, Long flowId, String processId,
			String taskName) {
		this.applicantId = applicantId;
		this.applicantName = applicantName;
		this.auditorId = auditorId;
		this.auditorName = auditorName;
		this.createTime = createTime;
		this.auditContent = auditContent;
		this.status = status;
		this.flowId = flowId;
		this.processId = processId;
		this.taskName = taskName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
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

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getApproveProgress(){
		if(status==1){
			return "通过";
		}else{
			return "驳回";
		}
	}
}
