package org.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.common.dao.ActionRepository;
import org.common.dao.ModuleRepository;
import org.common.dao.PermitRepository;
import org.common.dao.RolePermitRepository;
import org.common.entity.Action;
import org.common.entity.Module;
import org.common.entity.Permit;
import org.common.entity.view.PaginationView;
import org.common.entity.view.permit.PermitView;
import org.common.exception.OaException;
import org.common.exception.system.permit.PermitHasExistException;
import org.common.exception.system.permit.PermitRoleExistException;
import org.common.service.IPermitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permitService")
public class PermitServiceImpl implements IPermitService {
	@Autowired
	private PermitRepository permitDao;
	
	@Autowired
	private ModuleRepository moduleDao;
	
	@Autowired
	private ActionRepository actionDao;
	
	@Autowired
	private RolePermitRepository rolePermitDao;
	
	public void savePermit(PermitView permitView)throws OaException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("moduleId", permitView.getModuleId());
		map.put("actionId", permitView.getActionId());
		
		Permit tempPermit = permitDao.selectPermitByModuleIdAndActionId(map);
		if(tempPermit!=null){
			throw new PermitHasExistException();
		}
		
		Permit permit = new Permit();
		BeanUtils.copyProperties(permitView, permit);
		permit.setCreateTime(new Date());
		permit.setPermitCode(permitView.getModuleId()+"_"+permitView.getActionId());
		
		Module module = moduleDao.selectModuleById(permitView.getModuleId());
		Action action = actionDao.selectActionById(permitView.getActionId());
	
		permit.setPermitValue(module.getModuleValue()+":"+action.getActionValue());
		permitDao.savePermit(permit);
	}

	public void deletePermit(Long id) throws OaException{
		if(rolePermitDao.selectCountPermitByPermitId(id)>0){
			throw new PermitRoleExistException();
		}
		permitDao.deletePermit(id);
	}

	public List<Permit> selectPermitByModuleId(Long moduleId) {
		return permitDao.selectPermitByModuleId(moduleId);
	}
	
	public void selectPermitByPage(PaginationView<Permit> paginationView){
		paginationView.setCount(permitDao.selectPermitCountByPage(paginationView.loadFilter()));
		paginationView.setPaginationList(permitDao.selectPermitListByPage(paginationView.loadFilter()));
	}

	public List<Permission> selectPermitByUserId(Long userId) {
		List<Permission> permissionList = new ArrayList<Permission>();
		
		//拥有超级管理员权限
		int countSuper = permitDao.selectSuperRoleByUserId(userId);
		if(countSuper>0){
			permissionList.add(new WildcardPermission("*:*", false));
			return permissionList;
		}
		
		//通过普通角色获取权限
		List<String> permitValueList = permitDao.selectRolePermitByUserId(userId);
		
		if(permitValueList!=null && permitValueList.size()>0){
			for(String permitValue:permitValueList){
				permissionList.add(new WildcardPermission(permitValue,false));
			}
		}
		
		//通过职位获得权限
		
		//通过用户id获取权限组
		
		//通过用户id获取权限
		
		return permissionList;
	}
}
