package org.common.service;

import java.util.List;

import org.common.entity.Module;
import org.common.entity.view.PaginationView;
import org.common.entity.view.module.ModuelView;
import org.common.entity.view.module.ModuleForUpdateView;
import org.common.entity.view.module.ModuleLoginView;
import org.common.exception.OaException;

public interface IModuleService {
	/**
	 * 新增模块
	 * @param module 模块对象
	 */ 
	void saveModule(Module module)throws OaException;
	
	/**
	 * 编辑模块
	 * @param module 模块对象
	 */
	void editModule(ModuleForUpdateView moduleView)throws OaException;
	
	/**
	 * 删除模块
	 * @param id 主键id
	 */
	void deleteModule(Long id)throws OaException;
	
	/**
	 * 分页查询模块
	 * @param paginationView 分页插件
	 */
	void selectModuleByPage(PaginationView<Module> paginationView);
	
	/**
	 * 根据id查询模块
	 * @param id
	 * @return
	 */
	Module selectModuleById(Long id)throws OaException;
	
	/**
	 * 查询所有的一级模块
	 * @return
	 */
	List<Module> selectAllSuperModule();
	
	/**
	 * 查看模块详情
	 * @param id
	 * @return
	 */
	ModuelView searchModule(Long id);
	
	/**
	 * 查询所有的子模块
	 * @return
	 */
	List<Module> selectSubModule();
	
	/**
	 * 查询登录之后显示的菜单（模块）
	 * @return
	 */
	List<ModuleLoginView> loadLoginModule();
}
