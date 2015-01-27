package org.common.exception.system.role;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class RolePermitExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.ROLE_PERMIT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.ROLE_PERMIT_EXIST.message;
	}

}
