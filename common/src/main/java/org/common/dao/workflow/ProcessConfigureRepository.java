package org.common.dao.workflow;

import java.util.List;
import java.util.Map;

import org.common.dao.MyBatisRepository;
import org.common.entity.workflow.ProcessConfigure;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface ProcessConfigureRepository {
	void save(ProcessConfigure processConfigure);
	
	void update(ProcessConfigure processConfigure);
	
	ProcessConfigure selectProcessConfigureByCondition(Map map);
	
	ProcessConfigure selectProcessConfigureById(Long id);
	
	void delete(Long id);
	
	int selectProcessConfigureCountByPage(Map map);
	
	List<ProcessConfigure> selectProcessConfigureListByPage(Map map);
}
