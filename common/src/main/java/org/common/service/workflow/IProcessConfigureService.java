package org.common.service.workflow;

import java.util.List;
import java.util.Map;

import org.common.entity.view.PaginationView;
import org.common.entity.view.workflow.ProcessKeyView;
import org.common.entity.workflow.ProcessConfigure;

public interface IProcessConfigureService {
	void save(ProcessConfigure processConfigure);
	
	void update(ProcessConfigure processConfigure);
	
	void delete(Long id);
	
	ProcessConfigure selectProcessConfigureByCondition(Map map);
	
	ProcessConfigure selectProcessConfigureById(Long id);
	
	void selectProcessConfigureByPage(PaginationView<ProcessConfigure> paginationView);
	
	List<ProcessKeyView> findProcessKeyList();
}
