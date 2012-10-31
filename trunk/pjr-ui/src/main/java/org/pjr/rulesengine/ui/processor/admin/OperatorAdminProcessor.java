/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.List;

import org.pjr.rulesengine.TechnicalException;

import org.pjr.rulesengine.ui.uidto.OperatorDto;

/**
 * The Interface OperatorAdminProcessor.
 *
 * @author Anubhab(Infosys)
 */

public interface OperatorAdminProcessor {

	/**
	 * Method to insert rows into the PAC_RE_OPERATOR table.
	 *
	 * @param operatorDtoList the operator dto list
	 * @return the int
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public int insertOperator(List<OperatorDto> operatorDtoList) throws TechnicalException;

	/**
	 * Method to update the rows of PAC_RE_OPERATOR table.
	 *
	 * @param operatorDtoList the operator dto list
	 * @return true, if successful
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public boolean updateOperator(List<OperatorDto> operatorDtoList) throws TechnicalException;

	/**
	 * Method to delete rows of PAC_RE_OPERATOR table.
	 *
	 * @param operatorId the operator id
	 * @return the int
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public int deleteOperator(String operatorId) throws TechnicalException;

	/**
	 * Fetch all operators.
	 *
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<OperatorDto> fetchAllOperators() throws TechnicalException;

	/**
	 * Fetch operator.
	 *
	 * @param string the operator id
	 * @return the operator dto
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public OperatorDto fetchOperator(String string) throws TechnicalException;


	/**
	 * Fetch operators assigned to rule.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<OperatorDto> fetchOperatorsAssignedToRule (String ruleId) throws TechnicalException;

	/**
	 * Fetch operators assigned to subrule.
	 *
	 * @param ruleId the rule id
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<OperatorDto> fetchOperatorsAssignedToSubrule (String ruleId) throws TechnicalException;


	/**
	 * Save assign operators to rule.
	 *
	 * @param ruleId the rule id
	 * @param operatorIdToAssign the operator id to assign
	 * @param operatorIdToUnAssign the operator id to un assign
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public void saveAssignOperatorsToRule (String ruleId , List<String> operatorIdToAssign,List<String> operatorIdToUnAssign) throws TechnicalException;

	/**
	 * Save assign operators to subrule.
	 *
	 * @param subruleid the subruleid
	 * @param operatorIds the operator ids
	 * @return true, if successful
	 * @throws TechnicalException the technical exception
	 * @Author Anubhab(Infosys)
	 * Created: Sep 7, 2012
	 */
	public boolean saveAssignOperatorsToSubrule(String subruleid, List<String> operatorIds, List<String> operatorIdsUnassigned) throws TechnicalException;

	/**
	 * Operators used in subrule logic.
	 *
	 * @param subruleid the subruleid
	 * @param operatorIdsUnassigned the operator ids unassigned
	 * @return the list
	 * @Author Anubhab(Infosys)
	 * Created: Sep 10, 2012
	 */
	public List<String> operatorsUsedInSubruleLogic(String subruleid, List<String> operatorIdsUnassigned);

	/**
	 * Gets the opr rule ref.
	 *
	 * @param operatorId the operator id
	 * @return the opr rule ref
	 * @Author Anubhab(Infosys)
	 * Created: Sep 11, 2012
	 */
	public List<String> getOprRuleRef(String operatorId);

	/**
	 * Gets the opr subrule ref.
	 *
	 * @param operatorId the operator id
	 * @return the opr subrule ref
	 * @Author Anubhab(Infosys)
	 * Created: Sep 11, 2012
	 */
	public List<String> getOprSubruleRef(String operatorId);

	/**
	 * Fetch operators assigned to rule.
	 *
	 * @param operatorIdsToUnassign the operator ids to unassign
	 * @param ruleId the rule id
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<String> fetchOperatorsAssignedToRule(List<String> operatorIdsToUnassign ,String ruleId) throws TechnicalException;
}
