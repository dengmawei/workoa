package org.common.constant;

public enum OvertimeTaskNameEnum {
	START("start","开始",""),
	DEPART_MANAGER_APPROVE("departManagerApprove","部门经理审核",""),
	GENERAL_MANAGER_APPROVE("generalManagerApprove","总经理审核",""),
	STAFF_SUMMARY("staffOvertimeHourSummary","员工管理员汇总",""),
	MODIFY("moidfy","申请人修改",""),
	END("end","结束","");
	private String key;
	
	private String value;
	
	private String url;
	
	private OvertimeTaskNameEnum(String key,String value,String url){
		this.key = key;
		this.value = value;
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
