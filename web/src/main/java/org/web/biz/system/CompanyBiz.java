package org.web.biz.system;



import org.common.entity.Company;
import org.common.exception.OaException;
import org.common.service.ICompanyService;
import org.common.util.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("companyBiz")
public class CompanyBiz {
	@Autowired
	private ICompanyService companyService;
	
	public WebResult selectAllCompany(){
		WebResult webResult = new WebResult();
		webResult.setData(companyService.selectAllCompany());
		webResult.success();
		return webResult;
	}
	
	public WebResult addCompany(Company company){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setMessage("添加成功");
		companyService.addCompany(company);
		return webResult;
	}
	
	public WebResult updateCompany(Company company){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setMessage("修改成功");
		companyService.updateCompany(company);
		return webResult;
	}
	
	public WebResult deleteCompany(Long companyid){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setMessage("删除成功");
		try {
			companyService.deleteCompany(companyid);
		} catch (OaException e) {
			webResult.fail();
			webResult.setMessage(e.getMessage());
		}
		return webResult;
	}
	
	public WebResult selectCompanyById(Long companyid){
		WebResult webResult = new WebResult();
		webResult.success();
		webResult.setMessage("查询成功");
		webResult.setData(companyService.selectCompanyById(companyid));
		return webResult;
	}
}
