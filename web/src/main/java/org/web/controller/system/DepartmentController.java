package org.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.common.entity.Company;
import org.common.entity.Department;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.web.biz.system.CompanyBiz;
import org.web.biz.system.DepartmentBiz;
import org.web.controller.base.BaseController;

@Controller
@RequestMapping("/depart")
public class DepartmentController extends BaseController{
	@Autowired
	private DepartmentBiz departmentBiz;
	
	@Autowired
	private CompanyBiz companyBiz;
	
	@RequiresPermissions("depart:view")
	@RequestMapping(value="/index")
	public String index(){
		return "depart.page";
	}
	
	@RequiresPermissions("depart:select")
	@RequestMapping(value="/selectAllCompany",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectAllCompany(){
		return companyBiz.selectAllCompany();
	}
	
	@RequiresPermissions("depart:delete")
	@RequestMapping(value="/deleteCompany",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteCompany(@RequestParam("companyid") Long companyid){
		return companyBiz.deleteCompany(companyid);
	}
	
	@RequiresPermissions("depart:add")
	@RequestMapping(value="/addCompany",method=RequestMethod.POST)
	@ResponseBody
	public WebResult addCompany(@RequestParam("companyName") String companyName){
		Company company = new Company();
		company.setCompanyName(companyName);
		company.setIsDel(1);
		return companyBiz.addCompany(company);
	}
	
	@RequiresPermissions("depart:edit")
	@RequestMapping(value="/updateCompany",method=RequestMethod.POST)
	@ResponseBody
	public WebResult updateCompany(@RequestParam("companyid") Long companyid,@RequestParam("companyName") String companyName){
		Company company = new Company();
		company.setId(companyid);
		company.setCompanyName(companyName);
		company.setIsDel(1);
		return companyBiz.updateCompany(company);
	}
	
	@RequiresPermissions("depart:select")
	@RequestMapping(value="/loadCompany",method=RequestMethod.POST)
	@ResponseBody
	public WebResult loadCompany(@RequestParam("companyid") Long companyid){
		return companyBiz.selectCompanyById(companyid);
	}
	
	/**
	 * 根据集团id查询下属部门集合
	 * @param companyId
	 * @return
	 */
	@RequiresPermissions("depart:select")
	@RequestMapping(value="/selectDepartByCompanyId",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectDepartByCompanyId(@RequestParam("companyId")Long companyId){
		return departmentBiz.selectDepartByCompanyId(companyId);
	}
	
	/**
	 * 添加部门
	 * @param department
	 * @return
	 */
	@RequiresPermissions("depart:add")
	@RequestMapping(value="/addDepart",method=RequestMethod.POST)
	@ResponseBody
	public WebResult addDepart(@ModelAttribute("depart")Department department){
		return departmentBiz.addDepartment(department);
	}
	
	/**
	 * 根据id查询 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("depart:select")
	@RequestMapping(value="/selectDepartById",method=RequestMethod.POST)
	@ResponseBody
	public WebResult selectDepartById(@RequestParam("id")Long id){
		return departmentBiz.selectDepartById(id);
	}
	
	/**
	 * 编辑部门信息
	 * @param department
	 * @return
	 */
	@RequiresPermissions("depart:edit")
	@RequestMapping(value="/updateDepart",method=RequestMethod.POST)
	@ResponseBody
	public WebResult updateDepart(@ModelAttribute("department")Department department){
		return departmentBiz.updateDeppart(department);
	}
	
	/**
	 * 删除部门
	 * @param departId
	 * @return
	 */
	@RequiresPermissions("depart:delete")
	@RequestMapping(value="/deleteDepart",method=RequestMethod.POST)
	@ResponseBody
	public WebResult deleteDepart(@RequestParam("departId")Long departId){
		return departmentBiz.deleteDepart(departId);
	}
}
