package org.common.exception.system.role;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class RoleNotFoundException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.ROLE_NOT_FOUND.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.ROLE_NOT_FOUND.message;
	}

}
