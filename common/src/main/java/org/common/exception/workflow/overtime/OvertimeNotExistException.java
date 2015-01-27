package org.common.exception.workflow.overtime;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class OvertimeNotExistException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.OVERTIME_NOT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.OVERTIME_NOT_EXIST.message;
	}

}
