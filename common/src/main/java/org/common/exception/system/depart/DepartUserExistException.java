package org.common.exception.system.depart;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class DepartUserExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.DEPART_USER_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.DEPART_USER_EXIST.message;
	}

}
