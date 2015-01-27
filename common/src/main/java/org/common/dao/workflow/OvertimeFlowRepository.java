package org.common.dao.workflow;

import java.util.List;
import java.util.Map;

import org.common.dao.MyBatisRepository;
import org.common.entity.workflow.OvertimeFlow;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface OvertimeFlowRepository {
	/**
	 * 提交加班申请
	 * @param overtimeFlow
	 */
	long save(OvertimeFlow overtimeFlow);
	
	/**
	 * 修改加班申请
	 * @param overtimeFlow
	 */
	void update(OvertimeFlow overtimeFlow);
	
	/**
	 * 根据条件查询加班申请
	 * @param param
	 * @return
	 */
	List<OvertimeFlow> selectOvertimeListByCondition(Map param);
	
	/**
	 * 根据id查流程
	 * @param flowId
	 * @return
	 */
	OvertimeFlow selectOvertimeFlowById(Long flowId);
	
	/**
	 * 分页查询加班流程总数
	 * @param map
	 * @return
	 */
	int selectOvertimeCountByPage(Map map);
	
	/**
	 * 分页查询加班列表
	 * @param map
	 * @return
	 */
	List<OvertimeFlow> selectOvertimeListByPage(Map map);
	
	/**
	 * 根据流程id查询
	 * @param processId
	 * @return
	 */
	OvertimeFlow selectOvertimeFlowByProcessId(String processId);
	
	/**
	 * 删除加班申请
	 * @param flowId
	 */
	void deleteOvertimeFlow(Long flowId);
	
	/**
	 * 根据父id查询流程id
	 * @param superFlowId
	 * @return
	 */
	long selectFlowIdBySuperFlowId(Long superFlowId);
}
