package org.common.exception.system;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class SystemException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.SYSTEM_ERR.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.SYSTEM_ERR.message;
	}

}
