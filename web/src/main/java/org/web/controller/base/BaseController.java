package org.web.controller.base;

import org.springframework.ui.Model;
import org.web.biz.system.ErrorPage;


public class BaseController {
	public void returnErrorInfo(Model model, String errorMessage,String backUrl){
		model.addAttribute("errorPage", new ErrorPage(errorMessage, backUrl));
	}
}
