package org.common.service;

import java.util.Map;

import org.common.entity.Department;
import org.common.entity.view.department.DepartmentDetailView;
import org.common.exception.OaException;

public interface IDepartmentService {
	/**
	 * 根据集团id查询下属部门
	 * @param companyid
	 * @return
	 */
	Map<String,Object> selectDepartByCompanyId(Long companyid)throws OaException;
	
	/**
	 * 添加部门
	 * @param department
	 */
	void addDepartment(Department department)throws OaException;
	
	/**
	 * 根据id查询部门
	 * @param id
	 * @return
	 */
	DepartmentDetailView selectDepartmentById(Long id)throws OaException;
	
	/**
	 * 编辑部门
	 * @param department
	 * @throws OaException
	 */
	void updateDepartment(Department department)throws OaException;
	
	/**
	 * 删除部门
	 * @param departId
	 * @throws OaException
	 */
	void deleteDepartment(Long departId)throws OaException;
	
	/**
	 * 根据部门编码查询部门
	 * @param departCode
	 * @return
	 */
	Department selectDepartmentByCode(String departCode);
}
