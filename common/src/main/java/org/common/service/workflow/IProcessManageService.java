package org.common.service.workflow;

import java.util.Map;

import org.common.command.workflow.CompleteTaskCommand;
import org.common.command.workflow.StartProcessCommand;
import org.common.entity.view.PaginationView;
import org.common.entity.workflow.SuperFlowProcess;
import org.common.entity.workflow.UserPendingTask;
import org.common.exception.FlowException;

public interface IProcessManageService {
	/**
	 * 查询代办列表
	 * @param paginationView
	 */
	void findTaskInstanceInProcess(PaginationView<UserPendingTask> paginationView);
	
	/**
	 * 处理待办
	 * @param taskId
	 * @return
	 */
	String dealTask(String taskId)throws FlowException;
	
	/**
	 * 查看已办
	 * @param taskId
	 * @return
	 * @throws FlowException
	 */
	String searchTask(String taskId)throws FlowException;
	
	/**
	 * 查询所有的流程
	 * @param paginationView
	 */
	void findAllTaskProcess(PaginationView<SuperFlowProcess> paginationView)throws FlowException;
	
	/**
	 * 根据父流程id和流程key查询相应流程的详情url
	 * @param flowId
	 * @param processKey
	 * @return
	 */
	String loadFlowDetialUrl(Long superflowId,String processKey)throws FlowException;
	
	/**
	 * 保存用户代办任务
	 * @param task
	 */
	void saveUserPendTask(UserPendingTask task);
	
	/**
	 * 启动流程
	 * @param command 流程启动对象
	 * @return
	 */
	String startProcess(StartProcessCommand command); 
	
	/**
	 * 完成任务
	 * @param command 完成任务对象
	 * @throws FlowException
	 */
	void completeTask(CompleteTaskCommand command)throws FlowException;
}
