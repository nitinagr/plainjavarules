/**
 *
 */
package org.pjr.rulesengine.ui.uidto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Anubhab(Infosys)
 *
 */
public class SubruleDto {
	//Used for view in UI
	private String id;
	private String name;
	private String description;
	private boolean defaultValue;
	private boolean active;



	private String logicText;

	//Updated from request
	private String updatedLogicText;

	private String mappingLogicTextFromDB;


	private List<SubRuleLogicItem> logic = new ArrayList<SubRuleLogicItem>();




	public String getMappingLogicTextFromDB() {
		return mappingLogicTextFromDB;
	}

	private void setMappingLogicTextFromDB(List<SubRuleLogicItem> logic) {
		StringBuffer text = new StringBuffer();
		for(SubRuleLogicItem rl: logic){
			text.append(rl.getAttrMapIdOrOprMapId()).append(SubRuleLogicItem.ITEMS_SEPERATOR );
		}

		this.mappingLogicTextFromDB = text.toString();
	}

	public void setLogicText(String logicText) {
		this.logicText = logicText;
	}

	public String getLogicText() {
		return logicText;
	}

	private void setLogicText(List<SubRuleLogicItem> logic) {
		StringBuffer text = new StringBuffer();
		for(SubRuleLogicItem rl: logic){
			text.append(rl.getName()).append(" ");
		}
		this.logicText = text.toString();
	}

	public String getUpdatedLogicText() {
		return this.updatedLogicText;
	}

	public void setUpdatedLogicText(String updatedLogicText) {
		this.updatedLogicText = updatedLogicText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if(StringUtils.isNotBlank(name))this.name = StringUtils.replace(StringUtils.trim(name), " ", "_");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}


	public List<SubRuleLogicItem> getLogic() {
		return logic;
	}

	public void setLogic(List<SubRuleLogicItem> logic) {
		setLogicText(logic);
		setMappingLogicTextFromDB(logic);
		this.logic = logic;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubruleDto [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", defaultValue=");
		builder.append(defaultValue);
		builder.append(", active=");
		builder.append(active);
		builder.append(", logicText=");
		builder.append(logicText);
		builder.append(", updatedLogicText=");
		builder.append(updatedLogicText);
		builder.append(", logic=");
		builder.append(logic);
		builder.append("]");
		return builder.toString();
	}



}
