package org.common.exception.workflow;

import org.common.constant.ExceptionConst;
import org.common.exception.FlowException;

public class ExecutionSignalFailedException extends FlowException {

	@Override
	public int getCode() {
		return ExceptionConst.EXECUTION_SINAL_FAILED.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.EXECUTION_SINAL_FAILED.message;
	}

}
