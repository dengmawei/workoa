package org.web.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.constant.NameSpace;
import org.common.entity.Role;
import org.common.entity.view.PaginationView;
import org.common.entity.view.role.RoleUserView;
import org.common.entity.view.user.UserView;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.bind.annotation.CurrentUser;
import org.web.biz.system.ModuleBiz;
import org.web.biz.system.RoleBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	@Autowired
	private RoleBiz roleBiz;
	
	@Autowired
	private ModuleBiz moduleBiz;
	
	/**
	 * 角色管理界面
	 * @return
	 */
	@RequiresPermissions("role:view")
	@RequestMapping(value="/index")
	public String index(@CurrentUser UserView user,HttpServletRequest request){
		return "role.page";
	}
	
	/**
	 * 根据角色id查询角色
	 * @param roleId 角色I的
	 * @return
	 */
	@RequiresPermissions("role:select")
	@RequestMapping(value="/seletRoleById",method=RequestMethod.POST)
	@ResponseBody
	public WebResult seletRoleById(@RequestParam("roleId")Long roleId){
		return roleBiz.selectRoleById(roleId);
	}
	
	/**
	 * 添加角色
	 * @param role 角色对象
	 * @return
	 */
	@RequiresPermissions("role:add")
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	@ResponseBody
	public WebResult addRole(@ModelAttribute("role")Role role){
		return roleBiz.saveRole(role);
	}
	
	/**
	 * 编辑角色
	 * @param role 角色对象
	 * @return
	 */
	@RequiresPermissions("role:edit")
	@RequestMapping(value="/editRole",method=RequestMethod.POST)
	@ResponseBody
	public WebResult editRole(@ModelAttribute("role")Role role){
		return roleBiz.editRole(role);
	}
	
	/**
	 * 删除角色
	 * @param roleId 角色id
	 * @return
	 */
	@RequiresPermissions("role:delete")
	@RequestMapping(value="/deleteRole",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteRole(@RequestParam("roleId")Long roleId){
		return roleBiz.deleteRole(roleId);
	}
	
	/**
	 * 分页查询角色
	 * @param paginationView 分页对象
	 * @return
	 */
	@RequiresPermissions("role:select")
	@RequestMapping(value="/selectRoleByPage",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectRoleByPage(@ModelAttribute("paginationView")PaginationView<Role> paginationView,
			@RequestParam(value="roleName",required=false)String roleName){
		if(org.apache.commons.lang.StringUtils.isNotBlank(roleName)){
			paginationView.loadFilter().put("roleName", roleName);
		}
		return roleBiz.selectRoleByPage(paginationView);
	}
	
	/**
	 * 去角色用户管理界面
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("role:roleUserView")
	@RequestMapping(value="/toRoleUser/{roleId}")
	public String toRoleUserPage(@PathVariable Long roleId,Model model){
		model.addAttribute("roleId", roleId);
		model.addAttribute("role", roleBiz.selectRoleById(roleId).getData());
		return "role.user.page";
	}
	
	/**
	 * 分页查询角色用户列表
	 * @param paginationView 分页插件
	 * @param roleId 角色id
	 * @param roleName 角色名称
	 * @param roleCode 角色编码
	 * @param userCode 用户编码
	 * @param realName 真实姓名
	 * @return
	 */
	@RequiresPermissions("role:roleUserSelect")
	@RequestMapping(value="/selectRoleUserByPage",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectRoleUserByPage(@ModelAttribute("paginationView")PaginationView<RoleUserView> paginationView,
			@RequestParam("roleId")Long roleId,@RequestParam(value="userCode",required=false)String userCode,
			@RequestParam(value="userName",required=false)String userName){
		
		paginationView.loadFilter().put("roleId", roleId);
		if(StringUtils.isNotBlank(userCode)){
			paginationView.loadFilter().put("userCode", userCode);
		}
		if(StringUtils.isNotBlank(userName)){
			paginationView.loadFilter().put("userName", userName);
		}
		
		return roleBiz.selectRoleUserByPage(paginationView);
	}
	
	/**
	 * 添加角色用户关系
	 * @param userId 用户id
	 * @param roleIds 角色id集合
	 * @return
	 */
	@RequiresPermissions("role:roleUserAdd")
	@RequestMapping(value="/saveRoleUser",method = RequestMethod.POST)
	@ResponseBody
	public WebResult saveRoleUser(@RequestParam("userId")Long userId,@RequestParam("roleIds[]")Long[] roleIds){
		return roleBiz.saveRoleUser(userId, roleIds);
	}
	
	/**
	 * 删除角色用户关系
	 * @param ruId
	 * @return
	 */
	@RequiresPermissions("role:roleUserDelete")
	@RequestMapping(value="/deleteRoleUser",method = RequestMethod.POST)
	@ResponseBody
	public WebResult deleteRoleUser(@RequestParam("ruId")Long ruId){
		return roleBiz.deleteRoleUser(ruId);
	}
	
	/**
	 * 角色权限页面
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("role:rolePermitView")
	@RequestMapping(value="/selectRolePermit/{roleId}")
	public String selectRolePermit(@PathVariable Long roleId,Model model){
		WebResult webResult = roleBiz.toRolePermitPage(roleId);
		
		if(webResult.isSuccess()){
			model.addAllAttributes((Map<String,Object>)webResult.getData());
			model.addAttribute("role", roleBiz.selectRoleById(roleId).getData());
			return "role.permit.page";
		}else{
			returnErrorInfo(model,webResult.getMessage(), "/role/index.html");
			return "404.page";
		}
	}
	
	/**
	 * 添加角色权限
	 * @param roleId
	 * @param permitIdArr
	 * @return
	 */
	@RequiresPermissions("role:rolePermitAdd")
	@RequestMapping(value="/saveRolePermit",method=RequestMethod.POST)
	@ResponseBody
	public WebResult saveRolePermit(@RequestParam("roleId")Long roleId,@RequestParam("permitIdArr[]")Long[] permitIdArr){
		return roleBiz.saveRolePermit(roleId, permitIdArr);
	}
	
	/**
	 * 删除角色权限
	 * @param roleId
	 * @param permitId
	 * @return
	 */
	@RequiresPermissions("role:rolePermitDelete")
	@RequestMapping(value="/deleteRolePermit",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteRolePermit(@RequestParam("roleId")Long roleId,@RequestParam("permitId")Long permitId){
		return roleBiz.deleteRolePermit(roleId, permitId);
	}
}
