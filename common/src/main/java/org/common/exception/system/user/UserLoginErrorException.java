package org.common.exception.system.user;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UserLoginErrorException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.USER_LOGIN_ERROR.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.USER_LOGIN_ERROR.message;
	}

}
