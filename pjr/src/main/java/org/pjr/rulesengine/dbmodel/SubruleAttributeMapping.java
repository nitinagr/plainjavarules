package org.pjr.rulesengine.dbmodel;

import org.springframework.stereotype.Component;

//Administration
@Component
public class SubruleAttributeMapping {

	//Insert and Update
	private String id;
	private String subRuleId;
	private String attributeId;

	//fetch
	private Attribute attribute;

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
	public String getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}


}
