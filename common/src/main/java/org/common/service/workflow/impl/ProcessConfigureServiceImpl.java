package org.common.service.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.common.constant.WorkflowProcessKeyEnum;
import org.common.dao.workflow.ProcessConfigureRepository;
import org.common.entity.view.PaginationView;
import org.common.entity.view.workflow.ProcessKeyView;
import org.common.entity.workflow.ProcessConfigure;
import org.common.service.workflow.IProcessConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("processConfigureService")
public class ProcessConfigureServiceImpl implements IProcessConfigureService {
	@Autowired
	private ProcessConfigureRepository processConfigureDao;
	
	public void save(ProcessConfigure processConfigure) {
		processConfigure.setCreateTime(new Date());
		processConfigureDao.save(processConfigure);
	}

	public void update(ProcessConfigure processConfigure) {
		processConfigureDao.update(processConfigure);
	}

	public void delete(Long id) {
		processConfigureDao.delete(id);
	}

	public ProcessConfigure selectProcessConfigureByCondition(Map map) {
		return processConfigureDao.selectProcessConfigureByCondition(map);
	}

	public ProcessConfigure selectProcessConfigureById(Long id) {
		return processConfigureDao.selectProcessConfigureById(id);
	}

	public void selectProcessConfigureByPage(
			PaginationView<ProcessConfigure> paginationView) {
		paginationView.setCount(processConfigureDao.selectProcessConfigureCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(processConfigureDao.selectProcessConfigureListByPage(paginationView.loadFilter()));
	}

	public List<ProcessKeyView> findProcessKeyList() {
		List<ProcessKeyView> list = new ArrayList<ProcessKeyView>();
		
		WorkflowProcessKeyEnum[] keys = WorkflowProcessKeyEnum.values();
		for(WorkflowProcessKeyEnum processKey:keys){
			list.add(new ProcessKeyView(processKey.getKey(),processKey.getDesc()));
		}
		return list;
	}
	
}
