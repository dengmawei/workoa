package org.common.exception.system.permit;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class PermitRoleExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.PERMIT_ROLE_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.PERMIT_ROLE_EXIST.message;
	}

}
