package org.common.interceptor;

import org.common.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class LoadDefauleMenuInterceptor implements WebRequestInterceptor {
	@Autowired
	private IModuleService moduleService;

	public void afterCompletion(WebRequest request, Exception exception)
			throws Exception {
	}

	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		model.addAttribute("menuList", moduleService.loadLoginModule());
		model.addAttribute("sid", request.getParameter("sid"));
	}

	public void preHandle(WebRequest request) throws Exception {
	} 
}
