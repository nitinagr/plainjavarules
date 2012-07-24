package com.rules.common.processor;

import java.util.List;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.ConditionPtyMapping;
import com.rules.common.uidto.ConditionDto;

public interface ConditionProcessor {
	
	
	//public Condition getCondition(int ruleId,int conditionId) throws TechnicalException ;
	public List<ConditionPtyMapping> getConditionPtyMapping(int conditionId) throws TechnicalException;
	public void saveCondition(int ruleId,ConditionDto conditionDto) throws TechnicalException ;
	public ConditionDto getConditionWithRuleText(int ruleId,int conditionId) throws TechnicalException ;
}
