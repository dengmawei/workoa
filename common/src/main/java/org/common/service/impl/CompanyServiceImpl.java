package org.common.service.impl;

import java.util.List;

import org.common.dao.CompanyRepository;
import org.common.dao.DepartmentRepository;
import org.common.entity.Company;
import org.common.entity.view.department.DepartmentView;
import org.common.exception.OaException;
import org.common.exception.system.depart.CompanyCanNotDeleteException;
import org.common.exception.system.depart.CompanyNotFoundException;
import org.common.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("companyService")
public class CompanyServiceImpl implements ICompanyService {
	@Autowired
	private CompanyRepository companyDao;
	@Autowired
	private DepartmentRepository departDao;

	public List<Company> selectAllCompany() {
		return companyDao.selectAllCompany();
	}

	public void addCompany(Company company) {
		companyDao.addCompany(company);
	}

	public void deleteCompany(Long companyid)throws OaException {
		Company company = this.selectCompanyById(companyid);
		if(company==null){
			throw new CompanyNotFoundException();
		}
		int departNum = departDao.selectCountOfDepartByCompanyId(companyid);
		if(departNum>0){
			throw new CompanyCanNotDeleteException();
		}
		companyDao.deleteCompanyById(companyid);
	}

	public void updateCompany(Company company) {
		companyDao.updateCompany(company);
	}

	public Company selectCompanyById(Long companyid) {
		return companyDao.selectCompanyById(companyid);
	}
}
