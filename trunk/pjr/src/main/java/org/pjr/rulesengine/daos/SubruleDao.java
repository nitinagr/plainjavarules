package org.pjr.rulesengine.daos;

import java.util.List;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.dbmodel.SubruleLogic;

/**
 * The Interface SubruleDao.
 *
 * @author Sudhakar
 */
public interface SubruleDao {

	/**
	 * Method to insert rows into the PAC_RE_SUBRULE table.
	 *
	 * @param subruleList the subrule list
	 * @return the int[]
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public int[] insertSubrule(List<Subrule> subruleList) throws DataLayerException;

	/**
	 * Method to update the rows of PAC_RE_SUBRULE table.
	 *
	 * @param subruleList the subrule list
	 * @return true, if successful
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public boolean updateSubrule(List<Subrule> subruleList) throws DataLayerException;

	/**
	 * Method to delete rows of PAC_RE_SUBRULE table.
	 *
	 * @param subruleId the subrule id
	 * @return true, if successful
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public boolean deleteSubrule(List<String> subruleId) throws DataLayerException;

		/**
		 * Method to fetch all rows of PAC_RE_SUBRULE table.
		 *
		 * @return the list
		 * @throws DataLayerException the data layer exception
		 * @author  Sudhakar (pjr.org)
		 */
	public List<Subrule> fetchAllSubrules() throws DataLayerException;
	
	public List<Subrule> fetchAllSubrulesbyModelId(String modelId) throws DataLayerException;

	/**
	 * Method to fetch single row of PAC_RE_SUBRULE table.
	 *
	 * @param id the id
	 * @return the subrule
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public Subrule fetchSubrule(String id) throws DataLayerException;

	public Subrule fetchSubruleByRuleMapId(String id) throws DataLayerException;

	public List<RuleSubruleMapping> fetchRuleOperatorMappingForRule (String ruleId) throws DataLayerException;


	/**
	 * Fetch all subrules.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public List<Subrule> fetchAllSubrules(String ruleId)  throws DataLayerException;

	/**
	 * Save assign sub rules to rule.
	 *
	 * @param subruleidListToAssign the subruleid list to assign
	 * @param subruleidListToUnAssign the subruleid list to un assign
	 * @param ruleId the rule id
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public void saveAssignSubRulesToRule(List<String> subruleidListToAssign, List<String> subruleidListToUnAssign ,String ruleId)  throws DataLayerException;

	/**
	 * Sub rules used in rules logic.
	 *
	 * @param subRuleIds the sub rule ids
	 * @param ruleId the rule id
	 * @return the map
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public List<String> subRulesUsedInRulesLogic (List<String> subRuleIds,String ruleId)  throws DataLayerException;

	/**
	 * Fetch subrule by name.
	 *
	 * @param subRuleName the sub rule name
	 * @return the subrule
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public Subrule fetchSubruleByName(String subRuleName) throws DataLayerException;

	public void deleteSubRuleLogic (String subRuleId)  throws DataLayerException;

	public void insertSubRuleLogic (String subRuleId , List<SubruleLogic> logic) throws DataLayerException;

}
