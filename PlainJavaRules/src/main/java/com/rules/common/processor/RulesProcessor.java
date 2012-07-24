package com.rules.common.processor;

import java.util.List;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.uidto.RuleDto;

public interface RulesProcessor {
	
	public RuleDto getRule(int ruleId) throws TechnicalException;
	public List<RuleDto> getAllRules() throws TechnicalException;
	public boolean saveRule(RuleDto rule) throws TechnicalException;
	public void saveRulesConditionMapping(List<RulesConditionMapping> rulesConditionMappingList) throws TechnicalException;
	
	

}
