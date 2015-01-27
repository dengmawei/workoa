package org.common.exception.workflow;

import org.common.constant.ExceptionConst;
import org.common.exception.FlowException;

public class ProcessInstanceNotExistException extends FlowException {

	@Override
	public int getCode() {
		return ExceptionConst.PROCESS_INSTANCE_NOT_EXIST.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.PROCESS_INSTANCE_NOT_EXIST.message;
	}

}
