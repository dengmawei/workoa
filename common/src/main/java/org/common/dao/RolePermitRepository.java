package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.RolePermit;
import org.common.entity.view.role.RolePermitView;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface RolePermitRepository{
	void saveRolePermit(RolePermit rolePermit);
	
	void deleteRolePermit(Map map);
	
	List<RolePermitView> selectRolePermit(Long roleId);
	
	RolePermit selectRolePermitByRoleIdAndPermitId(Map map);
	
	/**
	 * 查询角色下的权限数
	 * @param roleId
	 * @return
	 */
	int selectCountPermitByRoleId(Long roleId);
	
	/**
	 * 查询与该权限相关的角色数
	 * @param permitId
	 * @return
	 */
	int selectCountPermitByPermitId(Long permitId);
}
