package org.common.entity.workflow;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class OvertimeFlow {
	private Long id;
	
	/**
	 * 申请人id
	 */
	private Long userId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 加班理由
	 */
	private String overtimeReason;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 是否删除
	 */
	private int isDel = 1;
	
	/**
	 * 加班时长
	 */
	private double hours;
	
	/**
	 * 实例id
	 */
	private String processId;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 父id
	 */
	private Long superFlowId = 0L;
	
	/**
	 * 部门id
	 */
	private Long departId;
	
	/**
	 * 部门名称
	 */
	private String departName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreateTime() {
		if(createTime==null){
			return "";
		}else{
			return DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm");
		}
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOvertimeReason() {
		return overtimeReason;
	}

	public void setOvertimeReason(String overtimeReason) {
		this.overtimeReason = overtimeReason;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public String getProcessId() {
		if(StringUtils.isBlank(processId)){
			return "";
		}
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getRealName() {
		if(realName==null){
			return "";
		}
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getSTime(){
		if(startTime==null){
			return "";
		}else{
			return DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm");
		}
	}
	
	public String getETime(){
		if(endTime==null){
			return "";
		}else{
			return DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm");
		}
	}

	public Long getSuperFlowId() {
		return superFlowId;
	}

	public void setSuperFlowId(Long superFlowId) {
		this.superFlowId = superFlowId;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}
}
