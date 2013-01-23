package org.pjr.rulesengine.dbmodel;



public class SubruleLogic {

	//Used for inserting and updating
	private String id;
	private String subRuleId;
	private String attributeMapId;
	private String operatorMapId;
	private String orderno;


	//only for fetching
	private Attribute attribute;
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
	public String getAttributeMapId() {
		return attributeMapId;
	}
	public void setAttributeMapId(String attributeId) {
		this.attributeMapId = attributeId;
	}
	public String getOperatorMapId() {
		return operatorMapId;
	}
	public void setOperatorMapId(String operatorId) {
		this.operatorMapId = operatorId;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
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
		builder.append("SubruleLogic [id=");
		builder.append(id);
		builder.append(", subRuleId=");
		builder.append(subRuleId);
		builder.append(", attributeMapId=");
		builder.append(attributeMapId);
		builder.append(", operatorMapId=");
		builder.append(operatorMapId);
		builder.append(", orderno=");
		builder.append(orderno);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append(", operator=");
		builder.append(operator);
		builder.append("]");
		return builder.toString();
	}

}
