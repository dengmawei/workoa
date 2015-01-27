package org.common.exception.system.depart;

import org.common.constant.ExceptionConst;
import org.common.exception.OaException;

public class CompanyCanNotDeleteException extends OaException {

	@Override
	public int getCode() {
		return ExceptionConst.COMPANY_CAN_NOT_DELETE.code;
	}

	@Override
	public String getMessage() {
		return ExceptionConst.COMPANY_CAN_NOT_DELETE.message;
	}

}
