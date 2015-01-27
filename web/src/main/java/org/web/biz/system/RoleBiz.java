package org.web.biz.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.entity.Role;
import org.common.entity.view.PaginationView;
import org.common.entity.view.role.RolePermitView;
import org.common.entity.view.role.RoleUserView;
import org.common.exception.OaException;
import org.common.service.IModuleService;
import org.common.service.IRoleService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("roleBiz")
public class RoleBiz {
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IModuleService moduleService;
	
	public WebResult saveRole(Role role){
		WebResult webResult = new WebResult();
		try {
			roleService.saveRole(role);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult editRole(Role role){
		WebResult webResult = new WebResult();
		try {
			roleService.updateRole(role);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult deleteRole(Long roleId){
		WebResult webResult = new WebResult();
		try {
			roleService.deleteRole(roleId);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult selectRoleById(Long roleId){
		WebResult webResult = new WebResult();
		try {
			webResult.setData(roleService.selectRoleById(roleId));
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult selectRoleByPage(PaginationView<Role> paginationView){
		WebResult webResult = new WebResult();
		roleService.selectRoleByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public WebResult selectRoleUserByPage(PaginationView<RoleUserView> paginationView){
		WebResult webResult = new WebResult();
		roleService.selectRoleUserByPage(paginationView);
		webResult.setData(paginationView.getPaginationDate());
		webResult.success();
		return webResult;
	}
	
	public WebResult saveRoleUser(Long userId,Long[] roleIds){
		WebResult webResult = new WebResult();
		roleService.saveRoleUser(userId, roleIds);
		webResult.success();
		return webResult;
	}
	
	public WebResult deleteRoleUser(Long ruId){
		WebResult webResult = new WebResult();
		roleService.deleteRoleUser(ruId);
		webResult.success();
		return webResult;
	}
	
	public WebResult saveRolePermit(Long roleId,Long[] permitIdArr){
		WebResult webResult = new WebResult();
		roleService.saveRolePermit(roleId, permitIdArr);
		webResult.success();
		return webResult;
	}
	
	public WebResult deleteRolePermit(Long roleId,Long permitId){
		WebResult webResult = new WebResult();
		roleService.deleteRolePermit(roleId, permitId);
		webResult.success();
		return webResult;
	}
	
	public WebResult toRolePermitPage(Long roleId){
		WebResult webResult = new WebResult();
		try {
			Role role = roleService.selectRoleById(roleId);
			List<RolePermitView> list = roleService.selectRolePermitByRoleId(roleId);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("role", role);
			map.put("list", list);
			map.put("moduleList", moduleService.selectSubModule());
			webResult.setData(map);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
}
