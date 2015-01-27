package org.common.workflow;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;

public class OneVetoJoinsignTask implements JoinsignTaskCommand {
	private String transitionNamePass;
	
	private String transitionNameNoPass;
	
	private String parentTaskId;
	
	private Task joinsignTask;
	
	public OneVetoJoinsignTask(String parentTaskId,String transitionNamePass,String transitionNameNoPass){
		this.parentTaskId = parentTaskId;
		this.transitionNamePass = transitionNamePass;
		this.transitionNameNoPass = transitionNameNoPass;
	}
	
	public Boolean execute(Environment environment) throws Exception {
		TaskService taskService = environment.get(TaskService.class);
		String joinsignTaskId = joinsignTask.getId();
		
		//获取变量中的会签意见sign
		String sign = (String)taskService.getVariable(joinsignTaskId, VAR_SIGN);
		
		//会签不同意，会签结束
		if(StringUtils.isNotBlank(sign) && ("no".equals(sign.toLowerCase()) || "false".equals(sign.toLowerCase()))){
			taskService.completeTask(joinsignTaskId);
			taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",签名："+sign);
			
			taskService.completeTask(parentTaskId, transitionNameNoPass);
			return true;
		}
		
		
		//会签同意
		taskService.completeTask(joinsignTaskId);
		taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",签名："+sign);
		
		//无子任务，会签结束
		if(taskService.getSubTasks(parentTaskId).size()==0){
			taskService.completeTask(parentTaskId,transitionNamePass);
			return true;
		}
		
		return false;
	}
	
	public void setJoinsignTask(Task joinsignTask){
		this.joinsignTask = joinsignTask;
	}

}
