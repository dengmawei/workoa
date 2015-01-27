package org.common.exception.workflow.overtime;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class OvertimeTimezoneConflictException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.OVERTIME_TIMEZONE_OCNFLICT.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.OVERTIME_TIMEZONE_OCNFLICT.message;
	}

}
