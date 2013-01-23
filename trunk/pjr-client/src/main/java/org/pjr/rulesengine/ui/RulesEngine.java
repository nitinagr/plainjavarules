package org.pjr.rulesengine.ui;

public interface RulesEngine {

	enum ExecutionMode{
		ELSEIF_MODE,
		EVAULATE_ALL_MODE
	}
	
	public Object process(final ExecutionMode executionMode ,final  String fullyQualifiedClassName, final Object object);
	
	public Object processSingleRule(final  String fullyQualifiedClassName, final Object object , final String ruleId);
	
	
}
