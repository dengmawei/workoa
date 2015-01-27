package org.common.service.impl;

import java.util.List;

import org.common.dao.ActionRepository;
import org.common.dao.PermitRepository;
import org.common.entity.Action;
import org.common.entity.Permit;
import org.common.entity.view.PaginationView;
import org.common.exception.OaException;
import org.common.exception.system.action.ActionPermitException;
import org.common.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("actionService")
public class ActionServiceImpl implements IActionService {
	@Autowired
	private ActionRepository actionDao;
	
	@Autowired
	private PermitRepository permitDao;
	
	public void saveAction(Action action) {
		actionDao.saveAction(action);
	}

	public void editAction(Action action) {
		actionDao.updateAction(action);
	}

	public void deleteAction(Long id)throws OaException{
		List<Permit> list = permitDao.selectPermitByActionId(id);
		if(list!=null && list.size()>0){
			throw new ActionPermitException();
		}
		actionDao.deleleAction(id);
	}

	public void selectActionByPage(PaginationView<Action> paginationView) {
		paginationView.setCount(actionDao.selectActionCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(actionDao.selectActionListByPage(paginationView.loadFilter()));
	}

	public Action selectActionById(Long id) {
		return actionDao.selectActionById(id);
	}

}
