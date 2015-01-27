package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.RoleUser;
import org.common.entity.view.role.RoleUserView;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface RoleUserRepository{
	/**
	 * 保存角色用户关系
	 * @param roleUser
	 */
	void saveRoleUser(RoleUser roleUser);
	
	/**
	 * 分页查询角色和用户的关联关系
	 * @param param
	 * @return
	 */
	List<RoleUserView> selectRoleUserListByPage(Map param);
	
	/**
	 * 分页查询角色和用户的总数
	 * @param param
	 * @return
	 */
	int selectRoleUserCountByPage(Map param);
	
	/**
	 * 查询角色下的用户数
	 * @param roleId 角色id
	 * @return
	 */
	int selectRoleUserByRoleId(long roleId);
	
	/**
	 * 删除角色用户关系
	 * @param ruId
	 */
	void deleteRoleUser(Long ruId);
}
