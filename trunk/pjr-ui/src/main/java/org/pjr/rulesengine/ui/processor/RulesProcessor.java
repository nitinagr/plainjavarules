package org.pjr.rulesengine.ui.processor;

import java.util.List;

import org.pjr.rulesengine.NonTechnicalException;
import org.pjr.rulesengine.TechnicalException;

import org.pjr.rulesengine.ui.uidto.RuleDto;
import org.pjr.rulesengine.ui.uidto.RuleLogicUi;


/**
 * The Interface RulesProcessor.
 *
 * @author Sudhakar
 */
public interface RulesProcessor {

	public List<RuleDto> fetchAllRules() throws TechnicalException;
	
	public List<RuleDto> fetchAllRulesByModel(String modelId) throws TechnicalException;
	
	public RuleDto fetchRule(String ruleId) throws TechnicalException;

	public void updateRule (RuleDto ruleDto) throws TechnicalException;

	public void deleteRule (String ruleId) throws TechnicalException;

	public List<RuleLogicUi> getAllRuleLogicItems(String ruleId) throws TechnicalException;

	public void createRuleDefinition(RuleDto ruleDto)	throws TechnicalException,NonTechnicalException;

	public RuleDto fetchRuleByRuleName(String ruleName) throws TechnicalException ;

	public void duplicateRuleDefinition(RuleDto ruleDto,String ruleIdToCopy) throws TechnicalException,NonTechnicalException;
	
	public void assignMandatoryOperatorsToRule(String rulename) throws TechnicalException;
}
