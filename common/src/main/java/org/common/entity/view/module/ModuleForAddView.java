package org.common.entity.view.module;

import java.util.Date;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;

import org.apache.commons.lang.StringUtils;

public class ModuleForAddView {
	/**
	 * 模块名
	 */
	@NotNull(message="模块名不能为空")
	private String moduleName;
	
	/**
	 * 模块描述
	 */
	private String moduleDesc;
	
	/**
	 * 模块值
	 */
	@NotNull(message="模块值不能为空")
	@Length(max=30,min=1,message="模块值字符长度超过限定长度")
	private String moduleValue;
	
	/**
	 * 访问地址
	 */
	@ValidateWithMethod(methodName="isAllowLinkNull",parameterType=String.class,message="访问地址不能为空且最长50字符")
	private String linkUrl;
	
	private boolean isAllowLinkNull(String linkUrl){
		if(isParent==0){
			if(StringUtils.isBlank(linkUrl)){
				return false;
			}
			
			if(linkUrl.length()>50){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 上级部门id
	 */
	@ValidateWithMethod(methodName="isNeedSuper",parameterType=Long.class,message="上级模块编码不能为空")
	private long superId = 0L;
	
	private boolean isNeedSuper(Long superId){
		if(isParent==1){
			return true;
		}
		
		if(superId.longValue()>0L){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 是否是父节点
	 */
	private int isParent=0;

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

	public String getModuleValue() {
		return moduleValue;
	}

	public void setModuleValue(String moduleValue) {
		this.moduleValue = moduleValue;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
}
