package org.common.entity;

public class PositionPermit {
	private Long id;
	
	private String positionCode;
	
	private String permitCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPermitCode() {
		return permitCode;
	}

	public void setPermitCode(String permitCode) {
		this.permitCode = permitCode;
	}
}
