package org.common.exception.system.user;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UserNameExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.USER_NAME_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.USER_NAME_EXIST.message;
	}

}
