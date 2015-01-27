package org.common.exception.system.depart;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class DepartmentCodeExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.DEPART_CODE_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.DEPART_CODE_EXIST.message;
	}

}
