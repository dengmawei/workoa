package org.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.dao.RolePermitRepository;
import org.common.dao.RoleRepository;
import org.common.dao.UserPermitRepository;
import org.common.entity.Role;
import org.common.entity.RolePermit;
import org.common.entity.UserPermit;
import org.common.entity.view.PaginationView;
import org.common.entity.view.role.RolePermitView;
import org.common.entity.view.role.RoleUserView;
import org.common.exception.OaException;
import org.common.exception.system.role.RoleNameExistException;
import org.common.exception.system.role.RoleNotFoundException;
import org.common.exception.system.role.RolePermitExistException;
import org.common.exception.system.role.RoleUserExistException;
import org.common.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private RoleRepository roleDao;
	
	@Autowired
	private UserPermitRepository userPermitDao;
	
	@Autowired
	private RolePermitRepository rolePermitDao;
	
	public void saveRole(Role role) throws OaException {
		role.setCreateTime(new Date());
		
		Role tempRole = roleDao.selectRoleByName(role.getRoleName());
		if(tempRole!=null){
			throw new RoleNameExistException();
		}
		roleDao.saveRole(role);
	}

	public void updateRole(Role role) throws OaException {
		Role tempRole = roleDao.selectRoleByName(role.getRoleName());
		if(tempRole!=null && role.getId().longValue()!=tempRole.getId().longValue()){
			throw new RoleNameExistException();
		}
		roleDao.updateRole(role);
	}

	public Role selectRoleById(Long roleId)throws OaException {
		Role role = roleDao.selectRoleById(roleId);
		if(role==null){
			throw new RoleNotFoundException();
		}
		return role; 
	}

	public void deleteRole(Long roleId) throws OaException {
		//判断该角色下是否存在用户和权限关联
		int userCount = userPermitDao.selectRoleUserCountByRoleId(roleId);
		if(userCount>0){
			throw new RoleUserExistException();
		}
		
		int permitCount = rolePermitDao.selectCountPermitByRoleId(roleId);
		if(permitCount>0){
			throw new RolePermitExistException();
		}
		roleDao.deleteRole(roleId);
	}

	public void selectRoleByPage(PaginationView<Role> paginationView) {
		paginationView.setCount(roleDao.selectRoleCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(roleDao.selectRoleListByPage(paginationView.loadFilter()));
	}

	public void selectRoleUserByPage(PaginationView<RoleUserView> paginationView) {
		paginationView.setPaginationList(userPermitDao.selectRoleUserListByPage(paginationView.loadFilter()));
		paginationView.setCount(userPermitDao.selectRoleUserCountByPage(paginationView.loadFilter()));
	}

	@Transactional(rollbackFor=Exception.class)
	public void saveRoleUser(Long userId, Long[] roleIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		for(Long roleId:roleIds){
			map.put("roleId", roleId);
			if(userPermitDao.selectCountRoleUser(map)>0){
				continue;
			}
			UserPermit userPermit = new UserPermit(userId, roleId,null,null);
			userPermitDao.saveRoleUser(userPermit);
		}
	}

	public void deleteRoleUser(Long ruId) {
		userPermitDao.deleteRoleUser(ruId);
	}

	@Transactional(rollbackFor=Exception.class)
	public void saveRolePermit(Long roleId, Long[] permitIdArr) {
		if(permitIdArr!=null && permitIdArr.length>0){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("roleId", roleId);
			for(Long permitId:permitIdArr){
				map.put("permitId", permitId);
				RolePermit rolePermit = rolePermitDao.selectRolePermitByRoleIdAndPermitId(map);
				if(rolePermit!=null){
					continue;
				}else{
					rolePermit = new RolePermit();
					rolePermit.setPermitId(permitId);
					rolePermit.setRoleId(roleId);
					rolePermitDao.saveRolePermit(rolePermit);
				}
			}
		}
	}
	
	public void deleteRolePermit(Long roleId,Long permitId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleId);
		map.put("permitId", permitId);
		
		rolePermitDao.deleteRolePermit(map);
	}

	public List<RolePermitView> selectRolePermitByRoleId(Long roleId) {
		return rolePermitDao.selectRolePermit(roleId);
	}

	public List<RoleUserView> selectRoleUserByRoleId(Long roleId) {
		return userPermitDao.selectRoleUserByRoleId(roleId);
	}

	public Role selectRoleByName(String roleName) {
		return roleDao.selectRoleByName(roleName);
	}
}
