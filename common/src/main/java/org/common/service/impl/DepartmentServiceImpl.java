package org.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.constant.ExceptionConst;
import org.common.dao.CompanyRepository;
import org.common.dao.DepartmentRepository;
import org.common.dao.UserRepository;
import org.common.entity.Company;
import org.common.entity.Department;
import org.common.entity.view.department.DepartmentDetailView;
import org.common.entity.view.department.DepartmentView;
import org.common.exception.OaException;
import org.common.exception.system.depart.CompanyNotFoundException;
import org.common.exception.system.depart.DepartEditException;
import org.common.exception.system.depart.DepartUserExistException;
import org.common.exception.system.depart.DepartmentCodeExistException;
import org.common.exception.system.depart.DepartmentNotFoundException;
import org.common.exception.system.depart.SubDepartExistException;
import org.common.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService {
	@Autowired
	public DepartmentRepository departmentRepository;
	
	@Autowired
	public CompanyRepository companyRepository;
	
	@Autowired
	public UserRepository userDao;
	
	public Map<String,Object> selectDepartByCompanyId(Long companyid) throws OaException{
		Map<String,Object> map = new HashMap<String,Object>();
		
		Company company = companyRepository.selectCompanyById(companyid);
		if(company==null){
			throw new CompanyNotFoundException();
		}
		
		map.put("company", company);
		map.put("departList", departmentRepository.selectDepartByCompanyId(companyid));
		return map;
	}

	public void addDepartment(Department department)throws OaException {
		if(department.getSuperId().longValue()!=0L){
			Department superDepart = departmentRepository.selectDepartmentById(department.getSuperId());
			department.setSuperCode(superDepart.getDepartmentCode());
		}
		Department tempDepart = departmentRepository.selectDepartmentByCode(department.getDepartmentCode());
		if(tempDepart!=null){
			throw new DepartmentCodeExistException();
		}
		departmentRepository.addDepartment(department);
	}

	public DepartmentDetailView selectDepartmentById(Long id) throws OaException {
		Department depart = departmentRepository.selectDepartmentById(id);
		
		if(depart==null){
			throw new DepartmentNotFoundException();
		}
		
		Department superDepart = departmentRepository.selectDepartmentByCode(depart.getSuperCode());
		
		Company company = companyRepository.selectCompanyById(depart.getCompanyId());
		if(company==null){
			throw new CompanyNotFoundException();
		}
		
		DepartmentDetailView view = new DepartmentDetailView();
		view.setId(depart.getId());
		view.setCompanyName(company.getCompanyName());
		view.setDepartmentCode(depart.getDepartmentCode());
		view.setDepartmentDesc(depart.getDepartmentDesc());
		view.setDepartmentName(depart.getDepartmentName());
		view.setSuperDepartmentName(superDepart!=null?superDepart.getDepartmentName():"");
		view.setCompanyId(company.getId());
		view.setSuperId(depart.getSuperId());
		
		return view;
	}

	public void updateDepartment(Department department) throws OaException {
		if(department.getSuperId().longValue() == department.getId()){
			throw new DepartEditException(ExceptionConst.SUPERDEPART_CANNOT_BESELF.code,ExceptionConst.SUPERDEPART_CANNOT_BESELF.message);
		}
		
		Department superDepart = null;
		if(department.getSuperId().longValue()>0L){
			superDepart = departmentRepository.selectDepartmentById(department.getSuperId());
			if(superDepart==null){
				throw new DepartmentNotFoundException();
			}
		}
		
		Department originalDepart = departmentRepository.selectDepartmentById(department.getId());
		
		if(isSuperDepart(originalDepart.getId(),department.getSuperId(),department.getCompanyId())){
			throw new DepartEditException(ExceptionConst.SUPERDEPART_CANNOT_SUBDEPART.code,ExceptionConst.SUPERDEPART_CANNOT_SUBDEPART.message);
		}
		
		originalDepart.setCompanyId(department.getCompanyId());
		originalDepart.setDepartmentCode(department.getDepartmentCode());
		originalDepart.setDepartmentDesc(department.getDepartmentDesc());
		originalDepart.setDepartmentName(department.getDepartmentName());
		originalDepart.setSuperId(superDepart!=null?superDepart.getId():0L);
		originalDepart.setSuperCode(superDepart!=null?superDepart.getDepartmentCode():null);
		
		departmentRepository.updateDepartment(originalDepart);
	}
	
	private boolean isSuperDepart(long departmentId,long subDepartId,long companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("superId", departmentId);
		map.put("companyId", companyId);
		List<DepartmentView> subDepartList = departmentRepository.selectSubDepartsById(map);
		
		for(DepartmentView view:subDepartList){
			if(view.getId().longValue()==subDepartId){
				return true;
			}else{
				if(isSuperDepart(view.getId(), subDepartId, companyId)){
					return true;
				}
			}
		}
		return false;
	}

	public void deleteDepartment(Long departId) throws OaException {
		int userCount = userDao.selectCountUserByDepartId(departId);
		if(userCount>0){
			throw new DepartUserExistException();
		}
		
		int subDepartCount = departmentRepository.selectSubDepartCountBySuperId(departId);
		if(subDepartCount>0){
			throw new SubDepartExistException();
		}
		
		departmentRepository.deleteDepartment(departId);
	}

	public Department selectDepartmentByCode(String departCode){
		return departmentRepository.selectDepartmentByCode(departCode);
	}
	
}
