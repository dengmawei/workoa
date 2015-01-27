package org.common.service;

import java.util.List;

import org.apache.shiro.authz.Permission;
import org.common.entity.Permit;
import org.common.entity.view.PaginationView;
import org.common.entity.view.permit.PermitView;
import org.common.exception.OaException;

public interface IPermitService {
	/**
	 * 添加权限
	 * @param permit
	 */
	void savePermit(PermitView permitView)throws OaException;
	
	/**
	 * 删除权限
	 * @param permit
	 */
	void deletePermit(Long id)throws OaException;
	
	/**
	 * 根据模块id查询权限
	 * @param moduleId
	 * @return
	 */
	List<Permit> selectPermitByModuleId(Long moduleId);
	
	/**
	 * 分页查询权限列表
	 * @param paginationView 分页对象
	 */
	void selectPermitByPage(PaginationView<Permit> paginationView);
	
	/**
	 * 根据用户id查询权限
	 * @param userId
	 * @return
	 */
	List<Permission> selectPermitByUserId(Long userId);
}
