package org.common.exception.system.module;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ModuleValueExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.MODULE_VALUE_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.MODULE_VALUE_EXIST.message;
	}

}
