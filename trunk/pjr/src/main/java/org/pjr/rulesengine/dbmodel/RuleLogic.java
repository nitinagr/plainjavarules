package org.pjr.rulesengine.dbmodel;

import org.springframework.stereotype.Component;

@Component
public class RuleLogic implements Comparable<RuleLogic>{

	//Used for inserting and updating
	private String id;
	private String ruleId;
	private String ruleSubRuleMapping;
	private String ruleOperatorIdMapping;
	private long orderno;


	//only for fetching
	private Subrule subRule;
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
	public String getRuleSubRuleMappingId() {
		return ruleSubRuleMapping;
	}
	public void setRuleSubRuleMapping(String subRuleId) {
		this.ruleSubRuleMapping = subRuleId;
	}
	public String getRuleOperatorIdMapping() {
		return ruleOperatorIdMapping;
	}
	public void setRuleOperatorIdMapping(String operatorId) {
		this.ruleOperatorIdMapping = operatorId;
	}
	public long getOrderno() {
		return orderno;
	}
	public void setOrderno(long orderno) {
		this.orderno = orderno;
	}
	public Subrule getSubRule() {
		return subRule;
	}
	public void setSubRule(Subrule subRule) {
		this.subRule = subRule;
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
		builder.append("RuleLogic [id=");
		builder.append(id);
		builder.append(", ruleId=");
		builder.append(ruleId);
		builder.append(", ruleSubRuleMapping=");
		builder.append(ruleSubRuleMapping);
		builder.append(", ruleOperatorIdMapping=");
		builder.append(ruleOperatorIdMapping);
		builder.append(", orderno=");
		builder.append(orderno);
		builder.append(", subRule=");
		builder.append(subRule);
		builder.append(", operator=");
		builder.append(operator);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int compareTo(RuleLogic rl) {

		return (int)(this.orderno - rl.orderno);
	}



}
