package org.web.biz.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.command.schedule.ScheduleCommand;
import org.common.entity.Schedule;
import org.common.entity.view.PaginationView;
import org.common.entity.view.schedule.ScheduleView;
import org.common.exception.OaException;
import org.common.exception.ParamsValidatorException;
import org.common.service.IScheduleService;
import org.common.util.OvalUtils;
import org.common.util.WebResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("scheduleBiz")
public class ScheduleBiz {
	@Autowired
	private IScheduleService scheduleService;
	
	public WebResult saveSchedule(ScheduleCommand scheduleCommand){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(scheduleCommand);
			Schedule schedule = new Schedule();
			BeanUtils.copyProperties(scheduleCommand, schedule);
			schedule.setStartTime(scheduleCommand.getStartTime());
			schedule.setEndTime(scheduleCommand.getEndTime());
			schedule.setCreateTime(new Date());
			schedule.setStatus(schedule.getPersonal()==1?1:0);
			scheduleService.saveSchedule(schedule,scheduleCommand.getParticipants());
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (ParamsValidatorException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public WebResult editSchedule(Schedule schedule){
		WebResult webResult = new WebResult();
		try {
			scheduleService.updateSchedule(schedule);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public WebResult deleteSchedule(long id,long userId){
		WebResult webResult = new WebResult();
		try {
			scheduleService.deleteSchedule(id,userId);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult selectScheduleById(long id){
		WebResult webResult = new WebResult();
		webResult.setData(scheduleService.selectScheduleById(id));
		webResult.success();
		return webResult;
	}
	
	public WebResult selectScheduleByUserId(long userId){
		WebResult webResult = new WebResult();
		webResult.setData(scheduleService.selectScheduleByUser(userId));
		webResult.success();
		return webResult;
	}
	
	public WebResult checkSchedule(Long[] ids,int status){
		WebResult webResult = new WebResult();
		if(status==Schedule.CHECKED){
			scheduleService.checkSchedule(ids);
		}else if(status==Schedule.REFUSED){
			scheduleService.refuseSchedule(ids);
		}
		webResult.success();
		return webResult;
	}
	
	public List<Schedule> selectPersonalScheduleFor5(long userId){
		return scheduleService.selectPersonalScheduleFor5(userId);
	}
	
	public WebResult selectScheduleByCondition(String start,String end,long userId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", start);
		map.put("end", end);
		map.put("userId", userId);
		
		WebResult webResult = new WebResult();
		webResult.setData(scheduleService.selectScheduleByCondition(map));
		webResult.success();
		return webResult;
	}
	
	public WebResult selectScheduleByPage(PaginationView<ScheduleView> paginationView){
		WebResult webResult = new WebResult();
		scheduleService.selectScheduleByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public List<Schedule> selectWorkScheduleFor5(long userId){
		return scheduleService.selectWorkScheduleFor5(userId);
	}
}
