package org.common.exception.system.schedule;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ScheduleCannotRemoveException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.SCHEDULE_CANNOT_REMOVE.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.SCHEDULE_CANNOT_REMOVE.message;
	}

}
