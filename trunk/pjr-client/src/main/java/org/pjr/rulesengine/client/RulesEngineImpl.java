package org.pjr.rulesengine.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mvel2.MVEL;
import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.dbmodel.Rule;

/**
 * The Class RulesEngineImpl.
 *
 * @author Sudhakar
 */
public class RulesEngineImpl implements RulesEngine{

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(RulesEngineImpl.class);

	
	RulesProcessorImpl rulesProcessor;
	/**
	 * Instantiates a new rules engine impl.
	 *
	 * @param dataSource the data source
	 * @author  Sudhakar
	 */
	public RulesEngineImpl(DataSource dataSource){
		rulesProcessor = RulesProcessorImpl.getInstance(dataSource);		
	}
	
	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.RulesEngine#process(org.pjr.rulesengine.ui.RulesEngine.ExecutionMode, java.lang.String, java.lang.Object)
	 */
	@Override
	public List<Rule> process(ExecutionMode executionMode, String fullyQualifiedClassName, Object object)  throws TechnicalException , NonTechnicalException{
		
		log.info("rules processing for "+fullyQualifiedClassName);
		
		String ruleId = "0";
		Rule rule = null;
		List<Rule> rules=new ArrayList<Rule>();
		
		//Business Validation
		Model model = rulesProcessor.isModelNameAlreadyExists(fullyQualifiedClassName);
		if(null == model) throw new NonTechnicalException(fullyQualifiedClassName + " is not available in DB ");
		
		try {
			Map<Rule,Serializable > expressions;
			{
				log.info("Preparing Rules Engine and Generating Expressions for Processing .....");
				long start = System.currentTimeMillis();
				expressions = getCompiledExpressions(fullyQualifiedClassName);
				log.info("Rules Engine preparation Completed in(ms) :"+(System.currentTimeMillis() - start));
			}
			
			Iterator<Rule> iterate = expressions.keySet().iterator();
			while(iterate.hasNext()){
				Rule tempRule = iterate.next();
				ruleId = tempRule.getId();
				Serializable compileExpr = expressions.get(tempRule);
			   	Boolean result = (Boolean) MVEL.executeExpression(compileExpr, object);

			    if(null != result && result){			    	
			    	rule = tempRule;
			    	if(executionMode.equals(ExecutionMode.ELSEIF_MODE)){
			    		rules.add(rule);
			    		break;
			    	}else if(executionMode.equals(ExecutionMode.EVAULATE_ALL_MODE)){			  
			    		rules.add(rule);		  
			    	}
			    }
			}
		} catch (Exception e) {
			log.error("Error processing RuleId :"+ruleId);
			throw new TechnicalException(e);
		}		
		
		return rules;
		
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.RulesEngine#processSingleRule(java.lang.String, java.lang.Object, java.lang.String)
	 */
	@Override
	public Object processSingleRule(String fullyQualifiedClassName, Object object, String ruleId)  throws TechnicalException , NonTechnicalException{		

		//Business Validation
		Model model = rulesProcessor.isModelNameAlreadyExists(fullyQualifiedClassName);
		if(null == model) throw new NonTechnicalException(fullyQualifiedClassName + " is not available in DB ");
		
		Rule tempRule = null;
	   	Boolean result;
		try {
			tempRule = rulesProcessor.fetchRule(ruleId);
			String strExpression =  tempRule.toMvelExpression();
			Serializable serializableExpr = MVEL.compileExpression(strExpression);
			result = (Boolean) MVEL.executeExpression(serializableExpr, object);
		} catch (Exception e) {
			log.error("", e);
			throw new TechnicalException(e);
		}

		if(!result) tempRule = null;
		
		return tempRule;
	}

	/**
	 * Gets the compiled expressions.
	 *
	 * @param modelClass the model class
	 * @return the compiled expressions
	 */
	public LinkedHashMap<Rule,Serializable > getCompiledExpressions(String modelClass){
		LinkedHashMap<Rule,Serializable > expressions = new LinkedHashMap<Rule, Serializable>();
		try {
			List<Rule> rules;
			if(null!=modelClass && !modelClass.isEmpty()){
				rules = rulesProcessor.fetchAllRulesBYExecutionOrder();
			} else {
				rules = rulesProcessor.fetchAllRulesBYExecutionOrder(modelClass);
			}
			for(Rule rule : rules){
				Rule tempRule = rulesProcessor.fetchRule(rule.getId());
				String strExpression =  tempRule.toMvelExpression();
				Serializable serializableExpr = MVEL.compileExpression(strExpression);
				expressions.put(tempRule, serializableExpr);
			}

		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		}
		return expressions;
	}
}
