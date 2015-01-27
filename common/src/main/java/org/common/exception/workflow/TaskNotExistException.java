package org.common.exception.workflow;

import org.common.constant.ExceptionConst;
import org.common.exception.FlowException;

public class TaskNotExistException extends FlowException {

	@Override
	public int getCode() {
		return ExceptionConst.TASK_NOT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.TASK_NOT_EXIST.message;
	}

}
