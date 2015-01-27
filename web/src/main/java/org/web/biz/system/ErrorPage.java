package org.web.biz.system;

public class ErrorPage {
	private String message;
	
	private String backUrl;

	public ErrorPage(String message, String backUrl) {
		this.message = message;
		this.backUrl = backUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
}
