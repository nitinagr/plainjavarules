package org.pjr.rulesengine.dbmodel;


//Administration

public class SubruleOperatorMapping {

	//Insert and Update
	private String id;
	private String subRuleId;
	private String operatorId;

	private Operator operator;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubRuleId() {
		return subRuleId;
	}
	public void setSubRuleId(String subRuleId) {
		this.subRuleId = subRuleId;
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


}
