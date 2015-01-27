package org.common.command.workflow.overtime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.ValidateWithMethod;

public class OvertimeCommand {
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	/**
	 * 业务id
	 */
	private Long flowId;
	
	/**
	 * 申请人id
	 */
	@NotBlank(message="用户id不能为空")
	private Long userId;
	
	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	private String userName;
	
	/**
	 * 真实姓名
	 */
	@NotBlank(message="真实姓名不能为空")
	private String realName;
	
	/**
	 * 手机号
	 */
	@NotBlank(message="手机号码不能为空")
	private String mobile;
	
	/**
	 * 开始时间
	 */
	@NotBlank(message="开始时间不能为空")
	private String overtimeStartTime;
	
	/**
	 * 结束时间
	 */
	@NotBlank(message="结束时间不能为空")
	@ValidateWithMethod(methodName="valideTimeZone",parameterType=String.class,message="结束时间不能早于开始时间")
	private String overtimeEndTime;
	
	private boolean valideTimeZone(String end){
		try {
			if(DATEFORMAT.parse(end).compareTo(DATEFORMAT.parse(overtimeStartTime))<=0){
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 加班理由
	 */
	@NotBlank(message="加班理由不能为空")
	private String overtimeReason;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 部门id
	 */
	private Long departId;
	
	/**
	 * 部门名称
	 */
	private String departName;

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

	public Date getStartTime() {
		try {
			return DATEFORMAT.parse(overtimeStartTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public Date getEndTime() {
		try {
			return DATEFORMAT.parse(overtimeEndTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public String getOvertimeStartTime() {
		return overtimeStartTime;
	}

	public void setOvertimeStartTime(String overtimeStartTime) {
		this.overtimeStartTime = overtimeStartTime;
	}

	public String getOvertimeEndTime() {
		return overtimeEndTime;
	}

	public void setOvertimeEndTime(String overtimeEndTime) {
		this.overtimeEndTime = overtimeEndTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
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
