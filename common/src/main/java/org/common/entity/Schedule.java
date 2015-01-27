package org.common.entity;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class Schedule {
	public static final int REFUSED = 2;
	public static final int CHECKED = 1;
	
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 最近更新时间
	 */
	private Date updateTime;
	
	/**
	 * 状态：0-待审核1-通过2-驳回3-废弃
	 */
	private int status=0;

	/**
	 * 是否私有
	 */
	private int personal=1;
	
	/**
	 * 是否标记为重要
	 */
	private int flag = 0;
	
	public Schedule(){
		
	}
	
	public Schedule(Long userId, String title, String content, Date createTime,
			Date startTime, Date endTime) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPersonal() {
		return personal;
	}

	public void setPersonal(int personal) {
		this.personal = personal;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
