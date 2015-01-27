package org.web.controller.system;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.entity.Module;
import org.common.entity.view.PaginationView;
import org.common.entity.view.module.ModuleForAddView;
import org.common.entity.view.module.ModuleForUpdateView;
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
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController{
	@Autowired
	private ModuleBiz moduleBiz;
	
	@RequiresPermissions("module:view")
	@RequestMapping(value="/index")
	public String index(Model model){
		model.addAttribute("parentList", moduleBiz.selectSuperModule());
		return "module.page";
	}
	
	@RequiresPermissions("module:select")
	@RequestMapping(value="/selectModuleByPage",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectModuleByPage(@ModelAttribute("paginationView")PaginationView<Module> paginationView,
			@RequestParam(value="moduleName",required=false)String moduleName,
			@RequestParam(value="isParent",required=false,defaultValue="-1")int isParent,
			@RequestParam("parentId")Long parentId){
		paginationView.loadFilter().put("moduleName", moduleName);
		paginationView.loadFilter().put("isParent", isParent);
		paginationView.loadFilter().put("parentId", parentId);
		return moduleBiz.selectModuleByPage(paginationView);
	}
	
	@RequiresPermissions("module:add")
	@RequestMapping(value="/toAddModule")
	public String toAddModulePage(Model model){
		model.addAttribute("superModuleList", moduleBiz.selectSuperModule());
		return "module.add.page";
	}
	
	@RequiresPermissions("module:add")
	@RequestMapping(value="/addModule")
	public String addModule(@ModelAttribute("moduleView")ModuleForAddView moduleView,Model model){
		WebResult webResult = moduleBiz.saveModule(moduleView);
		if(webResult.isSuccess()){
			return "redirect:/module/index.html";
		}else{
			model.addAttribute("result", webResult);
			model.addAttribute("moduleView", moduleView);
			model.addAttribute("superModuleList", moduleBiz.selectSuperModule());
			
			return "module.add.page";
		}
	}
	
	@RequiresPermissions("module:edit")
	@RequestMapping(value="/toEditModule/{id}")
	public String toEditModulePage(@PathVariable Long id,Model model){
		WebResult webResult = moduleBiz.selectEditModuleInfo(id);
		if(webResult.isSuccess()){
			model.addAllAttributes((Map<String,Object>)webResult.getData());
			return "module.edit.page";
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/module/index.html");
			return "404.page";
		}
	}
	
	@RequiresPermissions("module:edit")
	@RequestMapping(value="/editModule")
	public String editModule(@ModelAttribute("moduleView")ModuleForUpdateView moduleView,Model model){
		WebResult webResult = moduleBiz.editModule(moduleView);
		if(webResult.isSuccess()){
			return "redirect:/module/index.html";
		}else{
			String message = webResult.getMessage();
			webResult = moduleBiz.selectEditModuleInfo(moduleView.getId());
			Map<String,Object> map = null;
			if(webResult.isSuccess()){
				map = (Map)webResult.getData();
				map.put("message", message);
				model.addAllAttributes(map);
				return "module.edit.page";
			}else{
				returnErrorInfo(model,webResult.getMessage(), "/module/index.html");
				return "404.page";
			}
		}
	}
	
	@RequiresPermissions("module:delete")
	@RequestMapping(value="/deleteModule",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteModule(@RequestParam("id")Long id){
		return moduleBiz.deleteModule(id);
	}
	
	@RequiresPermissions("module:select")
	@RequestMapping(value="/searchModule",method=RequestMethod.POST)
	@ResponseBody
	public WebResult searchModule(@RequestParam("id")Long id){
		return moduleBiz.searchModule(id);
	}
}
