package org.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.common.entity.Permit;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface PermitRepository{
	/**
	 * 添加
	 * @param permit
	 */
	void savePermit(Permit permit);
	
	/**
	 * 删除
	 * @param id
	 */
	void deletePermit(Long id);
	
	/**
	 * 根据模块id查询权限
	 * @param moduleId
	 * @return
	 */
	List<Permit> selectPermitByModuleId(Long moduleId);
	
	/**
	 * 根据模块id 和 Action id 查询权限
	 * @param map
	 * @return
	 */
	Permit selectPermitByModuleIdAndActionId(Map map);
	
	/**
	 * 根据actionid查询权限
	 * @param actionId
	 * @return
	 */
	List<Permit> selectPermitByActionId(Long actionId);
	
	/**
	 * 分页查询权限
	 * @param map
	 * @return
	 */
	List<Permit> selectPermitListByPage(Map map);
	
	/**
	 * 分页查询权限总数
	 * @param map
	 * @return
	 */
	int selectPermitCountByPage(Map map);
	
	/**
	 * 根据userid-roleid-permitValue查询用户所拥有的角色下的权限集合
	 * @param userId
	 * @return
	 */
	List<String> selectRolePermitByUserId(Long userId);
	
	/**
	 * 根据userid查询是否拥有超级管理员角色
	 * @param userId
	 * @return
	 */
	int selectSuperRoleByUserId(long userId);
}
