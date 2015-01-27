package org.common.constant;

public enum OvertimeStatusEnum {
	DRAFT(0,"草稿"),CHECKING(1,"审核中"),PASS(2,"审核通过"),REFUSED(3,"驳回"),FAILED(4,"失败");
	
	private int code;
	private String desc;
	
	private OvertimeStatusEnum(int code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
