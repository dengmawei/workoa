package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.Module;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface ModuleRepository{
	/**
	 * 添加新模块
	 * @param module
	 */
	void saveModule(Module module);
	
	/**
	 * 编辑模块
	 * @param module
	 */
	void editModule(Module module);
	
	/**
	 * 删除模块
	 * @param id
	 */
	void deleteModule(Long id);
	
	/**
	 * 查询子模块数
	 * @param superId 父节点id
	 * @return
	 */
	int selectCountSubModule(Long superId);
	
	/**
	 * 分页查询模块
	 * @param param 参数
	 * @return
	 */
	List<Module> selectModuleListByPage(Map<String,Object> param);
	
	/**
	 * 分页查询模块总数 
	 * @param param 参数
	 * @return
	 */
	int selectModuleCountByPage(Map<String,Object> param);
	
	/**
	 * 根据id查询模块
	 * @param id
	 * @return
	 */
	Module selectModuleById(Long id);
	
	/**
	 * 查询所有的一级模块
	 * @return
	 */
	List<Module> selectSuperModule();
	
	/**
	 * 根据模块value查询模块对象
	 * @param moduleValue 模块值
	 * @return
	 */
	Module selectModuleByValue(String moduleValue);
	
	/**
	 * 查询所有的子模块
	 * @return
	 */
	List<Module> selectSubModule();
}
