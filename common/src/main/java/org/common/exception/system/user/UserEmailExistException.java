package org.common.exception.system.user;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UserEmailExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.USER_EMAIL_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.USER_EMAIL_EXIST.message;
	}

}
