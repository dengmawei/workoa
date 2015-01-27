package org.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.dao.ScheduleRepository;
import org.common.entity.Schedule;
import org.common.entity.ScheduleParticipant;
import org.common.entity.view.PaginationView;
import org.common.entity.view.schedule.ScheduleView;
import org.common.exception.OaException;
import org.common.exception.system.schedule.ScheduleCannotRemoveException;
import org.common.exception.system.schedule.ScheduleParticipantsNotNullException;
import org.common.exception.system.schedule.ScheduleTimeZoneConflictException;
import org.common.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scheduleService")
public class ScheduleServiceImpl implements IScheduleService {
	@Autowired
	private ScheduleRepository scheduleDao;
	
	@Transactional(rollbackFor=Exception.class)
	public void saveSchedule(Schedule schedule,Long[] participants)throws OaException {
		List<Schedule> scheduleList = scheduleDao.selctScheduleByUserId(schedule.getUserId());
		if(scheduleList!=null && scheduleList.size()>0){
			for(Schedule tempSchedule:scheduleList){
				//有时间范围冲突
				if(!(tempSchedule.getStartTime().compareTo(schedule.getEndTime())>0 || 
						tempSchedule.getEndTime().compareTo(schedule.getStartTime())<0)){
					throw new ScheduleTimeZoneConflictException();
				}
			}
		}
		
		scheduleDao.saveSchedule(schedule);
		if(schedule.getPersonal()==1){
			ScheduleParticipant scheduleParticipant = new ScheduleParticipant(schedule.getId(), schedule.getUserId());
			scheduleDao.saveScheduleParticipant(scheduleParticipant);
		}else{
			if(participants!=null && participants.length>0){
				for(Long participatorId:participants){
					ScheduleParticipant scheduleParticipant = new ScheduleParticipant(schedule.getId(), participatorId);
					scheduleDao.saveScheduleParticipant(scheduleParticipant);
				}
			}else{
				throw new ScheduleParticipantsNotNullException();
			}
		}
	}

	public void updateSchedule(Schedule schedule)throws OaException {
		List<Schedule> scheduleList = scheduleDao.selctScheduleByUserId(schedule.getUserId());
		if(scheduleList!=null && scheduleList.size()>0){
			for(Schedule tempSchedule:scheduleList){
				//不是自己本身且有范围冲突
				if(tempSchedule.getId().longValue()!=schedule.getId().longValue() && 
						!(tempSchedule.getStartTime().compareTo(schedule.getEndTime())>0 || 
						tempSchedule.getEndTime().compareTo(schedule.getStartTime())<0)){
					throw new ScheduleTimeZoneConflictException();
				}
			}
		}
		scheduleDao.updateSchedule(schedule);
	}

	public void deleteSchedule(Long id,long userId)throws OaException {
		Schedule schedule = scheduleDao.selectScheduleById(id);
		if(schedule.getUserId().longValue()==userId && schedule.getPersonal()==1){
			scheduleDao.deleteSchedule(id);
		}else{
			throw new ScheduleCannotRemoveException();
		}
	}

	public List<Schedule> selectScheduleByUser(Long userId) {
		return scheduleDao.selctScheduleByUserId(userId);
	}

	public Schedule selectScheduleById(Long id) {
		return scheduleDao.selectScheduleById(id);
	}

	public void refuseSchedule(Long[] ids) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", Schedule.REFUSED);
		
		params.put("ids", ids);
		scheduleDao.checkSchedule(params);
	}

	public void checkSchedule(Long[] ids) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", Schedule.CHECKED);
		
		params.put("ids", ids);
		scheduleDao.checkSchedule(params);
	}

	public List<Schedule> selectPersonalScheduleFor5(Long userId) {
		return scheduleDao.selectPersonalScheduleFor5(userId);
	}

	public List<ScheduleView> selectScheduleByCondition(Map map) {
		return scheduleDao.selectScheduleByCondition(map);
	}

	public void selectScheduleByPage(PaginationView<ScheduleView> paginationView) {
		int count = scheduleDao.selectScheduelCountByPage(paginationView.loadFilter());
		List<ScheduleView> list = scheduleDao.selectScheduleListByPage(paginationView.loadFilter());
		
		paginationView.setCount(count);
		paginationView.setPaginationList(list);
	}

	public List<Schedule> selectWorkScheduleFor5(Long userId) {
		return scheduleDao.selectWorkScheduleFor5(userId);
	}
}
