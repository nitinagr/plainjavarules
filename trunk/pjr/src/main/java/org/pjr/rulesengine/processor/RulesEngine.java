package org.pjr.rulesengine.processor;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.dbmodel.Subrule;


/**
 * The Interface RulesEngine.
 *
 * @author Sudhakar
 */
public interface RulesEngine {


	public Object processAllRules(Object object)  throws TechnicalException ;

	/**
	 * Process rule.
	 *
	 * @param object the object
	 * @param ruleId the rule id
	 * @return the object
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (pjr.org)
	 */
	public Object processRule(Object object , String ruleId) throws TechnicalException ;


	
	public LinkedHashMap<Rule,Serializable > getCompiledExpressions() throws TechnicalException;


}
