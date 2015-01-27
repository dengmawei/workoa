package org.common.shiro.resolver;

import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.common.service.IModuleService;
import org.common.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class WorkRolePermissionResolver implements RolePermissionResolver {
	
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		return null;
	}

}
