package org.web.controller.workflow;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.command.workflow.overtime.OvertimeApproveCommand;
import org.common.command.workflow.overtime.OvertimeCommand;
import org.common.command.workflow.overtime.OvertimeRefuseModifyCommand;
import org.common.constant.OvertimeStatusEnum;
import org.common.constant.OvertimeTaskNameEnum;
import org.common.entity.view.PaginationView;
import org.common.entity.view.user.UserView;
import org.common.entity.workflow.OvertimeFlow;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.bind.annotation.CurrentUser;
import org.web.biz.flow.OvertimeFlowBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/overtime")
public class OvertimeFlowController extends BaseController{
	@Autowired
	private OvertimeFlowBiz overtimeFlowBiz;
	
	@RequestMapping("/index")
	public String index(){
		return "overtime.page";
	}
	
	/**
	 * 分页查询加班流程
	 * @param paginationView
	 * @return
	 */
	@RequestMapping(value="/selectOvertimeByPage",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectOvertimeByPage(@CurrentUser UserView user,@ModelAttribute("paginationView")PaginationView<OvertimeFlow> paginationView,
			@RequestParam(value="status",defaultValue="-1",required=true)int status,
			@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime){
		paginationView.loadFilter().put("userId", user.getUserId());
		paginationView.loadFilter().put("status", status);
		paginationView.loadFilter().put("startTime", StringUtils.isNotBlank(startTime)?startTime+" 00:00:00":"");
		paginationView.loadFilter().put("endTime", StringUtils.isNotBlank(endTime)?endTime+" 23:59:59":"");
		return overtimeFlowBiz.selectOvertimeByPage(paginationView);
	}
	
	/**
	 * 申请加班
	 * @return
	 */
	@RequestMapping("/toApplyOvertime")
	public String toApplyOvertime(){
		return "add.overtime.page";
	}
	
	/**
	 * 编辑草稿
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEditOvertime/{flowId}")
	public String toEditOvertime(@PathVariable Long flowId,Model model,HttpServletRequest request){
		WebResult webResult = overtimeFlowBiz.selectOvertimeFlowById(flowId);
		if(webResult.isSuccess()){
			model.addAttribute("overtime", webResult.getData());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/overtime/index.html?sid="+request.getParameter("sid"));
			return "404.page";
		}
		
		return "edit.overtime.page";
	}
	
	/**
	 * 加班申请详情
	 * @param flowId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toOvertimeFlowInfo")
	public String toOvertimeFlowInfo(@RequestParam("flowId")Long flowId,Model model,HttpServletRequest request){
		WebResult webResult = overtimeFlowBiz.getOvertimeInfo(flowId);
		if(webResult.isSuccess()){
			model.addAttribute("view", webResult.getData());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/overtime/index.html?sid="+request.getParameter("sid"));
			return "404.page";
		}
		return "info.overtime.page";
	}
	
	/**
	 * 提交加班申请
	 * @param overtimeFlow
	 * @return
	 */
	@RequestMapping(value="/applyOvertime",method=RequestMethod.POST)
	@ResponseBody
	public WebResult applyOvertime(@CurrentUser UserView user,@ModelAttribute("overtimeflow")OvertimeCommand overtimeCommand){
		overtimeCommand.setUserId(user.getUserId());
		overtimeCommand.setUserName(user.getUserName());
		overtimeCommand.setRealName(user.getRealName());
		overtimeCommand.setMobile(user.getMobilePhone());
		overtimeCommand.setDepartId(user.getDeptId());
		overtimeCommand.setDepartName(user.getDeptName());
		return overtimeFlowBiz.applyOvertime(overtimeCommand);
	}
	
	/**
	 * 编辑草稿
	 * @param user
	 * @param overtimeCommand
	 * @return
	 */
	@RequestMapping(value="/editOvertimeDraft",method=RequestMethod.POST)
	@ResponseBody
	public WebResult editOvertimeDraft(@CurrentUser UserView user,@ModelAttribute("overtimeflow")OvertimeCommand overtimeCommand){
		overtimeCommand.setUserId(user.getUserId());
		overtimeCommand.setUserName(user.getUserName());
		overtimeCommand.setRealName(user.getRealName());
		overtimeCommand.setMobile(user.getMobilePhone());
		return overtimeFlowBiz.editOvertimeDraft(overtimeCommand);
	}
	
	/**
	 * 删除加班申请
	 * @param flowId
	 * @return
	 */
	@RequestMapping(value="/deleteOvertime",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteOvertime(@RequestParam("flowId")Long flowId){
		return overtimeFlowBiz.deleteOvertime(flowId);
	}
	/**
	 * 部门经理审核页面
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("overtime:departCheck")
	@RequestMapping(value="/toDeaprtApprove")
	public String toDeaprtApprove(@RequestParam("flowId") Long flowId,Model model,HttpServletRequest request){
		WebResult webResult = overtimeFlowBiz.getOvertimeInfo(flowId);
		if(webResult.isSuccess()){
			model.addAttribute("view", webResult.getData());
			model.addAttribute("taskName", OvertimeTaskNameEnum.DEPART_MANAGER_APPROVE.getValue());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/overtime/index.html?sid="+request.getParameter("sid"));
			return "404.page";
		}
		return "departApprove.overtime.page";
	}
	
	/**
	 * 部门经理审核
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("overtime:departCheck")
	@RequestMapping(value="/departManageApprove",method=RequestMethod.POST)
	@ResponseBody
	public WebResult departManagerApprove(@CurrentUser UserView user,@ModelAttribute("command")OvertimeApproveCommand command){
		command.setUser(user);
		return overtimeFlowBiz.departManageApprove(command);
	}
	
	/**
	 * 总经理审核页面
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("overtime:generalCheck")
	@RequestMapping(value="/toGeneralApprove")
	public String toGeneralApprove(@RequestParam("flowId") Long flowId,Model model,HttpServletRequest request){
		WebResult webResult = overtimeFlowBiz.getOvertimeInfo(flowId);
		if(webResult.isSuccess()){
			model.addAttribute("view", webResult.getData());
			model.addAttribute("taskName", OvertimeTaskNameEnum.GENERAL_MANAGER_APPROVE.getValue());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/overtime/index.html?sid="+request.getParameter("sid"));
			return "404.page";
		}
		return "generalApprove.overtime.page";
	}
	
	/**
	 * 总经理审核
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("overtime:generalCheck")
	@RequestMapping(value="/generalManagerApprove")
	@ResponseBody
	public WebResult generalManagerApprove(@CurrentUser UserView user,@ModelAttribute("command")OvertimeApproveCommand command){
		command.setUser(user);
		return overtimeFlowBiz.generalManaerApprove(command);
	}
	
	/**
	 * 员工管理员汇总页面
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("overtime:staffCheck")
	@RequestMapping(value="/toStaffSummary")
	public String toStaffSummary(@RequestParam("flowId") Long flowId,Model model,HttpServletRequest request){
		WebResult webResult = overtimeFlowBiz.getOvertimeInfo(flowId);
		if(webResult.isSuccess()){
			model.addAttribute("view", webResult.getData());
			model.addAttribute("taskName", OvertimeTaskNameEnum.STAFF_SUMMARY.getValue());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/overtime/index.html?sid="+request.getParameter("sid"));
			return "404.page";
		}
		return "staffSummary.overtime.page";
	}
	
	/**
	 * 员工管理员统计汇总
	 * @param flowId
	 * @return
	 */
	@RequiresPermissions("overtime:staffCheck")
	@RequestMapping(value="/staffManagerApprove")
	@ResponseBody
	public WebResult staffManagerApprove(@CurrentUser UserView user,@ModelAttribute("command")OvertimeApproveCommand command){
		command.setUser(user);
		return overtimeFlowBiz.staffManaerApprove(command);
	}
	
	/**
	 * 申请人退回修改页面
	 * @param flowId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toRefuseModify")
	public String toRefuseModify(@RequestParam("flowId") Long flowId,Model model,HttpServletRequest request){
		WebResult webResult = overtimeFlowBiz.getOvertimeInfo(flowId);
		if(webResult.isSuccess()){
			model.addAttribute("view", webResult.getData());
			model.addAttribute("taskName", OvertimeTaskNameEnum.MODIFY.getValue());
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/overtime/index.html?sid="+request.getParameter("sid"));
			return "404.page";
		}
		return "refuseModify.overtime.page";
	}
	
	/**
	 * 返回修改
	 * @param flowId
	 * @param abandon
	 * @return
	 */
	@RequestMapping(value="/refuseModify",method=RequestMethod.POST)
	@ResponseBody
	public WebResult refuseModify(@ModelAttribute("command")OvertimeRefuseModifyCommand command,@CurrentUser UserView user){
		command.setMobile(user.getMobilePhone());
		command.setStatus(OvertimeStatusEnum.CHECKING.getCode());
		command.setUserId(user.getUserId());
		command.setUserName(user.getUserName());
		command.setRealName(user.getRealName());
		
		return overtimeFlowBiz.refuseModify(command);
	}
	
	@RequestMapping("/overtimeFlowDetial")
	public String overtimeFlowDetial(@RequestParam("flowId")Long flowId,Model model){
		WebResult webResult = overtimeFlowBiz.getOvertimeInfo(flowId);
		model.addAttribute("view", webResult.getData());
		
		return "detail.overtime.page";
	}
}
