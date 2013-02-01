package org.pjr.rulesengine.ui;

import java.util.List;

import javax.sql.DataSource;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dao.ModelDao;
import org.pjr.rulesengine.dao.RuleDao;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.dbmodel.Rule;

public class RulesProcessorImpl {
	
	private DataSource dataSource;
	
	/** The rule dao. */
	private RuleDao ruleDao;
	
	/** The model dao. */
	private ModelDao modelDao;
	
	static RulesProcessorImpl  rulesProcessor = null;
	
	public static RulesProcessorImpl getInstance(DataSource dataSource){
		if(null == rulesProcessor){
			rulesProcessor = new RulesProcessorImpl(dataSource);			
			rulesProcessor.ruleDao=new RuleDao(dataSource);
			rulesProcessor.modelDao = new ModelDao(dataSource);				
		}
		return rulesProcessor;
	}
	
	RulesProcessorImpl (DataSource dataSource){	
		getInstance(dataSource);
	}

	
	public Rule fetchRule(final String ruleId) throws DataLayerException{
		Rule tempRule = ruleDao.fetchRule(ruleId);
		return tempRule;
	}
	
	public List<Rule> fetchAllRulesBYExecutionOrder() throws DataLayerException{
		List<Rule> rules ;
		rules = ruleDao.fetchAllRulesBYExecutionOrder();		
		return rules;
	}
	
	
	public List<Rule> fetchAllRulesBYExecutionOrder(String modelName) throws DataLayerException{
		List<Rule> rules ;
		rules = ruleDao.fetchAllRulesBYExecutionOrder(modelName);		
		return rules;		
	}
	
	public Model isModelNameAlreadyExists(String fullyQualifiedClassName) throws DataLayerException{
		Model model = modelDao.isModelNameAlreadyExists(fullyQualifiedClassName);
		return model;
	}
	
}
