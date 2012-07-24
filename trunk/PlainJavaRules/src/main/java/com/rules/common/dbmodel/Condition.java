package com.rules.common.dbmodel;



public class Condition {
	
	private int conditionId;
	private String conditionName;
	private String displayName;

	private boolean enable;
	private boolean defaultvalue;
	

	
	
	public int getConditionId() {
		return conditionId;
	}
	public void setConditionId(int conditionId) {
		this.conditionId = conditionId;
	}
	public String getConditionName() {
		return conditionName;
	}
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public boolean isDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(boolean defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Condition [conditionId=");
		builder.append(conditionId);
		builder.append(", conditionName=");
		builder.append(conditionName);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", enable=");
		builder.append(enable);
		builder.append(", defaultvalue=");
		builder.append(defaultvalue);
		builder.append("]");
		return builder.toString();
	}
	

	
}
