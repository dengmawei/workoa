package org.common.exception;


public class ParamsValidatorException extends BaseException {
	public int code;
	
	public String message;
	
	public ParamsValidatorException(int code, String message) {
        this.code = code;
        this.message = message;
    }
	
	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
