package org.common.dao.workflow;

import java.util.List;
import java.util.Map;

import org.common.dao.MyBatisRepository;
import org.common.entity.workflow.UserPendingTask;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface UserPendingTaskRepository {
	long save(UserPendingTask userPendingTask);
	
	int selectUserPendingTaskCountByPage(Map map);
	
	List<UserPendingTask> selectUserPendingTaskListByPage(Map map);
	
	List<UserPendingTask> selectUserPendingTaskByCondition(Map map);
	
	void updateUserPendingTaskStatusByCondition(Map map);
}
