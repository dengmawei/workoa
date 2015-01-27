package org.common.constant;


public enum WorkflowProcessKeyEnum {
	OVERTIME("overtime","加班申请"),LEAVE("leave","请假申请");
	private WorkflowProcessKeyEnum(String key,String desc){
		this.key = key;
		this.desc = desc;
	}
	
	private String key;
	
	private String desc;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
