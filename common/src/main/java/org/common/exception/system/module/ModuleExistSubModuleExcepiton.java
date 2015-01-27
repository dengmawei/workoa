package org.common.exception.system.module;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ModuleExistSubModuleExcepiton extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.MODULE_EXIST_SUBMODULE.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.MODULE_EXIST_SUBMODULE.message;
	}

}
