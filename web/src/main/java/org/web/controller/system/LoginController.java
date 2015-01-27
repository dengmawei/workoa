package org.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.common.command.user.ChangePassCommand;
import org.common.entity.view.user.UserView;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.bind.annotation.CurrentUser;
import org.web.biz.system.LoginBiz;
import org.web.biz.system.ScheduleBiz;
import org.web.controller.base.BaseController;

@Controller
public class LoginController extends BaseController{
	@Autowired
	private LoginBiz loginBiz;
	
	@Autowired
	private ScheduleBiz scheduleBiz;
	
	@RequestMapping(value="/login")
	public String login(HttpServletResponse response,HttpServletRequest request,Model model){
		String errorClassName = (String)request.getAttribute("shiroLoginFailure");
		
		if(UnknownAccountException.class.getName().equals(errorClassName)) {
			model.addAttribute("errorMessage", "用户名不存在");
        } else if(IncorrectCredentialsException.class.getName().equals(errorClassName)) {
        	model.addAttribute("errorMessage", "密码错误");
        } else if(errorClassName != null) {
        	model.addAttribute("errorMessage", "登录失败");
        }
		
		model.addAttribute("userName", request.getAttribute("userName"));
		model.addAttribute("password", request.getAttribute("password"));
		return "login.page";
	}
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(@CurrentUser UserView user, Model model){
		return "index.page";
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(){
		loginBiz.logout();
		return "redirect:/login.jsp";
	}
	
	/**
	 * 修改密码
	 * @param command
	 * @return
	 */
	@RequestMapping(value="/changePassword")
	@ResponseBody
	public WebResult changePassword(@ModelAttribute("changePassCommand")ChangePassCommand command){
		command.setUserName((String)SecurityUtils.getSubject().getPrincipal());
		return loginBiz.changePassword(command);
	}
}
