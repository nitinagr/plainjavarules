/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.List;

import org.pjr.rulesengine.TechnicalException;

import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * @author Anubhab(Infosys)
 *
 */
public interface SubruleAdminProcessor {

	public int createsubruleDefinition(List<SubruleDto> subruleDtoList)throws TechnicalException;
	public boolean deletesubruleDefinition(List<String> subruleidList)throws TechnicalException;


	public void unassignSubRulesFromRule (List<String> subruleidList , String ruleId) throws TechnicalException;
	public void saveAssignSubRulesToRule(List<String> subruleidListToAssign, List<String> subruleidListToUnAssign , String ruleId) throws TechnicalException;
	public void saveAssignAllOperatorsToSubrule(String subruleName) throws TechnicalException;
	public List<String>  subRulesUsedInRulesLogic (List<String> subRuleIdsToUnAssign,String ruleId)  throws TechnicalException;
}
