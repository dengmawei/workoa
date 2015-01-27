package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.Action;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface ActionRepository {
	void saveAction(Action action);
	
	void updateAction(Action action);
	
	void deleleAction(Long id);
	
	int selectActionCountByPage(Map map);
	
	List<Action> selectActionListByPage(Map map);
	
	Action selectActionById(Long id);
}
