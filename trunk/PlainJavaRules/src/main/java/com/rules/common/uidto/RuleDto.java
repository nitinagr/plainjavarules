package com.rules.common.uidto;

import java.util.List;

public class RuleDto {

	private int ruleId;
	private String ruleName;
	private String ruleDes;
	private boolean enable;
	private boolean defaultValue;	
	
	private List<ConditionDto> conditionList;
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDes() {
		return ruleDes;
	}
	public void setRuleDes(String ruleDes) {
		this.ruleDes = ruleDes;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<ConditionDto> getConditionList() {
		return conditionList;
	}
	public void setConditionList(List<ConditionDto> conditionList) {
		this.conditionList = conditionList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleDto [ruleId=");
		builder.append(ruleId);
		builder.append(", ruleName=");
		builder.append(ruleName);
		builder.append(", ruleDes=");
		builder.append(ruleDes);
		builder.append(", enable=");
		builder.append(enable);
		builder.append(", defaultValue=");
		builder.append(defaultValue);
		builder.append(", conditionList=");
		builder.append(conditionList);
		builder.append("]");
		return builder.toString();
	}

	
}
