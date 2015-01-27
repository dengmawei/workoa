package org.common.exception.system.depart;

import org.common.exception.OaException;

public class DepartEditException extends OaException {
	private int code;
	
	private String message;
	
	public DepartEditException(int code,String message){
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
