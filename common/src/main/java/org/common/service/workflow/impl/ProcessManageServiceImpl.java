package org.common.service.workflow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.command.workflow.CompleteTaskCommand;
import org.common.command.workflow.StartProcessCommand;
import org.common.constant.JoinsignTypeEnum;
import org.common.constant.OvertimeTaskNameEnum;
import org.common.constant.SignTypeEnum;
import org.common.constant.WorkflowProcessKeyEnum;
import org.common.dao.workflow.ProcessConfigureRepository;
import org.common.dao.workflow.SuperFlowProcessRepository;
import org.common.dao.workflow.UserPendingTaskRepository;
import org.common.entity.view.PaginationView;
import org.common.entity.workflow.ProcessConfigure;
import org.common.entity.workflow.SuperFlowProcess;
import org.common.entity.workflow.UserPendingTask;
import org.common.exception.FlowException;
import org.common.exception.workflow.ProcessNotException;
import org.common.exception.workflow.TaskNotExistException;
import org.common.service.workflow.IProcessManageService;
import org.common.service.workflow.IProcessTaskService;
import org.common.util.ProcessEngineUtils;
import org.common.workflow.AdviseJoinsignTask;
import org.common.workflow.AllVoteJoinsignTask;
import org.common.workflow.JoinsignTaskCommand;
import org.common.workflow.OneVetoJoinsignTask;
import org.common.workflow.PercentVoteJoinsignTask;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("processManageService")
public class ProcessManageServiceImpl implements IProcessManageService {
	@Autowired
	private OvertimeFlowServiceImpl overtimeFlowService;
	
	@Autowired
	private ProcessConfigureRepository processConfigureDao;
	
	@Autowired
	private SuperFlowProcessRepository superFlowProcessDao;
	
	@Autowired
	private ProcessEngineUtils processEngineUtils;
	
	@Autowired
	private UserPendingTaskRepository userPendingTaskDao;
	
	public void findTaskInstanceInProcess(PaginationView<UserPendingTask> paginationView) {
		paginationView.setCount(userPendingTaskDao.selectUserPendingTaskCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(userPendingTaskDao.selectUserPendingTaskListByPage(paginationView.loadFilter()));
	}

	public String dealTask(String taskId)throws FlowException {
		Task task = processEngineUtils.getTaskService().getTask(taskId);
		
		if(task==null){
			throw new TaskNotExistException();
		}
		
		String processKey = task.getExecutionId().substring(0, task.getExecutionId().indexOf("."));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("processKey", processKey);
		map.put("taskName", task.getName());
		ProcessConfigure processConfigure = processConfigureDao.selectProcessConfigureByCondition(map);
		
		Long flowId = getFlowIdByProcessId(task.getExecutionId(),getProcessTaskServiceByKey(processKey));;
		
		if(flowId==null){
			throw new TaskNotExistException();
		}
		
		return processConfigure.getTaskDealUrl().concat("?flowId=").concat(flowId.toString());
	}
	
	public String searchTask(String taskId) throws FlowException {
		HistoryTask task = (HistoryTask)processEngineUtils.getHistoryService().createHistoryTaskQuery().taskId(taskId).uniqueResult();
		
		if(task==null){
			throw new TaskNotExistException();
		}
		
		String processKey = task.getExecutionId().substring(0, task.getExecutionId().indexOf("."));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("processKey", processKey);
		map.put("taskName", "info");
		ProcessConfigure processConfigure = processConfigureDao.selectProcessConfigureByCondition(map);
		
		Long flowId = getFlowIdByProcessId(task.getExecutionId(),getProcessTaskServiceByKey(processKey));;
		
		if(flowId==null){
			throw new TaskNotExistException();
		}
		
		return processConfigure.getTaskDealUrl().concat("?flowId=").concat(flowId.toString());
	}

	/**
	 * 根据流程id查询实例id
	 * @param processId
	 * @param processTaskService
	 * @return
	 */
	private long getFlowIdByProcessId(String processId,IProcessTaskService processTaskService){
		return processTaskService.getFlowIdByProcessInstanceId(processId);
	}
	
	/**
	 * 通过key来获取业务实例
	 * @param processKey
	 * @return
	 * @throws FlowException
	 */
	private IProcessTaskService getProcessTaskServiceByKey(String processKey)throws FlowException{
		if(processKey.equals(WorkflowProcessKeyEnum.OVERTIME.getKey())){
			return overtimeFlowService;
		}
		
		throw new ProcessNotException();
	}

	public void findAllTaskProcess(PaginationView<SuperFlowProcess> paginationView)throws FlowException {
		paginationView.setCount(superFlowProcessDao.selectFlowProcessCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(superFlowProcessDao.selectFlowProcessListByPage(paginationView.loadFilter()));
	}

	public String loadFlowDetialUrl(Long superflowId, String processKey)throws FlowException {
		StringBuilder builder = new StringBuilder();
		IProcessTaskService processTaskService = getProcessTaskServiceByKey(processKey);
		long flowId = processTaskService.findFlowIdBySuperFlowId(superflowId);
		if(WorkflowProcessKeyEnum.OVERTIME.getKey().equals(processKey)){
			builder.append("/overtime/overtimeFlowDetial.html?flowId=").append(flowId);
		}
		
		return builder.toString();
	}

	public void saveUserPendTask(UserPendingTask task) {
		userPendingTaskDao.save(task);
	}

	public String startProcess(StartProcessCommand command) {
		if(command.getVariables()==null){
			Map<String,Object> variables = new HashMap<String,Object>();
			command.setVariables(variables);
		}
		
		command.getVariables().put("applicantId", command.getUserId().toString());
		
		ProcessInstance processInstance = processEngineUtils.startProcess(
				command.getProcessKey(), command.getVariables());
		
		superFlowProcessDao.save(command.getSuperFlowProcess());
		return processInstance.getId();
	}

	public void completeTask(CompleteTaskCommand command) throws FlowException {
		//1.获取并领取任务
		Task task = processEngineUtils.getTaskService().createTaskQuery().processInstanceId(command.getProcessId())
				.activityName(command.getActivityName()).uniqueResult();
		processEngineUtils.takeTask(task.getId(),command.getExecutorId().toString());
		
		//2.修改用户待办的状态为已办
		Map<String,Object> params = new HashMap<String,Object>();
		if(command.getSignType()==SignTypeEnum.COUNTERSIGN.getKey()){//如果是会签则只修改自己的待办，否则修改该任务下所有参与者的待办为已办
			params.put("userId", command.getExecutorId());
		}
		params.put("status", 1);
		params.put("processId", command.getProcessId());
		params.put("taskId", task.getId());
		params.put("executorId", command.getExecutorId());
		
		userPendingTaskDao.updateUserPendingTaskStatusByCondition(params);
		
		//3.完成任务
		if(command.getSignType()==SignTypeEnum.COMPETITION.getKey()){//竞争
			if(command.getStatus()==1){
				processEngineUtils.completeTask(task.getId(),command.getTransitionNamePass(),command.getVariables());
			}else{
				processEngineUtils.completeTask(task.getId(),command.getTransitionNameNoPass(),command.getVariables());
			}
		}else{//会签
			//3.1创建一票否决制command
			JoinsignTaskCommand taskCommand = null;
			if(command.getJoinsignType()==JoinsignTypeEnum.ONEVOTE_JOINSIGN.getCode()){
				taskCommand = new OneVetoJoinsignTask(task.getId(), command.getTransitionNamePass(), command.getTransitionNameNoPass());
			}else if(command.getJoinsignType()==JoinsignTypeEnum.ALLVOTE_JOINSIGN.getCode()){
				taskCommand = new AllVoteJoinsignTask(command.getTransitionNamePass(), command.getTransitionNameNoPass(), task.getId());
			}else if(command.getJoinsignType()==JoinsignTypeEnum.ADVISE_JOINSIGN.getCode()){
				taskCommand = new AdviseJoinsignTask(task.getId(), command.getMessage());
			}else{
				taskCommand = new PercentVoteJoinsignTask(command.getTransitionNamePass(), command.getTransitionNameNoPass(), task.getId(), command.getPrecent());
			}
			
			//3.2.获取子节点
			Task joinsignTask = null;
			List<Task> subTaskList =processEngineUtils.getTaskService().getSubTasks(task.getId());
			if(subTaskList!=null && subTaskList.size()>0){
				for(Task subTask:subTaskList){
					if(subTask.getAssignee().equals(command.getExecutorId().toString())){
						joinsignTask = subTask;
					}
				}
			}
			
			if(joinsignTask==null){
				throw new TaskNotExistException();
			}
			
			//3.3设置子节点
			taskCommand.setJoinsignTask(joinsignTask);
			
			//3.4完成任务
			processEngineUtils.getTaskService().setVariables(task.getId(), command.getVariables());
			processEngineUtils.getTaskService().setVariables(joinsignTask.getId(), command.getVariables());
			processEngineUtils.getProcessEngine().execute(taskCommand);
		}
	}
}
