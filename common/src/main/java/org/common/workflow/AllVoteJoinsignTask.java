package org.common.workflow;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;

public class AllVoteJoinsignTask implements JoinsignTaskCommand {
	private String transitionNamePass;
	
	private String transitionNameNoPass;
	
	private String parentTaskId;
	
	private Task joinsignTask;
	
	public AllVoteJoinsignTask(String transitionNamePass,
			String transitionNameNoPass, String parentTaskId) {
		this.transitionNamePass = transitionNamePass;
		this.transitionNameNoPass = transitionNameNoPass;
		this.parentTaskId = parentTaskId;
	}

	public Boolean execute(Environment environment) throws Exception {
		TaskService taskService = environment.get(TaskService.class);
		String joinsignTaskId = joinsignTask.getId();
		
		//获取变量中的会签意见sign
		String sign = (String)taskService.getVariable(joinsignTaskId, VAR_SIGN);
		
		//会签同意，会签结束
		if(StringUtils.isNotBlank(sign) && ("yes".equals(sign.toLowerCase()) || "true".equals(sign.toLowerCase()))){
			taskService.completeTask(joinsignTaskId);
			taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",签名："+sign);
			
			taskService.completeTask(parentTaskId, transitionNamePass);
			return true;
		}
		
		taskService.completeTask(joinsignTaskId);
		taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",签名："+sign);
		
		if(taskService.getSubTasks(parentTaskId).size()==0){
			taskService.completeTask(parentTaskId, transitionNameNoPass);
			return true;
		}
		
		return false;
	}

	public void setJoinsignTask(Task joinsignTask) {
		this.joinsignTask = joinsignTask;
	}

}
