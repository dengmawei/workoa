package org.common.service;

import org.common.entity.Action;
import org.common.entity.view.PaginationView;
import org.common.exception.OaException;

public interface IActionService {
	void saveAction(Action action);
	
	void editAction(Action action);
	
	void deleteAction(Long id)throws OaException;
	
	void selectActionByPage(PaginationView<Action> paginationView);
	
	Action selectActionById(Long id);
}
