package org.common.exception.system.module;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ModuleBeSuperModuleException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.MODULE_BE_SUPERMODULE.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.MODULE_BE_SUPERMODULE.message;
	}

}
