package org.common.exception.system.schedule;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class ScheduleParticipantsNotNullException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.SCHEDULE_PARTICIPANTS_NOT_NULL.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.SCHEDULE_PARTICIPANTS_NOT_NULL.message;
	}

}
