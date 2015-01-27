package org.web.biz.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.entity.Module;
import org.common.entity.view.PaginationView;
import org.common.entity.view.module.ModuleForAddView;
import org.common.entity.view.module.ModuleForUpdateView;
import org.common.exception.OaException;
import org.common.exception.ParamsValidatorException;
import org.common.service.IModuleService;
import org.common.util.OvalUtils;
import org.common.util.WebResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("moduleBiz")
public class ModuleBiz {
	@Autowired
	private IModuleService moduleService;
	
	public WebResult saveModule(ModuleForAddView moduleView){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(moduleView);
			Module module = new Module();
			BeanUtils.copyProperties(moduleView, module);
			moduleService.saveModule(module);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (ParamsValidatorException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		
		return webResult;
	}
	
	public WebResult editModule(ModuleForUpdateView moduleView){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(moduleView);
			moduleService.editModule(moduleView);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} catch (ParamsValidatorException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		} 
		return webResult;
	}
	
	public WebResult deleteModule(Long id){
		WebResult webResult = new WebResult();
		try {
			moduleService.deleteModule(id);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public WebResult selectModuleByPage(PaginationView<Module> paginationView){
		WebResult webResult = new WebResult();
		moduleService.selectModuleByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public WebResult selectEditModuleInfo(Long id){
		WebResult webResult = new WebResult();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("superModuleList", moduleService.selectAllSuperModule());
			map.put("module", moduleService.selectModuleById(id));
			webResult.setData(map);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public List<Module> selectSuperModule(){
		return moduleService.selectAllSuperModule();
	}
	
	public WebResult searchModule(Long id){
		WebResult webResult = new WebResult();
		webResult.setData(moduleService.searchModule(id));
		webResult.success();
		return webResult;
	}
	
	public WebResult selectModuleById(Long moduleId){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(moduleService.selectModuleById(moduleId));
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public List<Module> selectAllSubModule(){
		return moduleService.selectSubModule();
	}
}
