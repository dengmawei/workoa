package org.common.service.workflow;


public interface IProcessTaskService {
	long getFlowIdByProcessInstanceId(String processId);
	
	/**
	 * 根据父id查询流程id
	 * @param superFlowId
	 * @return
	 */
	long findFlowIdBySuperFlowId(Long superFlowId);
}
