package org.web.biz.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.command.LoginCommand;
import org.common.command.user.ChangePassCommand;
import org.common.entity.Module;
import org.common.entity.view.module.ModuleLoginView;
import org.common.entity.view.module.ModuleSimpleView;
import org.common.exception.OaException;
import org.common.exception.ParamsValidatorException;
import org.common.service.ILoginService;
import org.common.service.IModuleService;
import org.common.util.OvalUtils;
import org.common.util.WebResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component("loginBiz")
public class LoginBiz {
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IModuleService moduleService;
	
	public void logout(){
		loginService.loginOut();
	}
	
	public WebResult changePassword(ChangePassCommand command){
		WebResult webResult = new WebResult();
		try {
			OvalUtils.paramsValidator(command);
			loginService.changePassword(command.getUserName(), command.getPassword(), command.getNewPassword());
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		} catch (ParamsValidatorException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
}
