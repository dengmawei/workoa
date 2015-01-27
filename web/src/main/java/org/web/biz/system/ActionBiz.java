package org.web.biz.system;

import org.common.entity.Action;
import org.common.entity.view.PaginationView;
import org.common.exception.OaException;
import org.common.service.IActionService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("actionBiz")
public class ActionBiz {
	@Autowired
	private IActionService actionService;
	
	public WebResult saveAction(Action action){
		WebResult webResult = new WebResult();
		actionService.saveAction(action);
		webResult.success();
		return webResult;
	}
	
	public WebResult editAction(Action action){
		WebResult webResult = new WebResult();
		actionService.editAction(action);
		webResult.success();
		return webResult;
	}
	
	public WebResult deleteAction(Long id){
		WebResult webResult = new WebResult();
		try {
			actionService.deleteAction(id);
			webResult.success();
		} catch (OaException e) {
			webResult.setMessage(e.getMessage());
			webResult.fail();
		}
		return webResult;
	}
	
	public WebResult selectActionById(Long id){
		WebResult webResult = new WebResult();
		webResult.setData(actionService.selectActionById(id));
		webResult.success();
		return webResult;
	}
	
	public WebResult selectActionByPage(PaginationView<Action> paginationView){
		WebResult webResult = new WebResult();
		actionService.selectActionByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
}
