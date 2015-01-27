package org.common.constant;

public enum SignTypeEnum {
	COMPETITION(0,"竞争"),COUNTERSIGN(1,"会签");
	private int key;
	
	private String value;
	
	private SignTypeEnum(int key,String value){
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
