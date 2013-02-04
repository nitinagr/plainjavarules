package org.pjr.rulesengine.dbmodel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;



/**
 * The Class Rule.
 *
 * @author Sudhakar
 */

public class Rule implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4087267830412509495L;

	//Used for inserting and updating
	/** The id. */
	private String id;

	/** The rule name. */
	private String ruleName;

	/** The rule description. */
	private String ruleDescription;

	/** The active. */
	private boolean active;

	/** The return value. */
	private String returnValue;

	/** The execution order. */
	private int executionOrder;

	//only for fetching
	/** The logic. */
	Set<RuleLogic> logic = new TreeSet<RuleLogic>();


	private String modelId;
	
	

	public String getModelId() {
		return modelId;
	}


	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


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
	 * Gets the rule name.
	 *
	 * @return the rule name
	 */
	public String getRuleName() {
		return ruleName;
	}


	/**
	 * Sets the rule name.
	 *
	 * @param ruleName the new rule name
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}


	/**
	 * Gets the rule description.
	 *
	 * @return the rule description
	 */
	public String getRuleDescription() {
		return ruleDescription;
	}


	/**
	 * Sets the rule description.
	 *
	 * @param ruleDescription the new rule description
	 */
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}


	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 * @author  Sudhakar (pjr.org)
	 */
	public boolean isActive() {
		return active;
	}


	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}






	/**
	 * Gets the return value.
	 *
	 * @return the return value
	 */
	public String getReturnValue() {
		return returnValue;
	}


	/**
	 * Sets the return value.
	 *
	 * @param returnValue the new return value
	 */
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}




	/**
	 * Gets the logic.
	 *
	 * @return the logic
	 */
	public Set<RuleLogic> getLogic() {
		return logic;
	}


	/**
	 * Sets the logic.
	 *
	 * @param logic the new logic
	 */
	public void setLogic(Set<RuleLogic> logic) {
		this.logic = logic;
	}


	/**
	 * Gets the execution order.
	 *
	 * @return the execution order
	 */
	public int getExecutionOrder() {
		return executionOrder;
	}


	/**
	 * Sets the execution order.
	 *
	 * @param executionOrder the new execution order
	 */
	public void setExecutionOrder(int executionOrder) {
		this.executionOrder = executionOrder;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rule [id=");
		builder.append(id);
		builder.append(", ruleName=");
		builder.append(ruleName);
		builder.append(", ruleDescription=");
		builder.append(ruleDescription);
		builder.append(", active=");
		builder.append(active);
		builder.append(", returnValue=");
		builder.append(returnValue);
		builder.append(", executionOrder=");
		builder.append(executionOrder);
		builder.append(", logic=");
		builder.append(logic);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * To mvel expression.
	 *
	 * @return the string
	 * @author  Sudhakar (pjr.org)
	 */
	public String toMvelExpression(){
		StringBuffer buff = new StringBuffer();
		buff.append(" ");
		if (this.isActive()) {
			if(null != this.getLogic()){
				Iterator<RuleLogic> iterator = this.getLogic().iterator();

				while(iterator.hasNext()){
					RuleLogic rl = iterator.next();

					if (null != rl.getSubRule()) {
						//Logic item is a sub rule
						buff.append(rl.getSubRule().toMvelExpression());
						if(iterator.hasNext())buff.append(" ");
					} else {
						//Logic item is a operator
						buff.append(rl.getOperator().getValue());
						if(iterator.hasNext())buff.append(" ");
					}
				}
			}

		}else{
			buff.append(false);
		}
		buff.append(" ");

		return buff.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
