package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.Schedule;
import org.common.entity.ScheduleParticipant;
import org.common.entity.view.schedule.ScheduleView;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface ScheduleRepository {
	/**
	 * 新增日程
	 * @param schedule
	 */
	long saveSchedule(Schedule schedule);
	
	/**
	 * 修改日程
	 * @param schedule
	 */
	void updateSchedule(Schedule schedule);
	
	/**
	 * 根据id查询日程
	 * @param id
	 * @return
	 */
	Schedule selectScheduleById(Long id);
	
	/**
	 * 查询用户的日程
	 * @param userId
	 * @return
	 */
	List<Schedule> selctScheduleByUserId(Long userId);
	
	/**
	 * 删除日程
	 * @param id
	 */
	void deleteSchedule(Long id);
	
	/**
	 * 审核日程
	 * @param params
	 */
	void checkSchedule(Map params);
	
	/**
	 * 查询个人前5条日程
	 * @param userId
	 * @return
	 */
	List<Schedule> selectPersonalScheduleFor5(Long userId);
	
	/**
	 * 查询工作前5条日程
	 * @param userId
	 * @return
	 */
	List<Schedule> selectWorkScheduleFor5(Long userId);
	
	/**
	 * 添加参与者
	 * @param scheduleParticipant
	 */
	void saveScheduleParticipant(ScheduleParticipant scheduleParticipant);
	
	/**
	 * 按条件查询日程
	 * @param map
	 * @return
	 */
	List<ScheduleView> selectScheduleByCondition(Map map);
	
	/**
	 * 分页查询日程总数
	 * @param map
	 * @return
	 */
	int selectScheduelCountByPage(Map map);
	
	/**
	 * 分页查询日程列表
	 * @param map
	 * @return
	 */
	List<ScheduleView> selectScheduleListByPage(Map map);
}
