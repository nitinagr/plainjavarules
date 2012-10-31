package org.pjr.rulesengine.dbmodel;

import org.springframework.stereotype.Component;

//Administration
/**
 * The Class RuleSubruleMapping.
 *
 * @author Sudhakar
 */
@Component
public class RuleSubruleMapping {
	//Insert and Update
	/** The id. */
	private String id;

	/** The rule id. */
	private String ruleId;



	//Only Fetch
	/** The sub rule id. */
	private String subRuleId;

	/** The sub rule name. */
	private String subRuleName;

	/** The sub rule description. */
	private String subRuleDescription;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the rule id.
	 *
	 * @return the rule id
	 */
	public String getRuleId() {
		return ruleId;
	}

	/**
	 * Sets the rule id.
	 *
	 * @param ruleId the new rule id
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * Gets the sub rule id.
	 *
	 * @return the sub rule id
	 */
	public String getSubRuleId() {
		return subRuleId;
	}

	/**
	 * Sets the sub rule id.
	 *
	 * @param subRuleId the new sub rule id
	 */
	public void setSubRuleId(String subRuleId) {
		this.subRuleId = subRuleId;
	}

	/**
	 * Gets the sub rule name.
	 *
	 * @return the sub rule name
	 */
	public String getSubRuleName() {
		return subRuleName;
	}

	/**
	 * Sets the sub rule name.
	 *
	 * @param subRuleName the new sub rule name
	 */
	public void setSubRuleName(String subRuleName) {
		this.subRuleName = subRuleName;
	}

	/**
	 * Gets the sub rule description.
	 *
	 * @return the sub rule description
	 */
	public String getSubRuleDescription() {
		return subRuleDescription;
	}

	/**
	 * Sets the sub rule description.
	 *
	 * @param subRuleDescription the new sub rule description
	 */
	public void setSubRuleDescription(String subRuleDescription) {
		this.subRuleDescription = subRuleDescription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleSubruleMapping [id=");
		builder.append(id);
		builder.append(", ruleId=");
		builder.append(ruleId);
		builder.append(", subRuleId=");
		builder.append(subRuleId);
		builder.append("]");
		return builder.toString();
	}


}
