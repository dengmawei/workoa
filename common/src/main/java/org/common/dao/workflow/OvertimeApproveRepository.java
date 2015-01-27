package org.common.dao.workflow;

import java.util.List;

import org.common.dao.MyBatisRepository;
import org.common.entity.workflow.OvertimeApprove;
import org.springframework.stereotype.Component;

@MyBatisRepository
@Component
public interface OvertimeApproveRepository {
	void save(OvertimeApprove overtimeApprove);
	
	List<OvertimeApprove> selectListByFlowId(Long flowId);
}
