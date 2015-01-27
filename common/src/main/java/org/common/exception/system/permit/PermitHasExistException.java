package org.common.exception.system.permit;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class PermitHasExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.PERMIT_HAS_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.PERMIT_HAS_EXIST.message;
	}

}
