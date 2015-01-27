package org.common.exception;

public abstract class BaseException extends Exception{
	public abstract int getCode();
	
	public abstract String getMessage();
}
