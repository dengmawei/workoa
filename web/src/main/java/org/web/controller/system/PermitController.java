package org.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.entity.Module;
import org.common.entity.Permit;
import org.common.entity.view.PaginationView;
import org.common.entity.view.permit.PermitView;
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
import org.web.biz.system.ModuleBiz;
import org.web.biz.system.PermitBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/permit")
public class PermitController extends BaseController{
	@Autowired
	private PermitBiz permitBiz;
	@Autowired
	private ModuleBiz moduleBiz;
	
	@RequiresPermissions("module:view")
	@RequestMapping(value="/toPermit/{moduleId}")
	public String toPermitPage(@PathVariable Long moduleId,Model model){
		WebResult webResult = moduleBiz.selectModuleById(moduleId);
		if(webResult.isSuccess()){
			model.addAttribute("module", (Module)webResult.getData());
			return "permit.page";
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/module/index.html");
			return "404.page";
		}
	}
	
	@RequiresPermissions("module:select")
	@RequestMapping(value="/selectPermit",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectPermit(@RequestParam("moduleId")Long moduleId){
		return permitBiz.selectPermitByModuleId(moduleId);
	}
	
	@RequiresPermissions("module:add")
	@RequestMapping(value="/savePermit",method=RequestMethod.POST)
	@ResponseBody
	public WebResult savePermit(@ModelAttribute("permitView")PermitView permitView){
		return permitBiz.savePermit(permitView);
	}
	
	@RequiresPermissions("module:delete")
	@RequestMapping(value="/deletePermit",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deletePermit(@RequestParam("id")Long id){
		return permitBiz.deletePermit(id);
	}
	
	@RequiresPermissions("module:select")
	@RequestMapping(value="/selectPermitByPage",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectPermitByPage(@ModelAttribute("paginationView")PaginationView<Permit> paginationView,
			@RequestParam(value="moduleId",required=false,defaultValue="0")long moduleId){
		if(moduleId>0){
			paginationView.loadFilter().put("moduleId", moduleId);
		}
		return permitBiz.selectPermitByPage(paginationView);
	}
}
