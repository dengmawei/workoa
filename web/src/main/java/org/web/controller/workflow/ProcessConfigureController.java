package org.web.controller.workflow;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.entity.view.PaginationView;
import org.common.entity.workflow.ProcessConfigure;
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
import org.web.biz.flow.ProcessConfigureBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/processConfigure")
public class ProcessConfigureController extends BaseController {
	@Autowired
	private ProcessConfigureBiz processConfigureBiz;
	
	@RequiresPermissions("processConfigure:view")
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("keys", processConfigureBiz.findProcessKeys());
		return "processConfigure.page";
	}
	
	@RequiresPermissions("processConfigure:select")
	@RequestMapping(value="/selectProcessConfigureByPage",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectProcessConfigureByPage(@ModelAttribute("paginatinView")PaginationView<ProcessConfigure> paginationView,
			@RequestParam("processKey")String processKey){
		paginationView.loadFilter().put("processKey", processKey);
		return processConfigureBiz.selectProcessConfigureByPage(paginationView);
	}
	
	@RequiresPermissions("processConfigure:select")
	@RequestMapping(value="/selectProcessConfigureById/{id}",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectProcessConfigureById(@PathVariable Long id){
		return processConfigureBiz.selectById(id);
	}
	
	@RequiresPermissions("processConfigure:add")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public WebResult save(@ModelAttribute("processConfigure")ProcessConfigure processConfigure){
		return processConfigureBiz.save(processConfigure);
	}
	
	@RequiresPermissions("processConfigure:edit")
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public WebResult update(@ModelAttribute("processConfigure")ProcessConfigure processConfigure){
		return processConfigureBiz.update(processConfigure);
	}
	
	@RequiresPermissions("processConfigure:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public WebResult save(@RequestParam("id")Long id){
		return processConfigureBiz.delete(id);
	}
}
