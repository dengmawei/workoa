package org.common.exception.system.depart;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class DepartmentNotFoundException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.DEPART_NOT_FOUND.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.DEPART_NOT_FOUND.message;
	}

}
