package org.common.constant;

public enum JoinsignTypeEnum {
	NO_JOINSIGN(0,"无会签"),ADVISE_JOINSIGN(1,"建议"),ALLVOTE_JOINSIGN(2,"一票通过"),ONEVOTE_JOINSIGN(3,"一票否决"),PERCENTVOTE_JOINSIGN(4,"投票比例");
	
	private int code;
	
	private String desc;

	private JoinsignTypeEnum(int code, String desc) {
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
