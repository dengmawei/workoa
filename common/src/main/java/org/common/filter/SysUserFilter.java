package org.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.common.constant.NameSpace;
import org.common.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class SysUserFilter extends PathMatchingFilter {
	@Autowired
	private IUserService userService;

	@Override
	protected boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		String userName = (String)SecurityUtils.getSubject().getPrincipal();
		request.setAttribute(NameSpace.CURRENT_USER, userService.selectUserByUserName(userName));
		return true;
	}
}
