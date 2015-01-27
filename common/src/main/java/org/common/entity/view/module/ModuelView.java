package org.common.entity.view.module;

import org.common.entity.Module;

public class ModuelView {
	private Module module;
	
	private Module superModule;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Module getSuperModule() {
		return superModule;
	}

	public void setSuperModule(Module superModule) {
		this.superModule = superModule;
	}
}
