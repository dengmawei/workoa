package org.common.exception.system.user;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UserNotPassException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.USER_NOT_PASS.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.USER_NOT_PASS.message;
	}

}
