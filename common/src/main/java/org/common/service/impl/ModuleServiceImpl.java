package org.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.common.dao.ModuleRepository;
import org.common.dao.PermitRepository;
import org.common.entity.Module;
import org.common.entity.Permit;
import org.common.entity.view.PaginationView;
import org.common.entity.view.module.ModuelView;
import org.common.entity.view.module.ModuleForUpdateView;
import org.common.entity.view.module.ModuleLoginView;
import org.common.entity.view.module.ModuleSimpleView;
import org.common.exception.OaException;
import org.common.exception.system.module.ModuelSuperNotExistException;
import org.common.exception.system.module.ModuleBeSuperModuleException;
import org.common.exception.system.module.ModuleValueExistException;
import org.common.exception.system.module.ModuleExistSubModuleExcepiton;
import org.common.exception.system.module.ModuleNotExistException;
import org.common.exception.system.module.ModulePermitExistException;
import org.common.exception.system.module.ModuleSuperCannotBeSelfException;
import org.common.service.IModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("moduleService")
public class ModuleServiceImpl implements IModuleService {
	@Autowired
	private ModuleRepository moduleDao;
	
	@Autowired
	private PermitRepository permitDao;
	
	public void saveModule(Module module)throws OaException {
		Module tempModule = moduleDao.selectModuleByValue(module.getModuleValue());
		if(tempModule!=null){
			throw new ModuleValueExistException();
		}
		
		if(module.getSuperId()>0L){
			Module superModule = moduleDao.selectModuleById(module.getSuperId());
		}
		
		module.setCreateTime(new Date());
		moduleDao.saveModule(module);
	}

	public void editModule(ModuleForUpdateView moduleView)throws OaException {
		//编辑前原模块对象
		Module originalModule = this.selectModuleById(moduleView.getId());
		
		//原模块是父节点
		if(originalModule.getSuperId().longValue()==0L){
			//现修改的父节点是当前模块
			if(moduleView.getSuperId()>0L && moduleView.getSuperId()==moduleView.getId().longValue()){
				throw new ModuleSuperCannotBeSelfException();
			}
			
			//是否有下级模块
			int subCount = moduleDao.selectCountSubModule(moduleView.getId());
			
			//且原节点下存在子节点，且现在修改为子节点
			if(subCount>0 && moduleView.getSuperId()>0L){
				throw new ModuleBeSuperModuleException();
			}
		}else{//原模卡是子节点
			//现修改为父节点
			if(moduleView.getSuperId()>0L){
				Module tempSuperModule = moduleDao.selectModuleById(moduleView.getSuperId());
				if(tempSuperModule==null){
					throw new ModuelSuperNotExistException();
				}
			}
		}
		
		//原模块和现模块的value不一样（意味着修改编码）
		if(!moduleView.getModuleValue().equals(originalModule.getModuleValue())){
			//现在模块value已在数据库中存在
			Module tempModule = moduleDao.selectModuleByValue(moduleView.getModuleValue());
			if(tempModule!=null){
				throw new ModuleValueExistException();
			}
		}
		
		Module module = new Module();
		BeanUtils.copyProperties(moduleView, module);
		
		if(moduleView.getSuperId()>0L){//如果当前节点为子节点，重新设置其父节点的code
			Module superModule = moduleDao.selectModuleById(module.getSuperId());
		}
		moduleDao.editModule(module);
	}

	public void deleteModule(Long id)throws OaException {
		int count = moduleDao.selectCountSubModule(id);
		
		if(count>0){
			throw new ModuleExistSubModuleExcepiton();
		}
		
		List<Permit> permitList = permitDao.selectPermitByModuleId(id);
		if(permitList!=null && permitList.size()>0){
			throw new ModulePermitExistException();
		}
		
		moduleDao.deleteModule(id);
	}

	public void selectModuleByPage(PaginationView<Module> paginationView) {
		paginationView.setCount(moduleDao.selectModuleCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(moduleDao.selectModuleListByPage(paginationView.loadFilter()));
	}

	public Module selectModuleById(Long id) throws OaException {
		Module module = moduleDao.selectModuleById(id);
		if(module==null){
			throw new ModuleNotExistException();
		}
		return module;
	}

	public List<Module> selectAllSuperModule() {
		return moduleDao.selectSuperModule();
	}

	public ModuelView searchModule(Long id) {
		ModuelView view = new ModuelView();
		view.setModule(moduleDao.selectModuleById(id));
		if(view.getModule().getSuperId()!=0){
			view.setSuperModule(moduleDao.selectModuleById(view.getModule().getSuperId()));
		}
		return view;
	}

	public List<Module> selectSubModule() {
		return moduleDao.selectSubModule();
	}

	public List<ModuleLoginView> loadLoginModule() {
		// 所有父级模块
		List<Module> superModuleList = selectAllSuperModule();

		// 所有子模块
		List<Module> subModuleList = selectSubModule();

		List<ModuleLoginView> list = new ArrayList<ModuleLoginView>();

		List<Module> tempList = null;
		// 遍历所有的模块，将子模块放入父模块列表中
		for (Module superModule : superModuleList) {
			ModuleLoginView moduleView = new ModuleLoginView();
			moduleView.setSuperModuleName(superModule.getModuleName());
			int subCount = subModuleList.size();
			tempList = new ArrayList<Module>();

			for (int i = 0; i < subCount; i++) {
				Module subModule = subModuleList.get(i);
				if (subModule.getSuperId().longValue() == superModule.getId()
						.longValue()) {
					ModuleSimpleView simpleView = new ModuleSimpleView();
					BeanUtils.copyProperties(subModule, simpleView);
					moduleView.addSubModule(simpleView);
					moduleView.addCount();

					tempList.add(subModule);
				}
			}
			list.add(moduleView);
			// 从子列表中移除
			subModuleList.remove(tempList);
		}

		if (tempList != null && tempList.size() > 0) {
			tempList = null;
		}
		return list;
	}
}
