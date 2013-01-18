package org.pjr.rulesengine.daos;

import java.util.List;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.dbmodel.RuleLogic;


/**
 * The Interface RulesDao.
 *
 * @author Sudhakar
 */
public interface RuleDao {


	//public void insertIntoOperator(final List<Operator> operatorList)throws DataLayerException;
	public void insertIntoRules(Rule rule)throws DataLayerException ;

	public void duplicateRuleAndRuleLogic(Rule rule,String ruleIdToCopy)throws DataLayerException ;
	//public void insertIntoRulesLogic(final List<RuleLogic> ruleLogicList)throws DataLayerException;
	//public void insertIntoRulesOprMapping(final List<RuleOperatorMapping> ruleOprMapList)throws DataLayerException;
	//public void insertIntoRuleSubRuleMapping(final List<RuleSubruleMapping> ruleSubRuleMapList)	throws DataLayerException;
	//public boolean updateRuleLogic(final List<RuleLogic> ruleLogicList) 	throws DataLayerException;
	public boolean updateRule(final List<Rule> ruleList)throws DataLayerException ;
	//public boolean updateRulesOprMapping(final List<RuleOperatorMapping> ruleOprMapList)throws DataLayerException;
	//public boolean updateRuleSubRuleMapping(final List<RuleSubruleMapping> ruleSubRuleMapList)throws DataLayerException ;
	//public boolean deleteFromRuleLogic(final Long ruleLogicId)	throws DataLayerException;
	//public boolean deleteFromRulesOprMap(final Long rulesOprId)	throws DataLayerException;
	public boolean deleteFromRules(final String rulesId)	throws DataLayerException;
	//public boolean deleteFromRuleSubRuleMap(final Long ruleSubruleId)	throws DataLayerException;


	public List<Rule> fetchAllRules() throws DataLayerException;

	public List<Rule> fetchAllRulesByModel(String modelId) throws DataLayerException;
	
	public List<Rule> fetchAllRulesBYExecutionOrder() throws DataLayerException;

	public Rule fetchRule(String ruleId) throws DataLayerException;
	public Rule fetchRuleByName(String ruleName) throws DataLayerException;

	public void  insertRuleLogic(String ruleId, List<RuleLogic> ruleLogics)  throws DataLayerException;
	public void deleteRuleLogic (String ruleId)   throws DataLayerException;

	public boolean isExecutionOrderExists(int executionOrder) throws DataLayerException;

}
