package org.common.exception.system.depart;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class SubDepartExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.SUBDEPART_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.SUBDEPART_EXIST.message;
	}

}
