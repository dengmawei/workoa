package org.common.exception.system.user;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UserNotFoundException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.USER_NOT_FOUND.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.USER_NOT_FOUND.message;
	}

}
