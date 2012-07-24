package com.rules.common.uidto;

public class ConditionDto {
	
	private int ruleId;
	private int conditionId;
	private String conditionName;
	private String displayName;
	private boolean enable;
	private boolean defaultvalue;	
	private String conditionDisplay;
	private String conditionMvel;
	
	
	
	public String[] getConditionDisplayInArrayFormat(){
		String conditionBusinessFriendlyText = getConditionDisplay();
		String[] conditionBusinessFriendlyTextArray = null;
		if(null != conditionBusinessFriendlyText)conditionBusinessFriendlyTextArray = conditionBusinessFriendlyText.split(" ");		
		return conditionBusinessFriendlyTextArray;
	}
	
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
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
	public String getConditionDisplay() {
		return conditionDisplay;
	}
	public void setConditionDisplay(String conditionDisplay) {
		this.conditionDisplay = conditionDisplay;
	}
	public String getConditionMvel() {
		return conditionMvel;
	}
	public void setConditionMvel(String conditionMvel) {
		this.conditionMvel = conditionMvel;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConditionDto [ruleId=");
		builder.append(ruleId);
		builder.append(", conditionId=");
		builder.append(conditionId);
		builder.append(", conditionName=");
		builder.append(conditionName);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", enable=");
		builder.append(enable);
		builder.append(", defaultvalue=");
		builder.append(defaultvalue);
		builder.append(", conditionDisplay=");
		builder.append(conditionDisplay);
		builder.append(", conditionMvel=");
		builder.append(conditionMvel);
		builder.append("]");
		return builder.toString();
	}
	
	

}
