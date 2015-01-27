package org.common.service;

import java.util.List;

import org.common.entity.Role;
import org.common.entity.view.PaginationView;
import org.common.entity.view.role.RolePermitView;
import org.common.entity.view.role.RoleUserView;
import org.common.exception.OaException;

public interface IRoleService {
	/**
	 * 新增角色
	 * @param role
	 * @throws OaException
	 */
	void saveRole(Role role) throws OaException;
	
	/**
	 * 编辑角色
	 * @param role
	 */
	void updateRole(Role role) throws OaException;
	
	/**
	 * 查询角色
	 * @param roleId
	 * @return
	 */
	Role selectRoleById(Long roleId)throws OaException;
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	void deleteRole(Long roleId)throws OaException;
	
	/**
	 * 分页查询角色列表
	 * @param paginationView 分页插件
	 */
	void selectRoleByPage(PaginationView<Role> paginationView);
	
	/**
	 * 分页查询角色用户列表
	 * @param paginationView 分页插件
	 */
	void selectRoleUserByPage(PaginationView<RoleUserView> paginationView);
	
	/**
	 * 保存角色用户关系(批量)
	 * @param userId 用户id
	 * @param roleIds 角色id集合
	 */
	void saveRoleUser(Long userId,Long[] roleIds);
	
	/**
	 * 删除角色用户关系
	 * @param ruId
	 */
	void deleteRoleUser(Long ruId);
	
	/**
	 * 批量增加角色权限
	 * @param roleId
	 * @param permitIdArr
	 */
	void saveRolePermit(Long roleId,Long[] permitIdArr);
	
	/**
	 * 删除角色-权限
	 * @param roleId
	 * @param permitId
	 */
	void deleteRolePermit(Long roleId,Long permitId);
	
	/**
	 * 根据角色id查询权限
	 * @param roleId
	 * @return
	 */
	List<RolePermitView> selectRolePermitByRoleId(Long roleId); 
	
	/**
	 * 查询角色下的用户信息
	 * @param roleId
	 * @return
	 */
	List<RoleUserView> selectRoleUserByRoleId(Long roleId);
	
	/**
	 * 根据角色名称查询角色
	 * @param roleName
	 * @return
	 */
	Role selectRoleByName(String roleName);
}
