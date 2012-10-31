package org.pjr.rulesengine.ui.uidto;

public class SubRuleLogicItem {

	public static final String ATTRIBUTE_ID_PREFIX ="sub";
	public static final String OPERATOR_ID_PREFIX ="opr";
	public static final String PREFIX_SEPERATOR="-";

	public static final String ITEMS_SEPERATOR="@";



	//Update
	private String subRuleLogicId;

	private String attrMapIdOrOprMapId;

	//For Displaying
	private String name;
	private boolean attribute;
	private boolean operator;



	public String getSubRuleLogicId() {
		return subRuleLogicId;
	}
	public void setSubRuleLogicId(String subRuleLogicId) {
		this.subRuleLogicId = subRuleLogicId;
	}
	public String getAttrMapIdOrOprMapId() {
		if(attribute) return ATTRIBUTE_ID_PREFIX+PREFIX_SEPERATOR+attrMapIdOrOprMapId;
		else return OPERATOR_ID_PREFIX+PREFIX_SEPERATOR+attrMapIdOrOprMapId;

	}
	public void setAttrMapIdOrOprMapId(String attrMapIdOrOprMapId) {
		this.attrMapIdOrOprMapId = attrMapIdOrOprMapId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAttribute() {
		return attribute;
	}
	public void setAttribute(boolean attribute) {
		this.attribute = attribute;
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
		builder.append("SubRuleLogicItem [subRuleLogicId=");
		builder.append(subRuleLogicId);
		builder.append(", attrMapIdOrOprMapId=");
		builder.append(attrMapIdOrOprMapId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append("]");
		return builder.toString();
	}




}
