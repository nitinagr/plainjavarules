package org.pjr.rulesengine.client;

import java.util.List;

import javax.sql.DataSource;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dao.ModelDao;
import org.pjr.rulesengine.dao.RuleDao;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.dbmodel.Rule;

public class RulesProcessorImpl {
	
	
	public static final String PJR_CACHE_NAME="pjr.cache" ;
	
	
	/** The rule dao. */
	private RuleDao ruleDao;
	
	/** The model dao. */
	private ModelDao modelDao;
	
	static RulesProcessorImpl  rulesProcessor = null;
	
	public static RulesProcessorImpl getInstance(DataSource dataSource){
		if(null == rulesProcessor){
			rulesProcessor = new RulesProcessorImpl();			
			rulesProcessor.ruleDao=new RuleDao(dataSource);
			rulesProcessor.modelDao = new ModelDao(dataSource);				
		}
		return rulesProcessor;
	}
	
	RulesProcessorImpl (){	
		
	}

	
	public Rule fetchRule(final String ruleId) throws DataLayerException{
		Rule tempRule = (Rule)PjrCachemanager.getInstance().get("RULE_ID_"+ruleId, PJR_CACHE_NAME);
		
		if(null == tempRule) {
			tempRule = ruleDao.fetchRule(ruleId);
			PjrCachemanager.getInstance().put("RULE_ID_"+ruleId, tempRule, PJR_CACHE_NAME);
		}
		return tempRule;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Rule> fetchAllRulesBYExecutionOrder() throws DataLayerException{
		List<Rule> rules ;
		
		rules = (List<Rule>) PjrCachemanager.getInstance().get("ALL_RULES", PJR_CACHE_NAME);
		if(null == rules){
			rules = ruleDao.fetchAllRulesBYExecutionOrder();		
			PjrCachemanager.getInstance().put("ALL_RULES", rules, PJR_CACHE_NAME);
		}
		return rules;
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> fetchAllRulesBYExecutionOrder(String modelName) throws DataLayerException{
		List<Rule> rules ;
		rules = (List<Rule>) PjrCachemanager.getInstance().get("ALL_RULES_"+modelName, PJR_CACHE_NAME);
		
		
		if(null == rules){
			rules = ruleDao.fetchAllRulesBYExecutionOrder(modelName);		
			PjrCachemanager.getInstance().put("ALL_RULES_"+modelName, rules, PJR_CACHE_NAME);
		}		
		return rules;		
	}
	
	
	public Model isModelNameAlreadyExists(String fullyQualifiedClassName) throws DataLayerException{
		Model model ;
		model = (Model) PjrCachemanager.getInstance().get("MODEL_"+fullyQualifiedClassName, PJR_CACHE_NAME);
		
		if(null == model){
			model = modelDao.isModelNameAlreadyExists(fullyQualifiedClassName);
			PjrCachemanager.getInstance().put("MODEL_"+fullyQualifiedClassName, model, PJR_CACHE_NAME);
		}
		return model;
	}
	
}
