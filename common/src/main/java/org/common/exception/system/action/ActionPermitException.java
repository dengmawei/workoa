package org.common.exception.system.action;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ActionPermitException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.ACTION_PERMIT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.ACTION_PERMIT_EXIST.message;
	}

}
