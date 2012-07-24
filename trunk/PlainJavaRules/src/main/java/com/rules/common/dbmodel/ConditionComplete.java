package com.rules.common.dbmodel;

import java.util.List;

public class ConditionComplete {

	private int id;
	private RulesConditionMapping rulesConditionMapping;
	private List<ConditionPtyMapping> conditionPtyMappingList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public RulesConditionMapping getRulesConditionMapping() {
		return rulesConditionMapping;
	}
	public void setRulesConditionMapping(RulesConditionMapping rulesConditionMapping) {
		this.rulesConditionMapping = rulesConditionMapping;
	}
	public List<ConditionPtyMapping> getConditionPtyMappingList() {
		return conditionPtyMappingList;
	}
	public void setConditionPtyMappingList(
			List<ConditionPtyMapping> conditionPtyMappingList) {
		this.conditionPtyMappingList = conditionPtyMappingList;
	}
	
	
	
}
