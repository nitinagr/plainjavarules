package org.pjr.rulesengine.daos;

import java.util.List;

import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.SubruleAttributeMapping;
import org.pjr.rulesengine.dbmodel.SubruleOperatorMapping;

/**
 * The Interface SubruleRuleMappingDao.
 *
 * @author Sudhakar
 */
public interface SubruleRuleMappingDao {

	/**
	 * Fetch all rule subrule mapping.
	 *
	 * @return the list
	 * @author  Sudhakar (pjr.org)
	 */
	public List<RuleSubruleMapping> fetchAllRuleSubruleMapping();

	/**
	 * Fetch all rule subrule mapping for a Rule.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @author  Sudhakar (pjr.org)
	 */
	public List<RuleSubruleMapping> fetchAllRuleSubruleMapping(String ruleId);



	/**
	 * Fetch all attribute mapping.
	 *
	 * @param subRuleId the sub rule id
	 * @return the list
	 * @author  Sudhakar (pjr.org)
	 */
	public List<SubruleAttributeMapping> fetchAllAttributeMapping(String subRuleId);

	/**
	 * Fetch all operator mapping.
	 *
	 * @param subRuleId the sub rule id
	 * @return the list
	 * @author  Sudhakar (pjr.org)
	 */
	public List<SubruleOperatorMapping> fetchAllOperatorMapping(String subRuleId);

	/**
	 * @param id
	 * @return
	 *
	 * @Author Sudhakar(pjr.org)
	 * Created: Sep 11, 2012
	 */
	public List<String> fetchRuleNamesForSubrule(String id);

}
