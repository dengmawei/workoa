package org.common.workflow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;

public class PercentVoteJoinsignTask implements JoinsignTaskCommand {
	private static final String PASS_PERCENT = "passPercent";
	
	private static final String SUB_TASK_COUNT = "subTaskCount";
	
	private static final String PASS_COUNT = "passCount";
	
	private String transitionNamePass;
	
	private String transitionNameNoPass;
	
	private String parentTaskId;
	
	private Task joinsignTask;
	
	public PercentVoteJoinsignTask(String transitionNamePass,
			String transitionNameNoPass, String parentTaskId,double percent) {
		this.transitionNamePass = transitionNamePass;
		this.transitionNameNoPass = transitionNameNoPass;
		this.parentTaskId = parentTaskId;
	}

	public Boolean execute(Environment environment) throws Exception {
		TaskService taskService = environment.get(TaskService.class);
		String joinsignTaskId = joinsignTask.getId();
		
		//获取变量中的会签意见sign
		String sign = (String)taskService.getVariable(joinsignTaskId, VAR_SIGN);
		
		double percent =  (Double)taskService.getVariable(parentTaskId, PASS_PERCENT);
		int subTaskCount = (Integer)taskService.getVariable(parentTaskId, SUB_TASK_COUNT);
		int passCount = taskService.getVariable(parentTaskId, PASS_COUNT)==null?0:(Integer)taskService.getVariable(parentTaskId, PASS_COUNT);
		
		if(StringUtils.isNotBlank(sign) && ("yes".equals(sign.toLowerCase()) || "true".equals(sign.toLowerCase()))){
			taskService.completeTask(joinsignTaskId);
			taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",签名："+sign);
			
			//计算通过比例
			passCount = passCount + 1;
			BigDecimal passPercent = new BigDecimal(passCount).divide(new BigDecimal(subTaskCount)).setScale(2, RoundingMode.HALF_UP);
			
			//同意比例大于预定比例
			if(passPercent.doubleValue()>=percent){
				taskService.completeTask(parentTaskId);
				return true;
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put(PASS_COUNT, passCount);
				taskService.setVariables(parentTaskId, map);
				return false;
			}
		}
		
		taskService.completeTask(joinsignTaskId);
		taskService.addTaskComment(parentTaskId, "用户:"+joinsignTask.getAssignee()+",签名："+sign);
		
		return false;
	}

	public void setJoinsignTask(Task joinsignTask) {
		this.joinsignTask = joinsignTask;
	}

}
