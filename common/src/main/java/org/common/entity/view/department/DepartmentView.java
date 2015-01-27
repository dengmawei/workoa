package org.common.entity.view.department;

public class DepartmentView {
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
	 * 上级部门id
	 */
	private Long superId;
	
	/**
	 * 上级部门code
	 */
	private String superCode;

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

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
	}

	public String getSuperCode() {
		return superCode;
	}

	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}
}
