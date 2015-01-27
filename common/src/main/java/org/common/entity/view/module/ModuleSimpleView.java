package org.common.entity.view.module;

public class ModuleSimpleView {
	private long id;
	/**
	 * 模块名
	 */
	private String moduleName;
	
	/**
	 * 访问地址
	 */
	private String linkUrl;
	
	/**
	 * 上级部门id
	 */
	private long superId = 0L;
	
	/**
	 * 模块值
	 */
	private String moduleValue;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public long getSuperId() {
		return superId;
	}

	public void setSuperId(long superId) {
		this.superId = superId;
	}

	public String getModuleValue() {
		return moduleValue;
	}

	public void setModuleValue(String moduleValue) {
		this.moduleValue = moduleValue;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
