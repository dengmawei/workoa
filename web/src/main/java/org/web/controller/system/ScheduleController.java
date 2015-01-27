package org.web.controller.system;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.command.schedule.ScheduleCommand;
import org.common.entity.view.PaginationView;
import org.common.entity.view.schedule.ScheduleView;
import org.common.entity.view.user.UserView;
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
import org.web.biz.system.ScheduleBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {
	@Autowired
	private ScheduleBiz scheduleBiz;
	
	/**
	 * 添加日程
	 * @param user
	 * @param scheduleCommand
	 * @return
	 */
	@RequestMapping("/saveSchedule")
	@ResponseBody
	public WebResult saveSchedule(@CurrentUser UserView user,@ModelAttribute("scheduleCommand")ScheduleCommand scheduleCommand){
		scheduleCommand.setUserId(user.getUserId());
		return scheduleBiz.saveSchedule(scheduleCommand);
	}
	
	/**
	 * 审核日程
	 * @param ids 
	 * @param status
	 * @return
	 */
	@RequiresPermissions("schedule:check")
	@RequestMapping("/checkSchedule")
	@ResponseBody
	public WebResult checkSchedule(@RequestParam("ids[]")Long[] ids,@RequestParam("status")int status){
		return scheduleBiz.checkSchedule(ids, status);
	}
	
	/**
	 * 按条件查询日程
	 * @param user
	 * @param start
	 * @param end
	 * @return
	 */
	@RequestMapping(value="/selectScheduleByCondition",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectScheduleByCondition(@CurrentUser UserView user,@RequestParam("start")String start,@RequestParam("end")String end){
		return scheduleBiz.selectScheduleByCondition(start, end, user.getUserId());
	}
	
	/**
	 * 获取日程详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/selectScheduleById/{id}")
	@ResponseBody
	public WebResult selectScheduleById(@PathVariable long id){
		return scheduleBiz.selectScheduleById(id);
	}
	
	/**
	 * 日程列表页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,@CurrentUser UserView user){
		model.addAttribute("myPersonalEvents", scheduleBiz.selectPersonalScheduleFor5(user.getUserId()));
		model.addAttribute("myWorkEvents", scheduleBiz.selectWorkScheduleFor5(user.getUserId()));
		return "schedule.page";
	}
	
	/**
	 * 日程审核页面
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/more")
	public String more(Model model,@CurrentUser UserView user){
		model.addAttribute("isPermit", SecurityUtils.getSubject().isPermitted("schedule:check"));
		model.addAttribute("userId", user.getUserId());
		return "schedule.list.page";
	}
	/**
	 * 分页查询日程
	 * @param user
	 * @return
	 */
	@RequestMapping("/selectScheduleByPage")
	@ResponseBody
	public WebResult selectScheduleByPage(@CurrentUser UserView user,@RequestParam("startTime")String startTime,
			@RequestParam("endTime")String endTime,
			@RequestParam(value="status",defaultValue="1")int status,
			@RequestParam(value="personal",defaultValue="1")int personal,
			@ModelAttribute("paginationView")PaginationView<ScheduleView> paginationView){
		
		if(StringUtils.isNotBlank(startTime)){
			paginationView.loadFilter().put("startTime", startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			paginationView.loadFilter().put("endTime", endTime);
		}
		
		if(personal==1){
			paginationView.loadFilter().put("personal", 1);
			paginationView.loadFilter().put("userId", user.getUserId());
			paginationView.loadFilter().put("status", 1);
		}else{
			paginationView.loadFilter().put("personal", 0);
			paginationView.loadFilter().put("status", status);
		}
		
		
		return scheduleBiz.selectScheduleByPage(paginationView);
	}
	
	/**
	 * 删除日程
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteSchedule",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteSchedule(@RequestParam("id")long id,@CurrentUser UserView user){
		return scheduleBiz.deleteSchedule(id,user.getUserId());
	}
}	
