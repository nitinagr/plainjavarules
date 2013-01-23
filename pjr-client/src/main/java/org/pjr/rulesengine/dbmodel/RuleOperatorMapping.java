package org.pjr.rulesengine.dbmodel;


//Administration

public class RuleOperatorMapping {

	//Insert and Update
	private String id;
	private String ruleId;
	private String operatorId;

	//fetch
	private Operator operator;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleOperatorMapping [id=");
		builder.append(id);
		builder.append(", ruleId=");
		builder.append(ruleId);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append("]");
		return builder.toString();
	}



}
