package org.common.dao;

import java.util.List;
import java.util.Map;

import org.common.entity.Department;
import org.common.entity.view.department.DepartmentView;
import org.springframework.stereotype.Component;

@Component
@MyBatisRepository
public interface DepartmentRepository {
	/**
	 * 根据集团id查询下属部门
	 * 
	 * @param companyid
	 * @return
	 */
	List<DepartmentView> selectDepartByCompanyId(long companyId);

	/**
	 * 根据id查询部门
	 * 
	 * @param id
	 * @return
	 */
	Department selectDepartmentById(Long id);

	/**
	 * 根据code查询部门
	 * 
	 * @param code
	 * @return
	 */
	Department selectDepartmentByCode(String code);
	
	/**
	 * 添加部门
	 * @param department
	 */
	void addDepartment(Department department);
	
	/**
	 * 根据公司id查询其下部门数
	 * @param companyId
	 * @return
	 */
	int selectCountOfDepartByCompanyId(long companyId);
	
	/**
	 * 查询子部门
	 * @param map
	 * @return
	 */
	List<DepartmentView> selectSubDepartsById(Map<String,Object> map);
	
	/**
	 * 更新部门信息
	 * @param department
	 */
	void updateDepartment(Department department);
	
	/**
	 * 查询部门下的子部门数
	 */
	int selectSubDepartCountBySuperId(Long departId);
	
	/**
	 * 删除部门
	 */
	void deleteDepartment(Long departId);
}
