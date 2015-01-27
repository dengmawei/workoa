package org.web.biz.system;

import org.common.entity.Permit;
import org.common.entity.view.PaginationView;
import org.common.entity.view.permit.PermitView;
import org.common.exception.OaException;
import org.common.service.IPermitService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("permitBiz")
public class PermitBiz {
	@Autowired
	private IPermitService permitService;
	
	public WebResult savePermit(PermitView permitView){
		WebResult webResult = new WebResult();
		try {
			permitService.savePermit(permitView);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult deletePermit(Long id){
		WebResult webResult = new WebResult();
		try {
			permitService.deletePermit(id);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult selectPermitByModuleId(Long moduleId){
		WebResult webResult = new WebResult();
		webResult.setData(permitService.selectPermitByModuleId(moduleId));
		webResult.success();
		return webResult;
	}
	
	public WebResult selectPermitByPage(PaginationView<Permit> paginationView){
		WebResult webResult = new WebResult();
		permitService.selectPermitByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult; 
	}
}
