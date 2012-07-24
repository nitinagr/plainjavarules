package com.rules.common.daos;

import java.util.List;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.ConditionPtyMapping;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.uidto.ConditionDto;

public interface ConditionsDao {


	public RulesConditionMapping getConditionWithRuleText(int ruleId , int conditionId) throws TechnicalException;
	public void saveCondition(int ruleId, ConditionDto conditionDto) throws TechnicalException ;	
	public List<ConditionPtyMapping> getConditionPtyMapping(int conditionId) throws TechnicalException;
	
}
