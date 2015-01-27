package org.web.controllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.common.service.IModuleService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


@ControllerAdvice
public class DefaultExceptionHandler{
	@Autowired
	private IModuleService moduleService;
	
    @ExceptionHandler({UnauthorizedException.class})
    @RequestMapping("/unauthenticated")
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
    	if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request  
                .getHeader("X-Requested-With")!= null && request  
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {  
    		ModelAndView modelAndView = new ModelAndView();
        	modelAndView.addObject("menuList", moduleService.loadLoginModule());
        	modelAndView.addObject("sid", request.getParameter("sid"));
        	modelAndView.addObject("errorMessage", "您的访问权限受限，请联系管理员！");
        	modelAndView.setViewName("unauthorized.page");
            return modelAndView;
    	}else{//异步ajax请求
    		try {  
                PrintWriter writer = ((HttpServletResponse)request.getNativeResponse()).getWriter();
                WebResult webResult = new WebResult();
                webResult.fail();
                webResult.setMessage("您的操作受限，请联系管理员");
                writer.write(JSON.toJSONString(webResult));  
                writer.flush();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            return null;  
    	}
    	
    }

}
