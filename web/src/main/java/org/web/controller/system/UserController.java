package org.web.controller.system;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.command.user.UserAddCommand;
import org.common.command.user.UserUpdateCommand;
import org.common.entity.view.PaginationView;
import org.common.entity.view.user.UserBaseView;
import org.common.entity.view.user.UserView;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.web.bind.annotation.CurrentUser;
import org.web.biz.system.UserBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private UserBiz userBiz;
	
	/**
	 * 用户中心
	 * @return
	 */
	@RequiresPermissions("employee:view")
	@RequestMapping(value = "/index")
	public String index(Model model){
		model.addAttribute("companyId", 1);
		return "user.page";
	}
	
	/**
	 * 分页查询用户
	 * @param paginationView
	 * @return
	 */
	@RequiresPermissions("employee:select")
	@RequestMapping(value="/selectUser",method = RequestMethod.POST)
	@ResponseBody
	public WebResult selectUser(@ModelAttribute("paginationView")PaginationView<UserBaseView> paginationView,@RequestParam("deptCode") String deptCode){
		paginationView.loadFilter().put("deptCode", deptCode);
		return userBiz.selectUser(paginationView);
	}
	
	/**
	 * 添加用户
	 * @param userCommand
	 * @return
	 */
	@RequiresPermissions("employee:add")
	@RequestMapping(value="/addUser",method = RequestMethod.POST)
	@ResponseBody
	public WebResult addUser(MultipartHttpServletRequest request,@ModelAttribute("userCommand")UserAddCommand userCommand){
		userCommand.setPicFile(request.getFile("userPic"));
		return userBiz.addUser(userCommand);
	}
	
	/**
	 * 编辑用户
	 * @param userCommand
	 * @return
	 */
	@RequiresPermissions("employee:edit")
	@RequestMapping(value="/editUser",method = RequestMethod.POST)
	@ResponseBody
	public WebResult editUser(MultipartHttpServletRequest request,@ModelAttribute("userCommand")UserUpdateCommand userCommand){
		userCommand.setPicFile(request.getFile("userPic"));
		return userBiz.updateUser(userCommand);
	}
	
	/**
	 * 根据用户id查询用户头像
	 * @param userId
	 * @return
	 */
	@RequiresPermissions("employee:select")
	@RequestMapping(value="/selectUserById",method = RequestMethod.POST)
	@ResponseBody
	public WebResult selectUserById(@RequestParam("userId")Long userId){
		return userBiz.selectUserById(userId);
	}
	
	/**
	 * 查询用户头像
	 * @param response
	 */
	@RequiresPermissions("employee:select")
	@RequestMapping(value="/selectUserImage",method = RequestMethod.GET)
	public void selectUserImage(HttpServletResponse response,HttpServletRequest request){
		try {
			Long userId = Long.valueOf(request.getParameter("userId"));
			OutputStream output = response.getOutputStream();
			userBiz.selectUserImage(userId,output);
			output.flush();
			output.close();
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * 根据当前用户名查询用户个人信息
	 * @return
	 */
	@RequestMapping(value="/selectCurrentUser",method = RequestMethod.POST)
	@ResponseBody
	public WebResult selectCurrentUser(@CurrentUser UserView user){
		return userBiz.selectUserByUserName(user.getUserName());
	}
	
	/**
	 * 更新个人权限
	 * @return
	 */
	@RequestMapping(value="/refreshPermission",method = RequestMethod.POST)
	@ResponseBody
	public WebResult refreshPermission(){
		return userBiz.refreshPersionPermission();
	}
}
