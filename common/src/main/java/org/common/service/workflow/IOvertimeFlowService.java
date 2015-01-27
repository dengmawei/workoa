package org.common.service.workflow;

import org.common.command.workflow.overtime.OvertimeApproveCommand;
import org.common.command.workflow.overtime.OvertimeRefuseModifyCommand;
import org.common.entity.view.PaginationView;
import org.common.entity.view.workflow.OvertimeFlowView;
import org.common.entity.workflow.OvertimeFlow;
import org.common.exception.FlowException;
import org.common.exception.OaException;

public interface IOvertimeFlowService {
	/**
	 * 提交加班申请
	 * @param overtimeFlow
	 */
	void saveOvertimeFlow(OvertimeFlow overtimeFlow)throws OaException;
	
	/**
	 * 编辑草稿
	 * @param overtimeFlow
	 * @throws OaException
	 */
	void editOvertimeFlowDraft(OvertimeFlow overtimeFlow)throws OaException;
	
	/**
	 * 部门经理审核
	 * @param flowId
	 */
	void departMangerApprove(OvertimeApproveCommand command) throws OaException,FlowException;
	
	/**
	 * 总经理审核
	 * @param flowId
	 */
	void generalManagerApprove(OvertimeApproveCommand command)throws OaException,FlowException;
	
	/**
	 * 员工加班统计汇总
	 * @param flowId
	 */
	void staffOvertimeSummary(OvertimeApproveCommand command)throws OaException,FlowException;
	
	/**
	 * 驳回修改
	 * @param flowId
	 * @throws OaException
	 * @throws FlowException
	 */
	void refuseModify(OvertimeRefuseModifyCommand command)throws OaException,FlowException;
	
	/**
	 * 根据id查询加班信息
	 * @param flowId
	 * @return
	 */
	OvertimeFlow selectOvertimeFlowById(Long flowId)throws OaException,FlowException;
	
	/**
	 * 分页查询加班流程
	 * @param paginationView
	 */
	void selectOvertimeFlowByPage(PaginationView<OvertimeFlow> paginationView);
	
	/**
	 * 加载加班请求详情
	 * @param flowId
	 * @return
	 * @throws OaExcept
	 */
	OvertimeFlowView getOvertimeFlowInfo(Long flowId)throws OaException;
	
	/**
	 * 删除加班申请
	 * @param flowId
	 * @throws OaException
	 */
	void deleteOvertimeFlow(Long flowId)throws OaException;
}
