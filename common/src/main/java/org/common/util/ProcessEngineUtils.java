package org.common.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.common.exception.FlowException;
import org.common.exception.workflow.ExecutionNotExistException;
import org.common.exception.workflow.ProcessInstanceNotExistException;
import org.common.exception.workflow.TaskNotExistException;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

public class ProcessEngineUtils {
	private ProcessEngine processEngine;
	private ExecutionService executionService;
	private TaskService taskService;
	private RepositoryService repositoryService;
	private ManagementService managementService;
	private IdentityService identityService;
	private HistoryService historyService;
	
	public ExecutionService getExecutionService() {
		return executionService;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	public ManagementService getManagementService() {
		return managementService;
	}
	public IdentityService getIdentityService() {
		return identityService;
	}
	public HistoryService getHistoryService() {
		return historyService;
	}
	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}
	public void initDeployment(){
		startDeployment("jbpm/overtime.jpdl.xml");
	}
	/**
	 * 发布流程
	 * @param url 
	 * @return
	 */
	public String startDeployment(String url){
		return repositoryService.createDeployment().addResourceFromClasspath(url).deploy();
	}
	
	/**
	 * 启动流程实例
	 * @param processKey
	 * @return
	 */
	public ProcessInstance startProcess(String processKey,Map<String,Object> variables){
		return executionService.startProcessInstanceByKey(processKey,variables);
	}
	
	/**
	 * 根据流程id查询流程实例
	 * @param processInstanceId
	 * @return
	 * @throws FlowException
	 */
	private ProcessInstance findProcessInstanceById(String processInstanceId)throws FlowException{
		ProcessInstance processInstance = executionService.findProcessInstanceById(processInstanceId);
		
		if(processInstance==null){
			throw new ProcessInstanceNotExistException();
		}
		
		return processInstance;
	}
	
	/**
	 * 根据流程实例id和状态名称查询活动的执行实例
	 * @param instance 流程实例
	 * @param stateName 状态名称
	 * @return
	 * @throws FlowException
	 */
	public Execution findActiveExecutionByName(String processInstanceId, String stateName)throws FlowException{
		ProcessInstance processInstance = findProcessInstanceById(processInstanceId);
		Execution execution = processInstance.findActiveExecutionIn(stateName);
		if(execution == null){
			throw new ExecutionNotExistException();
		}
		return execution;
	}
	
	/**
	 * 流程执行
	 * @param processInstanceId
	 * @param stateName
	 * @param transitionName
	 * @param nextStateName
	 * @throws FlowException
	 */
	public void signalExecution(String processInstanceId,String stateName,String transitionName)throws FlowException{
		Execution execution = findActiveExecutionByName(processInstanceId,stateName);
		ProcessInstance processInstance = executionService.signalExecutionById(execution.getId(), transitionName);
	}
	
	/**
	 * 领取任务
	 * @param processInstanceId 流程实例id
	 * @param userId 参与人id
	 * @param type 1-组 0-个人
	 * @return 任务id
	 */
	public void takeTask(String taskId,String userId) throws FlowException{
		Task task = taskService.getTask(taskId);
		
		if(task!=null && StringUtils.isBlank(task.getAssignee())){
			taskService.takeTask(task.getId(), userId);
		}
	}
	
	/**
	 * 完成任务
	 * @param taskId
	 * @throws FlowException
	 */
	public void completeTask(String taskId,String outcome,Map<String,Object> variable){
		if(StringUtils.isBlank(outcome)){
			taskService.completeTask(taskId);
		}else{
			taskService.completeTask(taskId,outcome,variable);
		}
	}
}

