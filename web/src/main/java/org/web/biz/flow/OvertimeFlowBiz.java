package org.web.biz.flow;

import java.util.Date;

import org.common.command.workflow.overtime.OvertimeApproveCommand;
import org.common.command.workflow.overtime.OvertimeCommand;
import org.common.command.workflow.overtime.OvertimeRefuseModifyCommand;
import org.common.entity.view.PaginationView;
import org.common.entity.workflow.OvertimeFlow;
import org.common.exception.FlowException;
import org.common.exception.OaException;
import org.common.exception.ParamsValidatorException;
import org.common.service.workflow.IOvertimeFlowService;
import org.common.util.OvalUtils;
import org.common.util.WebResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("overtimeFlowBiz")
public class OvertimeFlowBiz {
	@Autowired
	private IOvertimeFlowService overtimeFlowService;
	
	public WebResult applyOvertime(OvertimeCommand overtimeCommand){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(overtimeCommand);
			OvertimeFlow overtimeFlow = new OvertimeFlow();
			BeanUtils.copyProperties(overtimeCommand, overtimeFlow);
			overtimeFlow.setCreateTime(new Date());
			overtimeFlowService.saveOvertimeFlow(overtimeFlow);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		} catch (ParamsValidatorException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult editOvertimeDraft(OvertimeCommand overtimeCommand){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(overtimeCommand);
			OvertimeFlow overtimeFlow = new OvertimeFlow();
			BeanUtils.copyProperties(overtimeCommand, overtimeFlow);
			overtimeFlow.setId(overtimeCommand.getFlowId());
			overtimeFlowService.editOvertimeFlowDraft(overtimeFlow);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		} catch (ParamsValidatorException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult departManageApprove(OvertimeApproveCommand command){
		WebResult webResult = new WebResult();
		try {
			overtimeFlowService.departMangerApprove(command);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (FlowException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		
		return webResult;
	}
	
	public WebResult selectOvertimeFlowById(Long flowId){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(overtimeFlowService.selectOvertimeFlowById(flowId));
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (FlowException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public WebResult generalManaerApprove(OvertimeApproveCommand command){
		WebResult webResult = new WebResult();
		try {
			overtimeFlowService.generalManagerApprove(command);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (FlowException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		
		return webResult;
	}
	
	public WebResult staffManaerApprove(OvertimeApproveCommand command){
		WebResult webResult = new WebResult();
		try {
			overtimeFlowService.staffOvertimeSummary(command);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (FlowException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		
		return webResult;
	}
	
	public WebResult selectOvertimeByPage(PaginationView<OvertimeFlow> paginationView){
		WebResult webResult = new WebResult();
		overtimeFlowService.selectOvertimeFlowByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public WebResult refuseModify(OvertimeRefuseModifyCommand command){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(command);
			overtimeFlowService.refuseModify(command);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (FlowException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (ParamsValidatorException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		
		return webResult;
	}
	
	public WebResult getOvertimeInfo(Long flowId){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(overtimeFlowService.getOvertimeFlowInfo(flowId));
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public WebResult deleteOvertime(Long flowId){
		WebResult webResult = new WebResult();
		try {
			overtimeFlowService.deleteOvertimeFlow(flowId);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
}
