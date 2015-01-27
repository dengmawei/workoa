package org.common.exception.system;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class PictureFormatException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.PICTURE_FORMAT_ERR.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.PICTURE_FORMAT_ERR.message;
	}

}
