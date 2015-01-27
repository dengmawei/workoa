package org.common.exception.system.schedule;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ScheduleTimeZoneConflictException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.SCHEDULE_TIME_ZONE_CONFLICT.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.SCHEDULE_TIME_ZONE_CONFLICT.message;
	}

}
