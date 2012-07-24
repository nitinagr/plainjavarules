package com.rules.common.processor;

import com.rules.common.TechnicalException;


public interface RulesEngine {
	
	public boolean processRule(Object object , int ruleId) throws TechnicalException ;
}
