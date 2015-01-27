package org.common.entity.view.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleLoginView {
	private String superModuleName;
	
	private int subModuleCount;
	
	private List<ModuleSimpleView> subModuleList = new ArrayList<ModuleSimpleView>();


	public int getSubModuleCount() {
		return subModuleCount;
	}

	public void setSubModuleCount(int subModuleCount) {
		this.subModuleCount = subModuleCount;
	}

	public String getSuperModuleName() {
		return superModuleName;
	}

	public void setSuperModuleName(String superModuleName) {
		this.superModuleName = superModuleName;
	}

	public List<ModuleSimpleView> getSubModuleList() {
		return subModuleList;
	}

	public void setSubModuleList(List<ModuleSimpleView> subModuleList) {
		this.subModuleList = subModuleList;
	}
	
	public void addSubModule(ModuleSimpleView module){
		subModuleList.add(module);
	}
	
	public void addCount(){
		++this.subModuleCount;
	}
}
