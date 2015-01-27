package org.common.exception.system.module;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ModuelSuperNotExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.MODULE_SUPER_NOT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.MODULE_SUPER_NOT_EXIST.message;
	}

}
