package org.web.controller.workflow;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.entity.view.PaginationView;
import org.common.entity.view.user.UserView;
import org.common.entity.workflow.SuperFlowProcess;
import org.common.entity.workflow.UserPendingTask;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.bind.annotation.CurrentUser;
import org.web.biz.flow.ProcessConfigureBiz;
import org.web.biz.flow.ProcessManageBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/processManage")
public class ProcessManageController extends BaseController{
	@Autowired
	private ProcessManageBiz processManageBiz;
	
	@Autowired
	private ProcessConfigureBiz processConfigureBiz;
	
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("processTypeList", processConfigureBiz.findProcessKeys());
		return "process.page";
	}
	/**
	 * 查询待办任务列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/findTaskInProcess",method=RequestMethod.POST)
	@ResponseBody
	public WebResult findTaskInProcess(@CurrentUser UserView user,PaginationView<UserPendingTask> paginationView,
			@RequestParam("processType")String processType,@RequestParam("processStatus")int processStatus){
		paginationView.loadFilter().put("processType", processType);
		paginationView.loadFilter().put("processStatus", processStatus);
		paginationView.loadFilter().put("userId", user.getUserId().toString());
		return processManageBiz.findTaskInstanceInProcess(paginationView);
	}
	
	/**
	 * 处理待办
	 * @param taskId
	 * @param user
	 * @return
	 */
	@RequestMapping("/dealTask")
	@ResponseBody
	public WebResult dealTask(@RequestParam("taskId")String taskId,@CurrentUser UserView user){
		return processManageBiz.dealTask(taskId);
	}
	
	/**
	 * 查看已办
	 * @param taskId
	 * @param user
	 * @return
	 */
	@RequestMapping("/searchTask")
	@ResponseBody
	public WebResult searchTask(@RequestParam("taskId")String taskId,@CurrentUser UserView user){
		return processManageBiz.searchTask(taskId);
	}
	
	/**
	 * 流程管理中心
	 * @param model
	 * @return
	 */
	@RequiresPermissions("manageCenter:view")
	@RequestMapping("/manageCenter")
	public String manageCenter(Model model){
		model.addAttribute("processTypeList", processConfigureBiz.findProcessKeys());
		return "process.manage.page";
	}
	
	/**
	 * 分页查询所有流程
	 * @param paginationView
	 * @param startTime
	 * @param endTime
	 * @param processKey
	 * @param processId
	 * @param applierId
	 * @return
	 */
	@RequiresPermissions("manageCenter:select")
	@RequestMapping(value="/selectAllTaskProcess",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectAllTaskProcess(@ModelAttribute("paginationView")PaginationView<SuperFlowProcess> paginationView,
			@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime,
			@RequestParam("processKey")String processKey,@RequestParam("applierId")Long applierId){
		paginationView.loadFilter().put("startTime", startTime);
		paginationView.loadFilter().put("endTime", endTime);
		paginationView.loadFilter().put("processKey", processKey);
		if(applierId!=null){
			paginationView.loadFilter().put("applierId", applierId);
		}
		
		return processManageBiz.findAllTaskProcess(paginationView);
	}
	
	/**
	 * 任务详情
	 * @param flowId
	 * @param processKey
	 * @param model
	 * @return
	 */
	@RequiresPermissions("manageCenter:select")
	@RequestMapping("/processDetail")
	public String processDetail(@RequestParam("flowId")Long flowId,@RequestParam("processKey")String processKey,Model model){
		WebResult webResult = processManageBiz.findProcessDetial(flowId, processKey);
		if(webResult.isSuccess()){
			model.addAttribute("detailUrl", webResult.getData());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "");
			return "404.page";
		}
		return "process.info.page";
	}
}
