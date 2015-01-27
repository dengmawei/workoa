package org.common.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.entity.Role;
import org.common.entity.view.role.RoleUserView;
import org.common.entity.view.user.UserView;
import org.common.entity.workflow.UserPendingTask;
import org.common.exception.OaException;
import org.common.service.IRoleService;
import org.common.service.IUserService;
import org.common.service.impl.RoleServiceImpl;
import org.common.service.impl.UserServiceImpl;
import org.common.service.workflow.IProcessManageService;
import org.common.service.workflow.impl.ProcessManageServiceImpl;
import org.common.util.ProcessEngineUtils;
import org.common.util.SpringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.OpenTask;

public class AssignTask implements AssignmentHandler {
	public static final String YES = "yes";
	
	public static final String NO = "no";
	/**
	 * 是否是会签：yes-是,no-否
	 */
	private String isSign;
	/**
	 * 角色名
	 */
	private String roleName;
	
	/**
	 * 用户组
	 */
	private List<String> userGroup;
	
	/**
	 * 是否是流程发起人
	 */
	private boolean isSelf = false;
	
	public void assign(Assignable assignable, OpenExecution openExecution) throws Exception {
		IProcessManageService processManageService = (ProcessManageServiceImpl)SpringUtils.getBean("processManageService");
		ProcessEngineUtils processEngineUtils = (ProcessEngineUtils)SpringUtils.getBean("processEngineUtils");
		//流程实例
		ProcessInstance instance = openExecution.getProcessInstance();
		//流程定义
		ProcessDefinition definition = processEngineUtils.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionId(instance.getProcessDefinitionId())
				.uniqueResult();
		//当前任务
		Task task = processEngineUtils.getTaskService().createTaskQuery()
				.processInstanceId(instance.getId())
				.activityName(openExecution.getName())
				.uniqueResult();
		
		List<Long> userIds = new ArrayList<Long>();
		
		//角色不为空
		if(StringUtils.isNotBlank(roleName)){
			IRoleService roleService = (RoleServiceImpl)SpringUtils.getBean("roleService");
			
			Role role = roleService.selectRoleByName(roleName);
			if(role!=null){
				List<RoleUserView> list = roleService.selectRoleUserByRoleId(role.getId());
				
				if(list!=null&& list.size()>0){
					for(RoleUserView view:list){
						userIds.add(view.getUserId());
					}
				}
			}
		}
		
		//用户组不为空
		if(userGroup!=null && userGroup.size()>0){
			IUserService userService = (UserServiceImpl)SpringUtils.getBean("userService");
			
			for(String userName:userGroup){
				try{
					UserView user = userService.selectUserByUserName(userName);
					userIds.add(user.getUserId());
					
				}catch(OaException e){
				}
			}
		}
		
		if(isSelf){
			String userId = (String)processEngineUtils.getExecutionService().getVariable(openExecution.getId(), "applicantId");
			if(StringUtils.isNotBlank(userId)){
				userIds.add(Long.parseLong(userId));
			}
		}
		
		if(StringUtils.isNotBlank(isSign) && YES.equals(isSign)){//节点会签
			for(Long userId:userIds){
				assignable.addCandidateUser(userId.toString());
				//创建子节点
				Task subTask = ((OpenTask)task).createSubTask();
				//分配子节点给任务接收人
				subTask.setAssignee(userId.toString());
				processEngineUtils.getTaskService().addTaskParticipatingUser(task.getId(), userId.toString(), Participation.CLIENT);
				
				//增加待办记录流水
				UserPendingTask pendingTask = new UserPendingTask(userId, definition.getKey(), definition.getName(), instance.getId(), task.getId(), task.getActivityName(), 0,new Date(),1);
				processManageService.saveUserPendTask(pendingTask);
			}
		}else{//节点竞争获取，默认竞争
			for(Long userId:userIds){
				assignable.addCandidateUser(userId.toString());
				
				//增加待办记录流水
				UserPendingTask pendingTask = new UserPendingTask(userId, definition.getKey(), definition.getName(), instance.getId(), task.getId(), task.getActivityName(), 0,new Date(),0);
				processManageService.saveUserPendTask(pendingTask);
			}
		}
	}
}
