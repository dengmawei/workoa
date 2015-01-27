package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.Role;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface RoleRepository{
	/**
	 * 添加角色
	 * @param role
	 */
	long saveRole(Role role);
	
	/**
	 * 更新角色
	 * @param role
	 */
	void updateRole(Role role);
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	void deleteRole(long roleId);
	
	/**
	 * 分页查询角色
	 * @param map
	 * @return
	 */
	List<Role> selectRoleListByPage(Map<String,Object> map);
	
	/**
	 * 分页查询角色总数
	 * @param map
	 * @return
	 */
	int selectRoleCountByPage(Map<String,Object> map);
	
	/**
	 * 根据id查询角色
	 * @param roleId
	 * @return
	 */
	Role selectRoleById(Long roleId);
	
	/**
	 * 根据角色id删除角色-用户关系
	 * @param map
	 */
	void deleteRoleUserByRoleId(Map<String,Object> map);
	
	/**
	 * 根据用户id删除角色-用户关系
	 * @param map
	 */
	void deleteRoleUserByUserId(Map<String,Object> map);
	
	/**
	 * 根据角色名称查询角色
	 */
	Role selectRoleByName(String roleName);
}
