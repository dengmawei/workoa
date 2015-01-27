package org.common.service;

import java.util.List;
import java.util.Map;

import org.common.entity.Schedule;
import org.common.entity.view.PaginationView;
import org.common.entity.view.schedule.ScheduleView;
import org.common.exception.OaException;

public interface IScheduleService {
	/**
	 * 新增日程
	 * @param schedule
	 */
	void saveSchedule(Schedule schedule,Long[] participants)throws OaException;
	
	/**
	 * 编辑日程
	 * @param schedule
	 */
	void updateSchedule(Schedule schedule)throws OaException;
	
	/**
	 * 删除日程
	 * @param id
	 */
	void deleteSchedule(Long id,long userId)throws OaException;
	
	/**
	 * 按用户查询日程
	 * @param userId
	 * @return
	 */
	List<Schedule> selectScheduleByUser(Long userId);
	
	/**
	 * 查询单个日程
	 * @param id
	 * @return
	 */
	Schedule selectScheduleById(Long id);
	
	/**
	 * 驳回日程安排
	 * @param ids
	 */
	void refuseSchedule(Long[] ids);
	
	/**
	 * 通过审核
	 * @param ids
	 */
	void checkSchedule(Long[] ids);
	
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
	 * 按条件查询日程
	 * @param map
	 * @return
	 */
	List<ScheduleView> selectScheduleByCondition(Map map);
	
	/**
	 * 分页查询日程
	 * @param paginationView
	 */
	void selectScheduleByPage(PaginationView<ScheduleView> paginationView);
}
