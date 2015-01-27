package org.common.exception.workflow;

import org.common.constant.ExceptionConst;
import org.common.exception.FlowException;

public class ExecutionNotExistException extends FlowException {

	@Override
	public int getCode() {
		return ExceptionConst.EXECUTION_NOT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.EXECUTION_NOT_EXIST.message;
	}

}
