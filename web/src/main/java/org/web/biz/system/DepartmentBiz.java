package org.web.biz.system;

import org.common.entity.Department;
import org.common.exception.OaException;
import org.common.service.IDepartmentService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("departmentBiz")
public class DepartmentBiz {
	@Autowired
	private IDepartmentService departmentService;
	
	public WebResult selectDepartByCompanyId(Long companyid){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setMessage("查询成功");
		try {
			webResult.setData(departmentService.selectDepartByCompanyId(companyid));
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult addDepartment(Department depart){
		WebResult webResult = new WebResult();
		try {
			departmentService.addDepartment(depart);
			webResult.success();
			webResult.setMessage("添加成功");
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		
		return webResult;
	}
	
	public WebResult selectDepartById(Long id){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setMessage("查询成功");
		try {
			webResult.setData(departmentService.selectDepartmentById(id));
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult updateDeppart(Department depart){
		WebResult webResult = new WebResult();
		try {
			departmentService.updateDepartment(depart);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult deleteDepart(Long departId){
		WebResult webResult = new WebResult();
		try {
			departmentService.deleteDepartment(departId);
			webResult.success();
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
}
