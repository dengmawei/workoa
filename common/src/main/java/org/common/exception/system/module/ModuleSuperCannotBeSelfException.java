package org.common.exception.system.module;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ModuleSuperCannotBeSelfException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.MODULE_SUPERMODULE_CANNOT_BESELF.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.MODULE_SUPERMODULE_CANNOT_BESELF.message;
	}

}
