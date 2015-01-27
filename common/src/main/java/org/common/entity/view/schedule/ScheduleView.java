package org.common.entity.view.schedule;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class ScheduleView {
	private Long id;
	
	private Long userId;
	
	private String title;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date createTime;
	
	private int status;
	
	private int flag;
	
	private int personal;

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

	public String getStartTime() {
		if(startTime==null){
			return "";
		}
		return DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm");
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		if(endTime==null){
			return "";
		}
		return DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm");
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartYear(){
		if(startTime==null){
			return "";
		}
		return DateFormatUtils.format(startTime, "yyyy");
	}
	
	public String getStartMonth(){
		if(startTime==null){
			return "";
		}
		return DateFormatUtils.format(startTime, "M");
	}
	
	public String getStartDay(){
		if(startTime==null){
			return "";
		}
		return DateFormatUtils.format(startTime, "d");
	}
	
	public String getStartHour(){
		if(startTime==null){
			return "";
		}
		return DateFormatUtils.format(startTime, "H");
	}
	
	public String getStartMinute(){
		if(startTime==null){
			return "";
		}
		return DateFormatUtils.format(startTime, "m");
	}
	
	public String getEndYear(){
		if(endTime==null){
			return "";
		}
		return DateFormatUtils.format(endTime, "yyyy");
	}
	
	public String getEndMonth(){
		if(endTime==null){
			return "";
		}
		return DateFormatUtils.format(endTime, "M");
	}
	
	public String getEndDay(){
		if(endTime==null){
			return "";
		}
		return DateFormatUtils.format(endTime, "d");
	}
	
	public String getEndHour(){
		if(endTime==null){
			return "";
		}
		return DateFormatUtils.format(endTime, "H");
	}
	
	public String getEndMinute(){
		if(endTime==null){
			return "";
		}
		return DateFormatUtils.format(endTime, "m");
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getPersonal() {
		return personal;
	}

	public void setPersonal(int personal) {
		this.personal = personal;
	}

}
