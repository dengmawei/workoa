package org.common.dao;

import java.util.List;

import org.common.entity.Company;
import org.springframework.stereotype.Component;
@Component
@MyBatisRepository
public interface CompanyRepository{
	/**
	 * 查询所有的集团
	 * @return
	 */
	List<Company> selectAllCompany();
	
	/**
	 * 根据ID删除集团
	 * @param companyid
	 */
	void deleteCompanyById(Long companyid);
	
	/**
	 * 根据id查询集团
	 * @param companyid
	 * @return
	 */
	Company selectCompanyById(Long companyid);
	
	/**
	 * 添加company
	 * @param company
	 */
	void addCompany(Company company);
	
	/**
	 * 更新company
	 * @param company
	 */
	void updateCompany(Company company);
}
