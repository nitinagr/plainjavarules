/**
 *
 */
package org.pjr.rulesengine.ui.processor;

import java.util.List;

import org.pjr.rulesengine.TechnicalException;

import org.pjr.rulesengine.ui.uidto.RuleSubruleMappingDto;
import org.pjr.rulesengine.ui.uidto.SubRuleLogicItem;
import org.pjr.rulesengine.ui.uidto.SubruleDto;

/**
 * The Interface SubruleProcessor.
 *
 * @author Anubhab(Infosys)
 */
public interface SubruleProcessor {

	/**
	 * Update subrule.
	 *
	 * @param subruleDto the subrule dto
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public void updateSubrule(SubruleDto subruleDto) throws TechnicalException;

	/**
	 * Fetch all subrules.
	 *
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<SubruleDto> fetchAllSubrules() throws TechnicalException;
	
	public List<SubruleDto> fetchAllSubrulesbyModelId(String modelId) throws TechnicalException;

	/**
	 * Fetch subrule by subruleid.
	 *
	 * @param subruleid the id
	 * @return the subrule dto
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public SubruleDto fetchSubrule(String subruleid) throws TechnicalException;

	/**
	 * Fetch all subrules for a Rule Id.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<SubruleDto> fetchAllSubrules(String ruleId)  throws TechnicalException;

	/**
	 * Fetch all subrules mapping.
	 *
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<RuleSubruleMappingDto> fetchAllSubrulesMapping() throws TechnicalException;

	/**
	 * Fetch subrules mapping for a rule.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<RuleSubruleMappingDto> fetchSubrulesMapping(String ruleId) throws TechnicalException;



	/**
	 * @param id
	 * @return
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 11, 2012
	 */
	public List<String> fetchRuleNamesForSubrule(String id);

	/**
	 * Gets the all sub rule logic items.
	 *
	 * @param subRuleid the sub ruleid
	 * @return the all sub rule logic items
	 * @throws TechnicalException the technical exception
	 */
	public List<SubRuleLogicItem> getAllSubRuleLogicItems(String subRuleid) throws TechnicalException;

}


