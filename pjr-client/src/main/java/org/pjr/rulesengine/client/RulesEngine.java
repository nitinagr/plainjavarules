package org.pjr.rulesengine.client;

import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;

/**
 * The Interface RulesEngine.
 *
 * @author Sudhakar
 */
public interface RulesEngine {

	/**
	 * The Enum ExecutionMode.
	 *
	 * @author Sudhakar
	 */
	enum ExecutionMode{
		
		/** The elseif mode. */
		ELSEIF_MODE,
		
		/** The evaulate all mode. */
		EVAULATE_ALL_MODE
	}
	
	/**
	 * Process.
	 *
	 * @param executionMode the execution mode
	 * @param fullyQualifiedClassName the fully qualified class name
	 * @param object the object
	 * @return the object
	 * @throws TechnicalException the technical exception
	 * @throws NonTechnicalException the non technical exception
	 * @author  Sudhakar
	 */
	public Object process(final ExecutionMode executionMode ,final  String fullyQualifiedClassName, final Object object) throws TechnicalException , NonTechnicalException;
	
	/**
	 * Process single rule.
	 *
	 * @param fullyQualifiedClassName the fully qualified class name
	 * @param object the object
	 * @param ruleId the rule id
	 * @return the object
	 * @throws TechnicalException the technical exception
	 * @throws NonTechnicalException the non technical exception
	 * @author  Sudhakar
	 */
	public Object processSingleRule(final  String fullyQualifiedClassName, final Object object , final String ruleId)  throws TechnicalException , NonTechnicalException;
	
	
}
