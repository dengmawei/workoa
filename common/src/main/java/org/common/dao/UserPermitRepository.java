package org.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.common.entity.Role;
import org.common.entity.UserPermit;
import org.common.entity.view.role.RoleUserView;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface UserPermitRepository{
	/**
	 * 保存角色用户关系
	 * @param roleUser
	 */
	void saveRoleUser(UserPermit userPermit);
	
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
	int selectRoleUserCountByRoleId(long roleId);
	
	/**
	 * 删除角色用户关系
	 * @param ruId
	 */
	void deleteRoleUser(Long ruId);
	
	/**
	 * 根据用户id查询角色列表
	 * @param userId
	 * @return
	 */
	/*Set<String> selectRoleCodeByUserId(Long userId);*/
	
	int selectCountRoleUser(Map map);
	
	/**
	 * 根据角色查询用户信息
	 * @param roleId
	 * @return
	 */
	List<RoleUserView> selectRoleUserByRoleId(Long roleId);
}
