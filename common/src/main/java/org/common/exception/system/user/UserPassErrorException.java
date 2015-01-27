package org.common.exception.system.user;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UserPassErrorException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.USER_PASS_ERROR.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.USER_PASS_ERROR.message;
	}

}
