package org.common.dao.workflow;

import java.util.List;
import java.util.Map;

import org.common.dao.MyBatisRepository;
import org.common.entity.workflow.SuperFlowProcess;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface SuperFlowProcessRepository {
	long save(SuperFlowProcess superFlowProcess);
	
	int selectFlowProcessCountByPage(Map map);
	
	List<SuperFlowProcess> selectFlowProcessListByPage(Map map);
}
