package org.common.entity;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class Module {
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 模块名
	 */
	private String moduleName;
	
	/**
	 * 模块描述
	 */
	private String moduleDesc;
	
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
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 是否删除
	 */
	private int isDel=1;
	
	/**
	 * 是否是父节点
	 */
	private int isParent=0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getLinkUrl() {
		if(linkUrl==null){
			return "";
		}
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getSuperId() {
		return superId;
	}

	public void setSuperId(Long superId) {
		this.superId = superId;
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

	public String getCreateTime() {
		return DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
}
