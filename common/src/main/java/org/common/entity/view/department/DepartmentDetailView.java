package org.common.entity.view.department;

public class DepartmentDetailView {
	/**
	 * 部门id
	 */
	private Long id;
	
	/**
	 * 部门编号
	 */
	private String departmentCode;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	/**
	 * 部门职能描述
	 */
	private String departmentDesc;
	
	/**
	 * 上级部门名称
	 */
	private String superDepartmentName;
	
	/**
	 * 集团名称
	 */
	private String companyName;
	
	/**
	 * 集团id
	 */
	private Long companyId;
	
	/**
	 * 上级部门id
	 */
	private Long superId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public String getSuperDepartmentName() {
		return superDepartmentName;
	}

	public void setSuperDepartmentName(String superDepartmentName) {
		this.superDepartmentName = superDepartmentName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}
}
