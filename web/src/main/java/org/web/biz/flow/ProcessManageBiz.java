package org.web.biz.flow;

import org.common.entity.view.PaginationView;
import org.common.entity.workflow.SuperFlowProcess;
import org.common.entity.workflow.UserPendingTask;
import org.common.exception.FlowException;
import org.common.service.workflow.IProcessManageService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("processManageBiz")
public class ProcessManageBiz {
	@Autowired
	private IProcessManageService processManageService;
	
	public WebResult findTaskInstanceInProcess(PaginationView<UserPendingTask> paginationView){
		WebResult webResult = new WebResult();
		processManageService.findTaskInstanceInProcess(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public WebResult dealTask(String taskId){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(processManageService.dealTask(taskId));
			webResult.success();
		} catch (FlowException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult searchTask(String taskId){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(processManageService.searchTask(taskId));
			webResult.success();
		} catch (FlowException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult findAllTaskProcess(PaginationView<SuperFlowProcess> paginationView){
		WebResult webResult = new WebResult();
		try {
			processManageService.findAllTaskProcess(paginationView);
			webResult.setData(paginationView.getPaginationDate());
			webResult.success();
		} catch (FlowException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult findProcessDetial(Long flowId,String processKey){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(processManageService.loadFlowDetialUrl(flowId, processKey));
			webResult.success();
		} catch (FlowException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		
		return webResult;
	}
}
