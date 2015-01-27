package org.common.workflow;

import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;

public class AdviseJoinsignTask implements JoinsignTaskCommand {
	private String parentTaskId;
	
	private String message;
	
	private Task joinsignTask;
	
	public AdviseJoinsignTask(String parentTaskId, String message) {
		this.parentTaskId = parentTaskId;
		this.message = message;
	}

	public Boolean execute(Environment environment) throws Exception {
		TaskService taskService = environment.get(TaskService.class);
		String joinsignTaskId = joinsignTask.getId();
		
		taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",审批意见："+message);
		
		taskService.completeTask(joinsignTaskId);
		
		if(taskService.getSubTasks(parentTaskId).size()==0){
			taskService.completeTask(parentTaskId);
			return true;
		}
		return false;
	}

	public void setJoinsignTask(Task joinsignTask) {
		this.joinsignTask = joinsignTask;
	}

}
