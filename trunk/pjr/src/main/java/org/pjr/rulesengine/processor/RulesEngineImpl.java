package org.pjr.rulesengine.processor;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mvel2.MVEL;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.RuleDao;
import org.pjr.rulesengine.dbmodel.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * The Class RulesEngineImpl.
 *
 * @author Sudhakar
 */
@Component("rulesEngine")
public class RulesEngineImpl implements RulesEngine{

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(RulesEngineImpl.class);


	@Autowired
	private RuleDao ruleDao;

	
	public RuleDao getRuleDao() {
		return ruleDao;
	}


	public void setRuleDao(RuleDao ruleDao) {
		this.ruleDao = ruleDao;
	}


	@Override
	public Object processAllRules(Object object) throws TechnicalException {
		
		String ruleId = "0";
		Rule rule = null;
		try {
			Map<Rule,Serializable > expressions;
			{
				log.info("Preparing Rules Engine and Generating Expressions for Processing .....");
				long start = System.currentTimeMillis();
				expressions = getCompiledExpressions();
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
			    	break;
			    }
			}
		} catch (Exception e) {
			log.error("Error processing RuleId :"+ruleId);
		}
		return rule;
	}


	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.processor.RulesEngine#processRule(java.lang.Object, int)
	 */
	@Transactional(readOnly=true)
	@Override
	public Object processRule(Object object , String ruleId) throws TechnicalException {
		boolean result = false;

		Rule rule = ruleDao.fetchRule(ruleId);


		Object mvelResult;
		try {
			if(null == rule ){
				throw new TechnicalException("Rule not found for ruleId:"+ruleId);
			}

			String mvelCondition = transformToMvelFormat(rule);
			mvelResult = MVEL.eval(mvelCondition,(Object)object);
			result = (Boolean) mvelResult;
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}


	private String transformToMvelFormat(Rule rule){
		return rule.toMvelExpression();
	}

	@Cacheable(cacheName="RulesEngineImpl.getCompiledExpressions" )
	//@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public LinkedHashMap<Rule,Serializable > getCompiledExpressions() throws TechnicalException{
		log.info("fetching Rules from Db.......");
		LinkedHashMap<Rule,Serializable > expressions = new LinkedHashMap<Rule, Serializable>();
		try {
			List<Rule> rules = ruleDao.fetchAllRulesBYExecutionOrder();

			for(Rule rule : rules){
				Rule tempRule = ruleDao.fetchRule(rule.getId());
				String strExpression =  tempRule.toMvelExpression();
				Serializable serializableExpr = MVEL.compileExpression(strExpression);
				expressions.put(tempRule, serializableExpr);
			}

		} catch (Exception e) {
			throw new TechnicalException(e, "Unable to generate expression for the Rule " );
		}


		return expressions;
	}


}
