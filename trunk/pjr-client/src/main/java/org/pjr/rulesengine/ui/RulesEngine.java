package org.pjr.rulesengine.ui;

import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;

public interface RulesEngine {

	enum ExecutionMode{
		ELSEIF_MODE,
		EVAULATE_ALL_MODE
	}
	
	public Object process(final ExecutionMode executionMode ,final  String fullyQualifiedClassName, final Object object) throws TechnicalException , NonTechnicalException;
	
	public Object processSingleRule(final  String fullyQualifiedClassName, final Object object , final String ruleId)  throws TechnicalException , NonTechnicalException;
	
	
}
