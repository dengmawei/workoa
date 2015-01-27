package org.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.common.command.LoginCommand;
import org.common.service.ILoginService;
import org.common.util.OvalUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class WorkoaFormAuthenticationFilter extends FormAuthenticationFilter {
	@Autowired
	private ILoginService loginService;
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		if(isLoginRequest(request, response)){
			if(isLoginSubmission(request, response)){
				LoginCommand command = new LoginCommand();
				command.setUserName(request.getParameter(getUsernameParam()));
				command.setPassword(request.getParameter(getPasswordParam()));
				command.setRemeberMe("1".equals(request.getParameter(getRememberMeParam())));
				if(loginService.login(command,req)){
					WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
			        return false;
				}
				return true;
			}else{
				return true;
			}
		}else{
			saveRequestAndRedirectToLogin(request, response);
            return false;
		}
	}
}
