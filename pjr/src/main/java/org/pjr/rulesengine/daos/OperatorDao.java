/**
 *
 */
package org.pjr.rulesengine.daos;

import java.util.List;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.RuleOperatorMapping;

/**
 * The Interface OperatorDao.
 *
 * @author Sudhakar(pjr.org)
 */
public interface OperatorDao {
	/**
	 *
	 * @param operatorList the operator list
	 * @return the int[]
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public int[] insertIntoOperator(List<Operator> operatorList) throws DataLayerException;

	/**
	 * Method to update the rows of PAC_RE_OPERATOR table.
	 *
	 * @param operatorList the operator list
	 * @return true, if successful
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public boolean updateOperator(List<Operator> operatorList) throws DataLayerException;

	/**
	 * Method to delete rows of PAC_RE_OPERATOR table.
	 *
	 * @param operatorId the operator id
	 * @return the int[]
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public int[] deleteOperator(List<String> operatorId) throws DataLayerException;

	/**
	 * Fetch all operators.
	 *
	 * @return the list
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public List<Operator> fetchAllOperators() throws DataLayerException;

	/**
	 * Fetch operator.
	 *
	 * @param operatorId the operator id
	 * @return the operator
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public Operator fetchOperator(String operatorId) throws DataLayerException;

	/**
	 * Fetch operators allowed for rule.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public List<Operator> fetchOperatorsAllowedForRule(String ruleId) throws DataLayerException;


	/**
	 * Save assign operators to rule.
	 *
	 * @param ruleId the rule id
	 * @param operatorIdToAssign the operator id to assign
	 * @param operatorIdToUnAssign the operator id to un assign
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public void saveAssignOperatorsToRule(String ruleId,  List<String> operatorIdToAssign,List<String> operatorIdToUnAssign)  throws DataLayerException;

	/**
	 * Fetch operators assigned to rule.
	 *
	 * @param operatorIdsToUnassign the operator ids to unassign
	 * @param ruleId the rule id
	 * @return the list
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public List<String> fetchOperatorsAssignedToRule(List<String> operatorIdsToUnassign ,String ruleId) throws DataLayerException;

	/**
	 * Checks if is operator name exists.
	 *
	 * @param name the name
	 * @return true, if is operator name exists
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public boolean isOperatorNameExists(String name)throws DataLayerException;

	/**
	 * Checks if is operator value exists.
	 *
	 * @param name the name
	 * @return true, if is operator value exists
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public boolean isOperatorValueExists(String name)throws DataLayerException;

	/**
	 * Fetch operators allowed for subrule.
	 *
	 * @param subruleId the subrule id
	 * @return the list
	 * @throws DataLayerException the data layer exception
	 * @Author Sudhakar(pjr.org)
	 * Created: Sep 7, 2012
	 */
	public List<Operator> fetchOperatorsAllowedForSubrule(String subruleId)throws DataLayerException;

	/**
	 * Save assign operators to subrule.
	 *
	 * @param subruleId the subrule id
	 * @param operatorIds the operator ids
	 * @param operatorIdsUnassigned
	 * @return true, if successful
	 * @throws DataLayerException the data layer exception
	 * @Author Sudhakar(pjr.org)
	 * Created: Sep 7, 2012
	 */
	public boolean saveAssignOperatorsToSubrule(String subruleId, List<String> operatorIds, List<String> operatorIdsUnassigned)throws DataLayerException;


	/**
	 * Operator names in subrule logic.
	 *
	 * @param subruleid the subruleid
	 * @param operatorIdsUnassigned the operator ids unassigned
	 * @return the list
	 * @Author Sudhakar(pjr.org)
	 * Created: Sep 10, 2012
	 */
	public List<String> operatorNamesInSubruleLogic(String subruleid, List<String> operatorIdsUnassigned);


	/**
	 * Fetch rule operator mapping for rule.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar (pjr.org)
	 */
	public List<RuleOperatorMapping> fetchRuleOperatorMappingForRule(String ruleId) throws DataLayerException;

	/**
	 * Gets the rules for operator.
	 *
	 * @param operatorId the operator id
	 * @return the rules for operator
	 * @Author Sudhakar(pjr.org)
	 * Created: Sep 11, 2012
	 */
	public List<String> getRulesForOperator(String operatorId);

	/**
	 * Gets the subrules for operator.
	 *
	 * @param operatorId the operator id
	 * @return the subrules for operator
	 * @Author Sudhakar(pjr.org)
	 * Created: Sep 11, 2012
	 */
	public List<String> getSubrulesForOperator(String operatorId);

}
