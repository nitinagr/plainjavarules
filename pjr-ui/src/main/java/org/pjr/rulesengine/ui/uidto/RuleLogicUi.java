package org.pjr.rulesengine.ui.uidto;

public class RuleLogicUi {

	public static final String SUBRULE_ID_PREFIX ="sub";
	public static final String OPERATOR_ID_PREFIX ="opr";
	public static final String PREFIX_SEPERATOR="-";

	public static final String ITEMS_SEPERATOR="@";



	//Update
	private String ruleLogicId;

	private String subRuleMapIdOrOprMapId;

	//For Displaying
	private String name;
	private boolean subRule;
	private boolean operator;


	public String getRuleLogicId() {
		return ruleLogicId;
	}
	public void setRuleLogicId(String ruleLogicId) {
		this.ruleLogicId = ruleLogicId;
	}
	public String getSubRuleMapIdOrOprMapId() {
		if(subRule) return SUBRULE_ID_PREFIX+PREFIX_SEPERATOR+subRuleMapIdOrOprMapId;
		else return OPERATOR_ID_PREFIX+PREFIX_SEPERATOR+subRuleMapIdOrOprMapId;
	}
	public void setSubRuleMapIdOrOprMapId(String subRuleIdOrOprId) {
		this.subRuleMapIdOrOprMapId = subRuleIdOrOprId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSubRule() {
		return subRule;
	}
	public void setSubRule(boolean subRule) {
		this.subRule = subRule;
	}
	public boolean isOperator() {
		return operator;
	}
	public void setOperator(boolean operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleLogic [ruleLogicId=");
		builder.append(ruleLogicId);
		builder.append(", subRuleIdOrOprId=");
		builder.append(subRuleMapIdOrOprMapId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", subRule=");
		builder.append(subRule);
		builder.append(", operator=");
		builder.append(operator);
		builder.append("]");
		return builder.toString();
	}




}
