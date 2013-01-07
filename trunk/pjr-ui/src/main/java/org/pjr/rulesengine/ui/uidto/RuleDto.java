package org.pjr.rulesengine.ui.uidto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * The Class RuleDto.
 *
 * @author Sudhakar
 */
public class RuleDto {

	/** The rule id. */
	private String ruleId;

	/** The rule name. */
	private String ruleName;

	/** The rule des. */
	private String ruleDes;

	/** The enable. */
	private boolean active;

	/** The default value. */
	private String returnValue;

	private String executionOrder;

	private String logicText;

	//Updated from request
	private String updatedLogicText;

	private String mappingLogicTextFromDB;
	
	private String modelId;

	private List<RuleLogicUi> logic = new ArrayList<RuleLogicUi>();

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
		if(null != ruleId) {
			this.ruleId = StringUtils.trim(ruleId) ;
		}
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
		if(StringUtils.isNotBlank(ruleName))this.ruleName = StringUtils.replace(StringUtils.trim(ruleName), " ", "_");

	}

	public String getRuleDes() {
		return ruleDes;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public void setRuleDes(String ruleDes) {
		this.ruleDes = ruleDes;
		if(null != ruleDes) {
			this.ruleDes = StringUtils.trim(ruleDes) ;
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
		if(null != returnValue) {
			this.returnValue = StringUtils.trim(returnValue) ;
		}
	}




	public String getExecutionOrder() {
		return executionOrder;
	}

	public void setExecutionOrder(String executionOrder) {
		this.executionOrder = executionOrder;
		if(null != executionOrder) {
			this.executionOrder = StringUtils.trim(executionOrder) ;
		}
	}

	public String getLogicText() {
		return logicText;
	}

	private void setLogicText(List<RuleLogicUi> logic) {
		StringBuffer text = new StringBuffer();

		for(RuleLogicUi rl: logic){
			text.append(rl.getName()).append(" ");
		}
		this.logicText = text.toString();
	}

	public List<RuleLogicUi> getLogic() {
		return logic;
	}

	public void setLogic(List<RuleLogicUi> logic) {
		setLogicText(logic);
		setMappingLogicTextFromDB(logic);
		this.logic = logic;
	}

	public String getUpdatedLogicText() {
		return updatedLogicText;
	}

	public void setUpdatedLogicText(String updatedLogicText) {
		if(StringUtils.isNotBlank(updatedLogicText))
		this.updatedLogicText = StringUtils.trim(updatedLogicText);
	}

	public String getMappingLogicTextFromDB() {
		return mappingLogicTextFromDB;
	}

	private void setMappingLogicTextFromDB(List<RuleLogicUi> logic) {
		StringBuffer text = new StringBuffer();
		for(RuleLogicUi rl: logic){
			text.append(rl.getSubRuleMapIdOrOprMapId()).append(RuleLogicUi.ITEMS_SEPERATOR);
		}
		this.mappingLogicTextFromDB = text.toString();
	}

	public void setLogicText(String logicText) {
		this.logicText = logicText;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleDto [ruleId=");
		builder.append(ruleId);
		builder.append(", ruleName=");
		builder.append(ruleName);
		builder.append(", ruleDes=");
		builder.append(ruleDes);
		builder.append(", active=");
		builder.append(active);
		builder.append(", returnValue=");
		builder.append(returnValue);
		builder.append(", executionOrder=");
		builder.append(executionOrder);
		builder.append(", logicText=");
		builder.append(logicText);
		builder.append(", logic=");
		builder.append(logic);
		builder.append("]");
		return builder.toString();
	}



}
