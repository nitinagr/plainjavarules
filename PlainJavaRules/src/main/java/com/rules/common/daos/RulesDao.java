package com.rules.common.daos;

import java.util.List;

import com.rules.common.TechnicalException;
import com.rules.common.dbmodel.RulesConditionMapping;
import com.rules.common.dbmodel.UserRules;

public interface RulesDao {
	public boolean save(UserRules rule);
	public UserRules getUserRule(int ruleId) throws TechnicalException;
	public List<UserRules> getAllUserRules() throws TechnicalException;	
	public void saveRulesConditionMapping(List<RulesConditionMapping> rulesConditionMappingList) throws TechnicalException;

}
