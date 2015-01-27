package org.common.service.workflow.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.common.command.workflow.CompleteTaskCommand;
import org.common.command.workflow.StartProcessCommand;
import org.common.command.workflow.overtime.OvertimeApproveCommand;
import org.common.command.workflow.overtime.OvertimeRefuseModifyCommand;
import org.common.constant.JoinsignTypeEnum;
import org.common.constant.OvertimeStatusEnum;
import org.common.constant.OvertimeTaskNameEnum;
import org.common.constant.SignTypeEnum;
import org.common.constant.WorkflowProcessKeyEnum;
import org.common.dao.workflow.OvertimeApproveRepository;
import org.common.dao.workflow.OvertimeFlowRepository;
import org.common.dao.workflow.UserPendingTaskRepository;
import org.common.entity.view.PaginationView;
import org.common.entity.view.workflow.OvertimeFlowView;
import org.common.entity.workflow.OvertimeApprove;
import org.common.entity.workflow.OvertimeFlow;
import org.common.entity.workflow.SuperFlowProcess;
import org.common.exception.FlowException;
import org.common.exception.OaException;
import org.common.exception.workflow.TaskNotExistException;
import org.common.exception.workflow.overtime.OvertimeNotExistException;
import org.common.exception.workflow.overtime.OvertimeTimezoneConflictException;
import org.common.service.workflow.IOvertimeFlowService;
import org.common.service.workflow.IProcessManageService;
import org.common.service.workflow.IProcessTaskService;
import org.common.util.ProcessEngineUtils;
import org.common.workflow.OneVetoJoinsignTask;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("overtimeFlowService")
public class OvertimeFlowServiceImpl implements IOvertimeFlowService,IProcessTaskService {
	public static final BigDecimal times = new BigDecimal(1000*60*60);
	@Autowired
	private OvertimeFlowRepository overtimeDao;
	
	@Autowired
	private OvertimeApproveRepository overtimeApproveDao;
	
	@Autowired
	private UserPendingTaskRepository userPendingTaskDao;
	
	@Autowired
	private ProcessEngineUtils processEngineUtils;
	
	@Autowired
	private IProcessManageService processManageService;
	
	@Transactional(rollbackFor=Exception.class)
	public void saveOvertimeFlow(OvertimeFlow overtimeFlow)throws OaException {
		//获取开始时间和结束时间内的加班记录，判断是否有时间冲突
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", overtimeFlow.getUserId());
		param.put("startTime", DateFormatUtils.format(overtimeFlow.getStartTime(), "yyyy-MM-dd HH:mm"));
		param.put("endTime", DateFormatUtils.format(overtimeFlow.getEndTime(), "yyyy-MM-dd HH:mm"));
		List<OvertimeFlow> list = overtimeDao.selectOvertimeListByCondition(param);
		
		if(list!=null && list.size()>0){
			throw new OvertimeTimezoneConflictException();
		}
		
		BigDecimal interval = new BigDecimal(overtimeFlow.getEndTime().getTime()-overtimeFlow.getStartTime().getTime());
		//设置加班时长
		overtimeFlow.setHours(interval.divide(times,1,BigDecimal.ROUND_HALF_UP).doubleValue());
		
		if(overtimeFlow.getStatus()==OvertimeStatusEnum.CHECKING.getCode()){
			//启动参数
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("target", "toDepartApprove");
			
			//流程记录
			SuperFlowProcess flowProcess = new SuperFlowProcess(overtimeFlow.getUserId(), overtimeFlow.getUserName(),
					overtimeFlow.getRealName(), new Date(), WorkflowProcessKeyEnum.OVERTIME.getKey(), WorkflowProcessKeyEnum.OVERTIME.getDesc());

			//流程启动对象
			StartProcessCommand startProcessCommand = new StartProcessCommand(overtimeFlow.getUserId(),WorkflowProcessKeyEnum.OVERTIME.getKey(), variables, flowProcess);
			
			//启动流程
			String processId = processManageService.startProcess(startProcessCommand);
			
			//保存加班业务对象
			overtimeFlow.setProcessId(processId);
			overtimeFlow.setSuperFlowId(flowProcess.getId());
			overtimeDao.save(overtimeFlow);
		}else if(overtimeFlow.getStatus()==OvertimeStatusEnum.DRAFT.getCode()){
			overtimeDao.save(overtimeFlow);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void editOvertimeFlowDraft(OvertimeFlow overtimeFlow)
			throws OaException {
		//获取开始时间和结束时间内的加班记录，判断是否有时间冲突
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", overtimeFlow.getUserId());
		param.put("startTime", DateFormatUtils.format(overtimeFlow.getStartTime(), "yyyy-MM-dd HH:mm"));
		param.put("endTime", DateFormatUtils.format(overtimeFlow.getEndTime(),"yyyy-MM-dd HH:mm"));
		param.put("flowId", overtimeFlow.getId());
		List<OvertimeFlow> list = overtimeDao.selectOvertimeListByCondition(param);

		if (list != null && list.size() > 0) {
			throw new OvertimeTimezoneConflictException();
		}
		
		//设置加班时长
		BigDecimal interval = new BigDecimal(overtimeFlow.getEndTime().getTime()-overtimeFlow.getStartTime().getTime());
		overtimeFlow.setHours(interval.divide(times,1,BigDecimal.ROUND_HALF_UP).doubleValue());
		
		if(overtimeFlow.getStatus()==OvertimeStatusEnum.CHECKING.getCode()){
			//启动参数
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("target", "toDepartApprove");

			//流程记录
			SuperFlowProcess flowProcess = new SuperFlowProcess(overtimeFlow.getUserId(), overtimeFlow.getUserName(),
					overtimeFlow.getRealName(), new Date(), WorkflowProcessKeyEnum.OVERTIME.getKey(), WorkflowProcessKeyEnum.OVERTIME.getDesc());
			
			//启动流程
			StartProcessCommand startProcessCommand = new StartProcessCommand(overtimeFlow.getUserId(),WorkflowProcessKeyEnum.OVERTIME.getKey(), variables, flowProcess);
			String processId = processManageService.startProcess(startProcessCommand);
			
			//保存加班业务对象
			overtimeFlow.setProcessId(processId);
			overtimeFlow.setSuperFlowId(flowProcess.getId());
			overtimeDao.update(overtimeFlow);
		}else if(overtimeFlow.getStatus()==OvertimeStatusEnum.DRAFT.getCode()){
			overtimeDao.update(overtimeFlow);
		}
	}

	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public void departMangerApprove(OvertimeApproveCommand command) throws OaException,FlowException{
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(command.getFlowId());
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}

		if(command.getStatus()==0){
			overtimeFlow.setStatus(OvertimeStatusEnum.REFUSED.getCode());
			overtimeDao.update(overtimeFlow);
		}
		
		
		//保存审核记录
		OvertimeApprove overtimeApprove = new OvertimeApprove(overtimeFlow.getUserId(), overtimeFlow.getRealName(), 
				command.getUser().getUserId(), command.getUser().getRealName(), new Date(), StringUtils.isBlank(command.getAuditContent())?"":command.getAuditContent(), 
				command.getStatus(), command.getFlowId(), overtimeFlow.getProcessId(), OvertimeTaskNameEnum.DEPART_MANAGER_APPROVE.getValue());

		overtimeApproveDao.save(overtimeApprove);
		
		Map<String,Object> variables = new HashMap<String,Object>();
		if(command.getStatus()==1){
		}else{
			variables.put("target", "toModify");
		}
		
		//完成任务
		CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(overtimeFlow.getProcessId(), OvertimeTaskNameEnum.DEPART_MANAGER_APPROVE.getKey(), 
				"toGeneralApprove","toReturn", variables, command.getUser().getUserId(), SignTypeEnum.COMPETITION.getKey(),command.getStatus());
		processManageService.completeTask(completeTaskCommand);
	}

	@Transactional(rollbackFor=Exception.class)
	public void generalManagerApprove(OvertimeApproveCommand command)throws OaException,FlowException {
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(command.getFlowId());
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}
		
		if(command.getStatus()==0){
			overtimeFlow.setStatus(OvertimeStatusEnum.REFUSED.getCode());
			overtimeDao.update(overtimeFlow);
		}
		
		//保存审核记录
		OvertimeApprove overtimeApprove = new OvertimeApprove(overtimeFlow.getUserId(), overtimeFlow.getRealName(), 
				command.getUser().getUserId(), command.getUser().getRealName(), new Date(), StringUtils.isBlank(command.getAuditContent())?"":command.getAuditContent(), 
				command.getStatus(), command.getFlowId(), overtimeFlow.getProcessId(), OvertimeTaskNameEnum.GENERAL_MANAGER_APPROVE.getValue());
				
		overtimeApproveDao.save(overtimeApprove);
		
		//完成任务
		Map<String,Object> variables = new HashMap<String,Object>();
		if(command.getStatus()==1){
			variables.put("sign", "yes");
		}else{
			variables.put("sign", "no");
			variables.put("target", "toModify");
		}
		CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(overtimeFlow.getProcessId(), OvertimeTaskNameEnum.GENERAL_MANAGER_APPROVE.getKey(), 
				"toStaffApprove","toReturn", variables, command.getUser().getUserId(), SignTypeEnum.COUNTERSIGN.getKey(),command.getStatus());
		completeTaskCommand.setJoinsignType(JoinsignTypeEnum.ONEVOTE_JOINSIGN.getCode());
		
		processManageService.completeTask(completeTaskCommand);
	}

	@Transactional(rollbackFor=Exception.class)
	public void staffOvertimeSummary(OvertimeApproveCommand command)throws OaException,FlowException {
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(command.getFlowId());
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}
		
		overtimeFlow.setStatus(OvertimeStatusEnum.PASS.getCode());
		overtimeDao.update(overtimeFlow);
		//保存审核记录
		OvertimeApprove overtimeApprove = new OvertimeApprove(overtimeFlow.getUserId(), overtimeFlow.getRealName(), 
				command.getUser().getUserId(), command.getUser().getRealName(), new Date(), StringUtils.isBlank(command.getAuditContent())?"":command.getAuditContent(), 
				command.getStatus(), command.getFlowId(), overtimeFlow.getProcessId(), OvertimeTaskNameEnum.STAFF_SUMMARY.getValue());
				
		overtimeApproveDao.save(overtimeApprove);
		
		//完成任务
		CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(overtimeFlow.getProcessId(), OvertimeTaskNameEnum.STAFF_SUMMARY.getKey(), 
				"toEnd", "null", null, command.getUser().getUserId(), SignTypeEnum.COMPETITION.getKey(), command.getStatus());
		processManageService.completeTask(completeTaskCommand);
	}

	@Transactional(rollbackFor=Exception.class)
	public void refuseModify(OvertimeRefuseModifyCommand command) throws OaException, FlowException {
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(command.getFlowId());
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}
		
		if(command.getAbandon()==1){
			overtimeFlow.setStatus(OvertimeStatusEnum.FAILED.getCode());
		}else{
			overtimeFlow.setEndTime(command.getEndTime());
			overtimeFlow.setOvertimeReason(command.getOvertimeReason());
			overtimeFlow.setStartTime(command.getStartTime());
			overtimeFlow.setStatus(OvertimeStatusEnum.CHECKING.getCode());
			BigDecimal interval = new BigDecimal(overtimeFlow.getEndTime().getTime()-overtimeFlow.getStartTime().getTime());
			//获取加班时长
			overtimeFlow.setHours(interval.divide(times,1,BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		
		overtimeDao.update(overtimeFlow);
		//保存审核记录
		OvertimeApprove overtimeApprove = new OvertimeApprove(overtimeFlow.getUserId(), overtimeFlow.getRealName(), 
				command.getUserId(), command.getRealName(), new Date(), "", 
				1, command.getFlowId(), overtimeFlow.getProcessId(), OvertimeTaskNameEnum.MODIFY.getValue());
				
		overtimeApproveDao.save(overtimeApprove);
		
		//完成任务
		Map<String,Object> variables = new HashMap<String,Object>();
		if(command.getAbandon()==1){
			variables.put("target", "toEnd");
		}else{
			variables.put("target", "toDepartApprove");
		}
		
		CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(overtimeFlow.getProcessId(), OvertimeTaskNameEnum.MODIFY.getKey(), 
				"toSub", null, variables, command.getUserId(), SignTypeEnum.COMPETITION.getKey(), 1); 
		processManageService.completeTask(completeTaskCommand);
		
	}

	public OvertimeFlow selectOvertimeFlowById(Long flowId)throws OaException,FlowException {
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(flowId);
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}
		
		return overtimeFlow;
	}

	public void selectOvertimeFlowByPage(PaginationView<OvertimeFlow> paginationView) {
		paginationView.setCount(overtimeDao.selectOvertimeCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(overtimeDao.selectOvertimeListByPage(paginationView.loadFilter()));
	}
	
	public OvertimeFlowView getOvertimeFlowInfo(Long flowId) throws OaException {
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(flowId);
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}
		
		OvertimeFlowView view = new OvertimeFlowView();
		view.setOvertimeFlow(overtimeFlow);
		view.setOvertimeApproveList(overtimeApproveDao.selectListByFlowId(flowId));
		
		return view;
	}

	public void deleteOvertimeFlow(Long flowId) throws OaException {
		OvertimeFlow overtimeFlow = overtimeDao.selectOvertimeFlowById(flowId);
		if(overtimeFlow==null){
			throw new OvertimeNotExistException();
		}
		
		overtimeDao.deleteOvertimeFlow(flowId);
	}

	public long getFlowIdByProcessInstanceId(String processId) {
		OvertimeFlow flow = overtimeDao.selectOvertimeFlowByProcessId(processId);
		return flow.getId();
	}

	public long findFlowIdBySuperFlowId(Long superFlowId) {
		return overtimeDao.selectFlowIdBySuperFlowId(superFlowId);
	}
}
