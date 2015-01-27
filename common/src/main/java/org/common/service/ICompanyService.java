package org.common.service;

import java.util.List;

import org.common.entity.Company;
import org.common.exception.OaException;

public interface ICompanyService {
	/**
	 * 查询所有的集团
	 * @return
	 */
	List<Company> selectAllCompany();
	
	void addCompany(Company company);
	
	/**
	 * 删除集团
	 * @param companyid
	 */
	void deleteCompany(Long companyid)throws OaException;
	
	/**
	 * 修改集团
	 * @param company
	 */
	void updateCompany(Company company);
	
	/**
	 * 根据id查询集团
	 * @param companyid
	 * @return
	 */
	Company selectCompanyById(Long companyid);
}
