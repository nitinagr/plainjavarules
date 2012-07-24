package com.rules.common.dbmodel;

public class RulesConditionMapping {

	private int ruleId;
	private boolean enable;
	private Condition condition;
	private String conditionDisplay;
	private String conditionMvel;
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
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

}
