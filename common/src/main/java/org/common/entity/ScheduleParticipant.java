package org.common.entity;

public class ScheduleParticipant {
	private Long id;
	
	private Long scheduleId;
	
	private Long participatorId;

	public ScheduleParticipant(){
		
	}
	
	public ScheduleParticipant(Long scheduleId, Long participatorId) {
		this.scheduleId = scheduleId;
		this.participatorId = participatorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getParticipatorId() {
		return participatorId;
	}

	public void setParticipatorId(Long participatorId) {
		this.participatorId = participatorId;
	}

}
