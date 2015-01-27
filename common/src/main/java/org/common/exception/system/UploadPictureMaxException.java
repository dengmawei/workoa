package org.common.exception.system;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class UploadPictureMaxException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.UPLOAD_PICTURE_MAX_ERR.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.UPLOAD_PICTURE_MAX_ERR.message;
	}

}
