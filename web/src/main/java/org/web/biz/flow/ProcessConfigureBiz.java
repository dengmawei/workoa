package org.web.biz.flow;

import java.util.List;

import org.common.entity.view.PaginationView;
import org.common.entity.view.workflow.ProcessKeyView;
import org.common.entity.workflow.ProcessConfigure;
import org.common.service.workflow.IProcessConfigureService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("processConfigureBiz")
public class ProcessConfigureBiz {
	@Autowired
	private IProcessConfigureService proccessConfigureService;
	
	public WebResult selectProcessConfigureByPage(PaginationView<ProcessConfigure> paginationView){
		WebResult webResult = new WebResult();
		proccessConfigureService.selectProcessConfigureByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public WebResult save(ProcessConfigure processConfigure){
		WebResult webResult = new WebResult();
		proccessConfigureService.save(processConfigure);
		webResult.success();
		return webResult;
	}
	
	public WebResult update(ProcessConfigure processConfigure){
		WebResult webResult = new WebResult();
		proccessConfigureService.update(processConfigure);
		webResult.success();
		return webResult;
	}
	
	public WebResult delete(Long id){
		WebResult webResult = new WebResult();
		proccessConfigureService.delete(id);
		webResult.success();
		return webResult;
	}
	
	public WebResult selectById(Long id){
		WebResult webResult = new WebResult();
		webResult.setData(proccessConfigureService.selectProcessConfigureById(id));
		webResult.success();
		return webResult;
	}
	
	public List<ProcessKeyView> findProcessKeys(){
		return proccessConfigureService.findProcessKeyList();
	}
}
