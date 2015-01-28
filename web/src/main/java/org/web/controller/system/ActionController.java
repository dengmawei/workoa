package org.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.entity.Action;
import org.common.entity.view.PaginationView;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.biz.system.ActionBiz;
import org.web.controller.base.BaseController;


@Controller
@RequestMapping("/action")
public class ActionController extends BaseController{
	@Autowired
	private ActionBiz actionBiz;
	
	@RequiresPermissions("action:view")
	@RequestMapping(value="/index")
	public String index(){
		return "action.page";
	}
	
	@RequiresPermissions("action:add")
	@RequestMapping(value="/saveAction")
	@ResponseBody
	public WebResult saveAction(@ModelAttribute("action")Action action){
		return actionBiz.saveAction(action);
	}
	
	@RequiresPermissions("action:edit")
	@RequestMapping(value="/editAction")
	@ResponseBody
	public WebResult editAction(@ModelAttribute("action")Action action){
		return actionBiz.editAction(action);
	}
	
	@RequiresPermissions("action:delete")
	@RequestMapping(value="/deleteAction")
	@ResponseBody
	public WebResult deleteAction(@RequestParam("id")Long id){
		return actionBiz.deleteAction(id);
	}
	
	@RequiresPermissions("action:select")
	@RequestMapping(value="/selectAction")
	@ResponseBody
	public WebResult saveAction(@RequestParam("id")Long id){
		return actionBiz.selectActionById(id);
	}
	
	@RequiresPermissions("action:select")
	@RequestMapping(value="/selectActionByPage")
	@ResponseBody
	public WebResult selectActionByPage(@ModelAttribute("paginationView")PaginationView<Action> paginationView){
		return actionBiz.selectActionByPage(paginationView);
	} 
}
