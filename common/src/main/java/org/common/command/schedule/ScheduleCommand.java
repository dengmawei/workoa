package org.common.command.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.ValidateWithMethod;


public class ScheduleCommand {
	public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 标题
	 */
	@NotEmpty(message="标题不能为空")
	@MaxLength(value=50,message="标题过长")
	private String title;
	
	/**
	 * 内容
	 */
	@NotEmpty(message="内容不能为空")
	@MaxLength(value=500,message="内容过长")
	private String content;
	
	
	/**
	 * 开始时间
	 */
	@NotEmpty(message="开始时间不能为空")
	private String scheduleStartTime;
	
	/**
	 * 结束时间
	 */
	@NotEmpty(message="结束时间不能为空")
	@ValidateWithMethod(methodName="valideTimeZone",parameterType=String.class,message="结束时间不能早于开始时间")
	private String scheduleEndTime;
	
	private boolean valideTimeZone(String end){
		try {
			if(DATEFORMAT.parse(end).compareTo(DATEFORMAT.parse(scheduleStartTime))<=0){
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	private int personal;
	
	private int flag;
	
	private Long[] participants;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		try {
			return DATEFORMAT.parse(scheduleStartTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public Date getEndTime() {
		try {
			return DATEFORMAT.parse(scheduleEndTime);
		} catch (ParseException e) {
			return null;
		}
	}

	public int getPersonal() {
		return personal;
	}

	public void setPersonal(int personal) {
		this.personal = personal;
	}

	public String getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(String scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	public String getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(String scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Long[] getParticipants() {
		return participants;
	}

	public void setParticipants(Long[] participants) {
		this.participants = participants;
	}
}
